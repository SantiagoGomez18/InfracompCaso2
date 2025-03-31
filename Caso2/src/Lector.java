import java.io.File;
import java.util.Scanner;

public class Lector extends Thread {
    
    private Scanner sc = new Scanner(System.in);
    private MarcoPaginas marcos;
    private MarcoReferencia marcoReferencia;
    private ProcesadorReferencias procesadorReferencias;
    private int contador=0;
    private int contador2=0;

    public Lector(MarcoPaginas marcos, MarcoReferencia marcoReferencia, ProcesadorReferencias procesadorReferencias) {
        this.procesadorReferencias = procesadorReferencias;
        this.marcos = marcos;
        this.marcoReferencia = marcoReferencia;
    }

    public void run() {
        String archivo = procesadorReferencias.getNombreArchivoRef();
        File file = new File(archivo);

        if(!file.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }
        try(Scanner scanner = new Scanner(file)) {
            for (int i = 0; i<5; i++){
                scanner.nextLine();
            }

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] partes = line.split(",");
                int idPag = Integer.parseInt(partes[1]);
                String tipoAcceso = partes[3].trim();
                boolean esLectura = tipoAcceso.equals("R");
                boolean esEscritura = tipoAcceso.equals("W");
                if (esEscritura){
                    esLectura = true; 
                }
                Pagina pag = new Pagina(idPag, esLectura, esEscritura);

                procesadorReferencias.actualizarMarco(pag);
                contador++;
                contador2++;
                if (contador == 10000){
                    contador = 0;
                    Thread.sleep(1);
                }
                if (contador2 == 37){
                    break;
                }
                
            }
            System.out.println("El numero de hits es: " + procesadorReferencias.getHits());
            System.out.println("El numero de fallos es: " + procesadorReferencias.getFallos());

        }catch(Exception e){
            System.out.println("Error al procesar el archivo: " + e.getMessage());
        }
    }

}