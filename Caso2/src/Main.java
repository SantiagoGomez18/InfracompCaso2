import java.util.Scanner;

public class Main {
    private static boolean bandera = true;
    private static MarcoPaginas marcos;
    private static MarcoReferencia marcoReferencia;
    private static ProcesadorReferencias procesadorReferencias;

    public static void main(String[] args) {
        while(bandera){
            System.out.println("==== MENU ====");
            System.out.println("1. Generar Referencias");
            System.out.println("2. Procesar Referencias");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el tamaño de la página: ");
                    int tamanoPagina = scanner.nextInt();
                    System.out.print("Ingrese el nombre del archivo: ");
                    String nombreArchivo = scanner.next();
                    String nombreArchivoCompleto = "Caso2/Imagenes/" + nombreArchivo + ".bmp";
                    GeneradorReferencias generador = new GeneradorReferencias(tamanoPagina, nombreArchivoCompleto);
                    generador.generarReferencia();
                    break;
                case 2:
                    System.out.println("Ingrese el numero de marcos de pagina: ");
                    int nMarcos = scanner.nextInt();

                    System.out.println("Ingrese el nombre del archivo de referencia: ");
                    String nombreArchivoReferencia = scanner.next();
                    String nombreArchivoReferenciaCompleto = "Caso2/Referencias/" + nombreArchivoReferencia + ".txt";
                    procesadorReferencias = new ProcesadorReferencias(nMarcos, nombreArchivoReferenciaCompleto);

                    marcoReferencia = new MarcoReferencia();
                    marcos = new MarcoPaginas(nMarcos);
                    Lector lector = new Lector(marcos, marcoReferencia, procesadorReferencias);
                    Actualizador actualizador = new Actualizador(marcoReferencia);
                    lector.start();
                    actualizador.start();
                    
                    break;
                case 3:
                    bandera = false;
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}