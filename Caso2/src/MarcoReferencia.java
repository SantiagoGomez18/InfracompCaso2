import java.util.ArrayList;

public class MarcoReferencia {
    private ArrayList<Pagina> referencias;
    
    public MarcoReferencia() {
        this.referencias = new ArrayList<>();
    }

    public void actualizar(){
        for (Pagina pag : referencias){
            if (pag.isLectura()){
                pag.setLectura(false);
            }
        }
    }

    public void addReferencia(Pagina pag) {
        referencias.add(pag);
    }

    public void ActualizarRefExistente(Pagina pag){
        for (Pagina p : referencias){
            if (p.getId() == pag.getId()){
                p.setLectura(true);
                if (pag.isEscritura()){
                    p.setEscritura(true);
                }
            }
        }
    }

    public boolean existeReferencia(int idPag) {
        for (Pagina p : referencias) {
            if (p.getId() == idPag) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Pagina> getReferencias() {
        return referencias;
    }

}
