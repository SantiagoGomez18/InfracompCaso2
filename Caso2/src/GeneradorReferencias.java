
import java.io.FileWriter;
import java.io.PrintWriter;


public class GeneradorReferencias {
    private Imagen imagen;
    private Imagen imagenOut;
    private int tamanoPagina;
    private String nombreArchivo;


    // Sobel Kernels para detección de bordes
    static final int[][] SOBEL_X = {
        {-1, 0, 1},
        {-2, 0, 2},
        {-1, 0, 1}
    };
    static final int[][] SOBEL_Y = {
        {-1, -2, -1},
        { 0, 0, 0},
        { 1, 2, 1}
    };

    public GeneradorReferencias(int tamanoPagina, String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        this.imagen = new Imagen(nombreArchivo);
        this.tamanoPagina = tamanoPagina;
        this.imagenOut = new Imagen("Caso2/Imagenes/Salida.bmp");
    }


    public String generarReferencia() {
        System.out.println("Generando referencias para la imagen: " + this.nombreArchivo);
        int numReferencias = 0;
        StringBuilder referencias = new StringBuilder();
        referencias.append("TP=").append(tamanoPagina).append("\n");
        referencias.append("NF=").append(imagen.alto).append("\n");
        referencias.append("NC=").append(imagen.ancho).append("\n");
        referencias.append("NR=algo").append("\n");

        int offset = 0;
        int offsetI = offset;
        offset += imagen.alto * imagen.ancho * 3;

        int offsetSobelX = offset;
        offset += 3 * 3 *4;

        int offsetSobelY = offset;
        offset += 3 * 3 *4;

        int offsetOut = offset;
        offset += imagen.alto * imagen.ancho * 3;

        int total = offset;
        int numPaginas = (int) Math.ceil((double) total / tamanoPagina);
        referencias.append("NP=").append(numPaginas).append("\n");
    
        // Recorrer la imagen aplicando los dos filtros de Sobel
        for (int i = 1; i < imagen.alto - 1; i++) {
            for (int j = 1; j < imagen.ancho - 1; j++) {
                int gradXRed = 0, gradXGreen = 0, gradXBlue = 0;
                int gradYRed = 0, gradYGreen = 0, gradYBlue = 0;
    
                // Aplicar las máscaras Sobel X y Y
                for (int ki = -1; ki <= 1; ki++) {
                    for (int kj = -1; kj <= 1; kj++) {
                        int fila = i + ki;
                        int columna = j + kj;
    
                        // Acceder a los valores RGB de la imagen
                        int red = imagen.imagen[fila][columna][0];
                        int green = imagen.imagen[fila][columna][1];
                        int blue = imagen.imagen[fila][columna][2];
    
                        // Generar referencia para cada componente de color
                        for (int s = 0; s < 3; s++) {
                            int os = (fila * imagen.ancho * 3) + (columna * 3) + s;
                            int pagina = os / tamanoPagina;
                            int desplazamiento = os % tamanoPagina;
    
                            String color = "r";
                            if(s==1){
                                color = "g";
                            } else if(s==2){
                                color = "b";
                            }
                            referencias.append("Imagen[").append(fila).append("][").append(columna).append("].")
                                        .append(color).append(",").append(pagina).append(",")
                                        .append(desplazamiento).append(",R\n");
                            numReferencias++;
                        }
                        int filaSobel = ki + 1;
                        int columnaSobel = kj + 1;
                
                        // Calcular la posición de memoria en la matriz 3x3
                        int posX = offsetSobelX + (filaSobel * 3 + columnaSobel) * 4;
                        int posY = offsetSobelY + (filaSobel * 3 + columnaSobel) * 4;
                
                        int paginaX = posX / tamanoPagina;
                        int desplazamientoX = posX % tamanoPagina;
                
                        int paginaY = posY / tamanoPagina;
                        int desplazamientoY = posY % tamanoPagina;
                
                        // Almacenar referencias correctas
                        for (int s = 0; s < 3; s++) {
                            referencias.append("SOBEL_X[").append(filaSobel).append("][").append(columnaSobel)
                                        .append("],").append(paginaX).append(",")
                                        .append(desplazamientoX).append(",R\n");
                            numReferencias++;
                        }
                        for (int s = 0; s < 3; s++) {
                            referencias.append("SOBEL_Y[").append(filaSobel).append("][").append(columnaSobel)
                                        .append("],").append(paginaY).append(",")
                                        .append(desplazamientoY).append(",R\n");
                            numReferencias++;
                        }

                        for(int l=0; l<3; l++){
                            int pos = offsetOut + (i * imagen.ancho * 3) + (j * 3) + l;
                            int pagina = pos / tamanoPagina;
                            int desplazamiento = pos % tamanoPagina;
        
                            String color = "r";
                            if(l==1){
                                color = "g";
                            } else if(l==2){
                                color = "b";
                            }
                            referencias.append("RTA[").append(i).append("][").append(j).append("].")
                                        .append(color).append(",").append(pagina).append(",")
                                        .append(desplazamiento).append(",R\n");
                        }
                        // Aplicar las máscaras de Sobel a cada canal de color
                        int pesoX = SOBEL_X[filaSobel][columnaSobel];
                        int pesoY = SOBEL_Y[filaSobel][columnaSobel];
    
                        gradXRed += red * pesoX;
                        gradXGreen += green * pesoX;
                        gradXBlue += blue * pesoX;
    
                        gradYRed += red * pesoY;
                        gradYGreen += green * pesoY;
                        gradYBlue += blue * pesoY;
                    }
                }
                // Calcular la magnitud del gradiente
                int red = Math.min(Math.max((int) Math.sqrt(gradXRed * gradXRed + gradYRed * gradYRed), 0), 255);
                int green = Math.min(Math.max((int) Math.sqrt(gradXGreen * gradXGreen + gradYGreen * gradYGreen), 0), 255);
                int blue = Math.min(Math.max((int) Math.sqrt(gradXBlue * gradXBlue + gradYBlue * gradYBlue), 0), 255);
    
                // Guardar los valores en la imagen de salida
                imagenOut.imagen[i][j][0] = (byte) red;
                imagenOut.imagen[i][j][1] = (byte) green;
                imagenOut.imagen[i][j][2] = (byte) blue;
                
            }
        }
        
        int posDeNR = referencias.indexOf("NR=algo");
        referencias.replace(posDeNR, posDeNR + "NR=algo".length(), "NR=" + numReferencias);

        String nombreSalida = "referencias_" + new java.io.File(this.nombreArchivo).getName().replace(".bmp", "") + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreSalida))) {
            writer.print(referencias.toString());  // Escribir directamente el contenido
            System.out.println("Archivo de referencias generado: " + nombreSalida);
        } catch (Exception e) {
            System.err.println("Error generando las referencias: " + e.getMessage());
            e.printStackTrace();
        }

        return referencias.toString();
    }
    
    
}




