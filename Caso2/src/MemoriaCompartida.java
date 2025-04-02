import java.util.ArrayList;
import java.util.Iterator;

public class MemoriaCompartida {
    private ArrayList<Pagina> memoria;
    private int maxMarcos;

    public MemoriaCompartida(int maxMarcos) {
        this.maxMarcos = maxMarcos;
        this.memoria = new ArrayList<>();
    }

    public synchronized int getMaxMarcos() {
        return maxMarcos;
    }

    public int getTamaño() {
        return memoria.size();
    }

    public synchronized Pagina obtenerPagina(int id) {
        for (Pagina p : memoria) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public synchronized void agregarPagina(Pagina pag) {
        memoria.add(pag);
    }

    public synchronized void removerPagina(int id) {
        boolean eliminada = false;
        Iterator<Pagina> pagIterada = memoria.iterator();
        
        while (pagIterada.hasNext() && !eliminada) {
            if (pagIterada.next().getId() == id) {
                pagIterada.remove();
                eliminada = true; 
            }
        }
    }

    public synchronized Pagina seleccionRemplazo() {
        ArrayList<Pagina> ceroCero = new ArrayList<>();
        ArrayList<Pagina> unoCero = new ArrayList<>();
        ArrayList<Pagina> ceroUno = new ArrayList<>();
        ArrayList<Pagina> unoUno = new ArrayList<>();

        for (Pagina p : memoria) {
            boolean R = p.isLectura();
            boolean W = p.isEscritura();

            if (!R && !W) ceroCero.add(p);
            else if (!R && W) unoCero.add(p);
            else if (R && !W) ceroUno.add(p);
            else unoUno.add(p);
        }

        if (!ceroCero.isEmpty()) return ceroCero.get(0);
        if (!unoCero.isEmpty()) return unoCero.get(0);
        if (!ceroUno.isEmpty()) return ceroUno.get(0);
        if (!unoUno.isEmpty()) return unoUno.get(0);

        return null;
    }

    public synchronized void reiniciarBitsLectura() {
        for (Pagina pag : memoria) {
            pag.setLectura(false);
        }
    }


}
