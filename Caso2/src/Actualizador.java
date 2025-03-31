public class Actualizador extends Thread {
    
    private MarcoReferencia marcoReferencia;
    private boolean bandera = true;

    public Actualizador(MarcoReferencia marcoReferencia) {
        this.marcoReferencia = marcoReferencia;
    }


    public void run(){
        while(bandera){
            try {
                marcoReferencia.actualizar();
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
