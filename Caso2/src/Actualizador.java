public class Actualizador extends Thread {
    private final MemoriaCompartida memoria;
    private boolean activo = true;

    public Actualizador(MemoriaCompartida memoria) {
        this.memoria = memoria;
    }

    public void detener() {
        activo = false;
    }

    @Override
    public void run() {
        while (activo) {
            try {
                Thread.sleep(1); 
                memoria.reiniciarBitsLectura();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
