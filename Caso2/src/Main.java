public class Main {
    public static void main(String[] args) {
        int tamanoPagina = 512;  // O el tamaño que uses
        String nombreArchivo = "Caso2/Imagenes/parrots.bmp";  // Asegúrate de que el nombre sea correcto

        GeneradorReferencias generador = new GeneradorReferencias(tamanoPagina, nombreArchivo);
        generador.generarReferencia();  // Llamada al método

 // Para ver las referencias en consola
    }
}