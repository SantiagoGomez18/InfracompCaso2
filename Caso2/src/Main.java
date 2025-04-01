import java.util.Scanner;

public class Main {
    private static boolean bandera = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (bandera) {
            System.out.println("==== MENU ====");
            System.out.println("1. Generar Referencias");
            System.out.println("2. Procesar Referencias");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            if (opcion == 1) {
                System.out.print("Ingrese el tamaño de la página: ");
                int tamanoPagina = scanner.nextInt();
                System.out.print("Ingrese el nombre del archivo: ");
                String nombreArchivo = scanner.next();
                String nombreArchivoCompleto = "Caso2/Imagenes/" + nombreArchivo + ".bmp";
                GeneradorReferencias generador = new GeneradorReferencias(tamanoPagina, nombreArchivoCompleto);
                generador.generarReferencia();
            } else if (opcion == 2) {
                System.out.print("Ingrese el número de marcos de página: ");
                int numMarcos = scanner.nextInt();
                scanner.nextLine(); 
            
                System.out.print("Ingrese el nombre del archivo de referencia: ");
                String nombreArchivo = scanner.nextLine();
                String rutaArchivo = "Caso2/Referencias/" + nombreArchivo + ".txt";
            
                MemoriaCompartida memoria = new MemoriaCompartida(numMarcos);
                Lector lector = new Lector(memoria, rutaArchivo);
                Actualizador actualizador = new Actualizador(memoria);
            
                lector.start();
                actualizador.start();
            
                try {
                    lector.join(); //Hago el join para que el output salga antes que el main y se vea bonito Att: Santi :p
                } catch (InterruptedException e) {
                    System.out.println("Error esperando al lector: " + e.getMessage());
                }
            
                actualizador.detener(); 
            
                System.out.println("==== Fin del procesamiento ====");
            } else if (opcion == 3) {
                bandera = false;
                System.out.println("Saliendo del programa...");
            } else {
                System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}
