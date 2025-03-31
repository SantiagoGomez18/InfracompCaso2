import java.util.ArrayList;

public class MarcoPaginas {
    private int cantidad;
    private ArrayList<Pagina> paginas;

    public MarcoPaginas(int cantidad) {
        this.cantidad = cantidad;
        this.paginas = new ArrayList<>();
    }

    public int getCantidad() {
        return cantidad;
    }

    public ArrayList<Pagina> getPaginas() {
        return paginas;
    }
    
    public void addPag(Pagina pag){
        paginas.add(pag);
    }

    public void size(){
        System.out.println("El tamaño de la lista es: " + paginas.size());
    }
    
}
