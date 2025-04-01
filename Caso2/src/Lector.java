import java.io.File;
import java.util.Scanner;

public class Lector extends Thread {
    private final MemoriaCompartida memoria;
    private final String nombreArchivo;
    private int hits = 0;
    private int misses = 0;

    public Lector(MemoriaCompartida memoria, String nombreArchivo) {
        this.memoria = memoria;
        this.nombreArchivo = nombreArchivo;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    @Override
    public void run() {
        File file = new File(nombreArchivo);

        if (!file.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            for (int i = 0; i < 5; i++) {
                scanner.nextLine(); 
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] partes = line.split(",");
                int idPag = Integer.parseInt(partes[1]);
                String tipoAcceso = partes[3].trim();
                boolean esLectura = tipoAcceso.equals("R") || tipoAcceso.equals("W");
                boolean esEscritura = tipoAcceso.equals("W");

                Pagina pagNueva = new Pagina(idPag, esLectura, esEscritura);
                procesarAcceso(pagNueva);
            }

            System.out.println("El número de hits es: " + hits);
            System.out.println("El número de fallos es: " + misses);

        } catch (Exception e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
        }
    }

    private void procesarAcceso(Pagina nueva) {
        Pagina existente = memoria.obtenerPagina(nueva.getId());

        if (existente != null) {
            hits++;
            existente.setLectura(true);
            if (nueva.isEscritura()) {
                existente.setEscritura(true);
            }
        } else {
            misses++;
            if (memoria.getTamaño() < memoria.getMaxMarcos()) {
                memoria.agregarPagina(nueva);
            } else {
                Pagina reemplazo = memoria.seleccionRemplazo();
                if (reemplazo != null) {
                    memoria.removerPagina(reemplazo.getId());
                }
                memoria.agregarPagina(nueva);
            }
        }
    }
}
