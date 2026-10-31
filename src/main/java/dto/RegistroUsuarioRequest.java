package dto;

public class RegistroUsuarioRequest {
    private String nombre;
    private String apellido;
    private String dpi;
    private String telefono;
    private String correo;
    private String password;
    private String rol;
    private String nit;

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getDpi() { return dpi; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getPassword() { return password; }
    public String getRol() { return rol; }
    public String getNit() { return nit; }
}