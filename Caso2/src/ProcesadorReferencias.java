import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ProcesadorReferencias {
    private int nMarcos;
    private String nombreArchivoRef;
    private MarcoReferencia referencias;
    private MarcoPaginas marcos;
    private int missCounter;
    private int hitCounter;

    public ProcesadorReferencias(int nMarcos, String nombreArchivoRef) {
        this.nMarcos = nMarcos;
        this.nombreArchivoRef = nombreArchivoRef;
        this.marcos = new MarcoPaginas(nMarcos);
        this.referencias = new MarcoReferencia();
        this.missCounter = 0;
        this.hitCounter = 0;
    }

    public String getNombreArchivoRef() {
        return nombreArchivoRef;
    }

    public int buscarPag(int idPag) {
        for (int i = 0; i < marcos.getPaginas().size(); i++) {
            if (marcos.getPaginas().get(i).getId() == idPag) {
                return i;
            }
        }
        return -1;
    }

    public void actualizarMarco(Pagina pag) {
        int pos = buscarPag(pag.getId());

        if (pos != -1) { 
            hitCounter++;
            referencias.ActualizarRefExistente(pag);
        } else { 
            missCounter++;
            if (marcos.getPaginas().size() < nMarcos) {
                marcos.addPag(pag);
            } else {
                int idPagEliminar = buscarPagRetirar();
                removerPagina(idPagEliminar);
                marcos.addPag(pag);
            }

            if (!referencias.existeReferencia(pag.getId())) {
                referencias.addReferencia(pag);
            } else {
                referencias.ActualizarRefExistente(pag);
            }
        }

    }

    public int buscarPagRetirar() {
        ArrayList<Pagina> ceroCero = new ArrayList<>();
        ArrayList<Pagina> unoCero = new ArrayList<>();
        ArrayList<Pagina> ceroUno = new ArrayList<>();
        ArrayList<Pagina> unoUno = new ArrayList<>();

        for (Pagina pag : referencias.getReferencias()) {
            if (!pag.isLectura() && !pag.isEscritura()) {
                ceroCero.add(pag);
            } else if (!pag.isLectura() && pag.isEscritura()) {
                ceroUno.add(pag);
            } else if (pag.isLectura() && !pag.isEscritura()) {
                unoCero.add(pag);
            } else {
                unoUno.add(pag);
            }
        }

        int idAEliminar = buscarPagAux(ceroCero, marcos);
        if (idAEliminar == -1) idAEliminar = buscarPagAux(unoCero, marcos);
        if (idAEliminar == -1) idAEliminar = buscarPagAux(ceroUno, marcos);
        if (idAEliminar == -1) idAEliminar = buscarPagAux(unoUno, marcos);

        return idAEliminar;
    }

    public int buscarPagAux(ArrayList<Pagina> lista, MarcoPaginas marcos) {
        for (Pagina p : lista) {
            for (Pagina pag : marcos.getPaginas()) {
                if (p.getId() == pag.getId()) {
                    return p.getId();
                }
            }
        }
        return -1;
    }

    public boolean removerPagina(int id) {
        Iterator<Pagina> iter = marcos.getPaginas().iterator();
        while (iter.hasNext()) {
            Pagina p = iter.next();
            if (p.getId() == id) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    public int getHits() {
        return hitCounter;
    }

    public int getFallos() {
        return missCounter;
    }
}
