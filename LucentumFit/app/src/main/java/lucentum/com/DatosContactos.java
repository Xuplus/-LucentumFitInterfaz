package lucentum.com;

/**
 * Created by Fran on 16/05/2016.
 */
public class DatosContactos {

    private String id;
    private String nombre;
    private String localidad;
    private String pais;
    //private String ranking;

    public DatosContactos(String id, String nombre, String localidad, String pais /*String ranking*/){
        this.setId(id);
        this.setNombre(nombre);
        this.setLocalidad(localidad);
        this.setPais(pais);
        //this.setRanking(ranking);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

}
