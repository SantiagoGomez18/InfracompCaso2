import java.lang.reflect.Array;
import java.util.ArrayList;
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

    public int buscarPag(int idPag){
        for (int i=0; i<marcos.getMarcos().size(); i++){
            if (marcos.getMarcos().get(i).getId() == idPag){
                return i;
            }
        }
        return -1;
    }

    public void actualizarMarco(Pagina pag){
        int pos = buscarPag(pag.getId());
        marcos.size();
        if(pos != -1){
            hitCounter++;
            referencias.ActualizarRefExistente(pag);
            
        }else{
            System.out.println("Entro al else");
            missCounter++;
            if(marcos.getMarcos().size() < nMarcos){
                marcos.addPag(pag);
                for (Pagina p : marcos.getMarcos()){  
                    System.out.println("Pag del marco: " + p.getId());
                }
                if(!referencias.existeReferencia(pag.getId())){
                    referencias.addReferencia(pag);
                }else{
                    referencias.ActualizarRefExistente(pag);
                }
            }else{
                int idPag = buscarPagRetirar();
                System.out.println("Se retira la pagina: " + idPag);
                int posPag = buscarPag(idPag);
                System.out.println("Dentro");
                marcos.size();

                if (posPag != -1) { 
                    marcos.getMarcos().remove(posPag);
                
                } else {
                    
                }
                marcos.addPag(pag);


                if(!referencias.existeReferencia(pag.getId())){
                    referencias.ActualizarRefExistente(pag);
                }else{
                    referencias.ActualizarRefExistente(pag);
                }
            }
        }
    }

    public int buscarPagRetirar(){
        ArrayList<Pagina> ceroCero = new ArrayList<>();
        ArrayList<Pagina> unoCero = new ArrayList<>();
        ArrayList<Pagina> ceroUno = new ArrayList<>();
        ArrayList<Pagina> unoUno = new ArrayList<>();

        for (Pagina pag : referencias.getReferencias()){
            System.out.println("Pag: " + pag.getId() + " Lectura: " + pag.isLectura() + " Escritura: " + pag.isEscritura());
            if (!pag.isLectura() && !pag.isEscritura()){
                ceroCero.add(pag);
            }if (!pag.isLectura() && pag.isEscritura()){
                ceroUno.add(pag);
            }if (pag.isLectura() && !pag.isEscritura()){
                unoCero.add(pag);
            }else{
                unoUno.add(pag);
            }
        }
        int idAEliminar = buscarPagAux(ceroCero, marcos);
        if(ceroCero.isEmpty()) System.out.println("CeroCero: 0");
        for(Pagina p : ceroCero){
            System.out.println("CeroCero: " + p.getId());
        }
        if (ceroUno.isEmpty()) System.out.println("UnoCero: 0");
        for(Pagina p : ceroUno){
            System.out.println("UnoCero: " + p.getId());
        }
        if (unoCero.isEmpty()) System.out.println("CeroUno: 0");
        for(Pagina p : unoCero){
            System.out.println("CeroUno: " + p.getId());
        }
        if (unoUno.isEmpty()) System.out.println("UnoUno: 0");
        for(Pagina p : unoUno){
            System.out.println("UnoUno: " + p.getId());
        }



        return idAEliminar;

    }

    public int buscarPagAux(ArrayList<Pagina> lista, MarcoPaginas marcos){
        for(Pagina p : lista){
            for (Pagina pag : marcos.getMarcos()){

                if (p.getId() == pag.getId()){
                    return p.getId();
                }
            }
        }
        return -1;
    }



    public int getHits() {
        return hitCounter;
    }

    public int getFallos() {
        return missCounter;
    }
    
}
