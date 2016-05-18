package lucentum.com;

/**
 * Created by Fran on 17/05/2016.
 */
public class DatosRutas {
    private String nombre;
    private String descargas;
    private String KM;

    //falta constructor


    public DatosRutas(String nombre, String descargas, String KM) {
        this.setNombre(nombre);
        this.setDescargas(descargas);
        this.setKM(KM);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre.equals("null")){
            this.nombre="Sin datos";
        }else{
            this.nombre = nombre;
        }
    }

    public String getDescargas() {
        return descargas;
    }

    public void setDescargas(String descargas) {
        if(descargas.equals("null")){
            this.descargas="Veces descargada: Sin datos";
        }else{
            this.descargas = "Veces descargada: " + descargas;
        }
    }

    public String getKM() {
        return KM;
    }

    public void setKM(String KM) {
        if(KM.equals("null")){
            this.KM="Sin datos";
        }else{
            this.KM = KM + " KM";
        }
    }
}




