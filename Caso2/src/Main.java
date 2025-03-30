public class Main {
    public static void main(String[] args) {
        int tamanoPagina = 512;  
        String nombreArchivo = "Caso2/Imagenes/parrots.bmp";  

        GeneradorReferencias generador = new GeneradorReferencias(tamanoPagina, nombreArchivo);
        generador.generarReferencia();  

    }
}