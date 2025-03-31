import java.util.ArrayList;

public class MarcoPaginas {
    private int cantidad;
    private ArrayList<Pagina> marcos;

    public MarcoPaginas(int cantidad) {
        this.cantidad = cantidad;
        this.marcos = new ArrayList<>();
    }

    public int getCantidad() {
        return cantidad;
    }

    public ArrayList<Pagina> getMarcos() {
        return marcos;
    }
    
    public void addPag(Pagina pag){
        marcos.add(pag);
    }

    public void size(){
        System.out.println("El tamaño de la lista es: " + marcos.size());
    }
    
}
