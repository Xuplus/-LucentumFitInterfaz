package lucentum.com;

/**
 * Created by Sergio on 05/05/2016.
 */
public class Usuario {

    String Nombre;
    String Usuario;
    String Pass;
    String Correo;
    String Ciudad;
    String Pais;
    int Edad;
    int Altura;
    int Peso;
    String Imagen;

    //Constructor Completo
    public Usuario(String nom, String usu, String pas, String corr, String ciu, String pa, int ed, int alt, int pes, String img)
    {
        Nombre=nom;
        Usuario=usu;
        Pass=pas;
        Correo=corr;
        Ciudad=ciu;
        Pais=pa;
        Edad=ed;
        Altura=alt;
        Peso=pes;
        Imagen=img;

    }

    //Constructor Nombre, usuario y pass

    public Usuario(String usu, String corr, String pas)
    {
        Usuario=usu;
        Correo=corr;
        Pass=pas;
    }

    public String getNombre()
    {
        return Nombre;
    }

    public String getUsuario()
    {
        return Usuario;
    }

    public String getCorreo() {return Correo; }

    public String getPass()
    {
        return Pass;
    }


}
