import java.io.File;
import java.util.Scanner;

public class Lector extends Thread {
    private final MemoriaCompartida memoria;
    private final String nombreArchivo;
    private int hits = 0;
    private int misses = 0;
    private int totalRefs = 0;

    private final long tiempoRAM = 50;  
    private final long tiempoSWAP = 10000000; 
    private long tiempoTotal = 0;

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
    
            long inicio = System.nanoTime();
    
            while (scanner.hasNextLine()) {
                totalRefs++;
                String line = scanner.nextLine();
                String[] partes = line.split(",");
                int idPag = Integer.parseInt(partes[1]);
                String tipoAcceso = partes[3].trim();
                boolean esLectura = tipoAcceso.equals("R") || tipoAcceso.equals("W");
                boolean esEscritura = tipoAcceso.equals("W");
    
                Pagina pagNueva = new Pagina(idPag, esLectura, esEscritura);
                procesarAcceso(pagNueva);
            }
    
            long fin = System.nanoTime();
            tiempoTotal = fin - inicio;
    
            double porcentajeHits = ((double) hits / totalRefs) * 100;
            long tiempoIdeal = totalRefs * tiempoRAM;
            long tiempoPeor = totalRefs * (tiempoRAM + tiempoSWAP);
    
            System.out.println("==== RESULTADOS ====");
            System.out.println("Número de hits: " + hits);
            System.out.println("Número de fallos de página: " + misses);
            System.out.println(String.format("Porcentaje de hits: %.2f%%", porcentajeHits));
            System.out.println("Tiempo total con hits y misses: " + tiempoTotal + " ns");
            System.out.println("Tiempo con todas las referencias en RAM: " + tiempoIdeal + " ns");
            System.out.println("Tiempo donde todas las referencias generan fallas de página: " + tiempoPeor + " ns");
            System.out.println("==================");
    
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
