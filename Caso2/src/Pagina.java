public class Pagina {
    private int id;
    private boolean lectura;
    private boolean escritura;

    public Pagina(int id, boolean lectura, boolean escritura) {
        this.id = id;
        this.lectura = lectura;
        this.escritura = escritura;
    }

    public int getId() {
        return id;
    }

    public boolean isLectura() {
        return lectura;
    }

    public boolean isEscritura() {
        return escritura;
    }

    public void setLectura(boolean lectura) {
        this.lectura = lectura;
    }

    public void setEscritura(boolean escritura) {
        this.escritura = escritura;
    }
}
