import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class GeneradorReferencias {
    private Imagen imagen;
    private int tamanoPagina;
    private String nombreArchivo;

    static final int[][] SOBEL_X = {
        {-1, 0, 1},
        {-2, 0, 2},
        {-1, 0, 1}
    };
    static final int[][] SOBEL_Y = {
        {-1, -2, -1},
        { 0,  0,  0},
        { 1,  2,  1}
    };

    public GeneradorReferencias(int tamanoPagina, String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        this.imagen = new Imagen(nombreArchivo);
        this.tamanoPagina = tamanoPagina;
    }

    public String generarReferencia() {
        StringBuilder referencias = new StringBuilder();
        referencias.append("TP=").append(tamanoPagina).append("\n");
        referencias.append("NF=").append(imagen.alto).append("\n");
        referencias.append("NC=").append(imagen.ancho).append("\n");
        referencias.append("NR=algo\n");

        int numReferencias = 0;
        int offset = 0;
        int offsetImagen = offset;
        offset += imagen.alto * imagen.ancho * 3;

        int offsetSobelX = offset;
        offset += 3 * 3 * 4;

        int offsetSobelY = offset;
        offset += 3 * 3 * 4;

        int offsetOut = offset;
        offset += imagen.alto * imagen.ancho * 3;

        int total = offset;
        int numPaginas = (int) Math.ceil((double) total / tamanoPagina);
        referencias.append("NP=").append(numPaginas).append("\n");

        for (int i = 1; i < imagen.alto - 1; i++) {
            for (int j = 1; j < imagen.ancho - 1; j++) {
                int gradXRed = 0, gradXGreen = 0, gradXBlue = 0;
                int gradYRed = 0, gradYGreen = 0, gradYBlue = 0;

                for (int ki = -1; ki <= 1; ki++) {
                    for (int kj = -1; kj <= 1; kj++) {
                        int fila = i + ki;
                        int columna = j + kj;

                        for (int s = 0; s < 3; s++) {
                            int os = offsetImagen + (fila * imagen.ancho * 3) + (columna * 3) + s;
                            int pagina = os / tamanoPagina;
                            int desplazamiento = os % tamanoPagina;
                            String color = "r";
                            if(s == 1){
                                color = "g";
                            }else if(s == 2){
                                color = "b";
                            }
                            referencias.append("Imagen[").append(fila).append("][").append(columna).append("].")
                                        .append(color).append(",").append(pagina).append(",")
                                        .append(desplazamiento).append(",R\n");
                            numReferencias++;
                        }

                        int filaSobel = ki + 1;
                        int columnaSobel = kj + 1;
                        int posX = offsetSobelX + (filaSobel * 3 + columnaSobel) * 4;
                        int posY = offsetSobelY + (filaSobel * 3 + columnaSobel) * 4;

                        for (int s = 0; s < 3; s++) {
                            referencias.append("SOBEL_X[").append(filaSobel).append("][").append(columnaSobel)
                                        .append("],").append(posX / tamanoPagina).append(",")
                                        .append(posX % tamanoPagina).append(",R\n");
                            numReferencias++;
                        }
                        for (int s = 0; s < 3; s++) {
                            referencias.append("SOBEL_Y[").append(filaSobel).append("][").append(columnaSobel)
                                        .append("],").append(posY / tamanoPagina).append(",")
                                        .append(posY % tamanoPagina).append(",R\n");
                            numReferencias++;
                        }

                        int pesoX = SOBEL_X[filaSobel][columnaSobel];
                        int pesoY = SOBEL_Y[filaSobel][columnaSobel];

                        gradXRed += imagen.imagen[fila][columna][0] * pesoX;
                        gradXGreen += imagen.imagen[fila][columna][1] * pesoX;
                        gradXBlue += imagen.imagen[fila][columna][2] * pesoX;

                        gradYRed += imagen.imagen[fila][columna][0] * pesoY;
                        gradYGreen += imagen.imagen[fila][columna][1] * pesoY;
                        gradYBlue += imagen.imagen[fila][columna][2] * pesoY;
                    }
                }

                int posOut = offsetOut + (i * imagen.ancho * 3) + (j * 3);
                for (int l = 0; l < 3; l++) {
                    referencias.append("RTA[").append(i).append("][").append(j).append("].")
                                .append((l == 0) ? "r" : (l == 1) ? "g" : "b")
                                .append(",").append((posOut + l) / tamanoPagina)
                                .append(",").append((posOut + l) % tamanoPagina).append(",W\n");
                    numReferencias++;
                }
            }
        }

        int posDeNR = referencias.indexOf("NR=algo");
        referencias.replace(posDeNR, posDeNR + "NR=algo".length(), "NR=" + numReferencias);

        String rutaSalida = "Caso2/Referencias/referencias_" + new File(this.nombreArchivo).getName().replace(".bmp", "") + ".txt";
        new File("Caso2/Referencias").mkdirs();
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaSalida))) {
            writer.print(referencias.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return referencias.toString();
    }
}
