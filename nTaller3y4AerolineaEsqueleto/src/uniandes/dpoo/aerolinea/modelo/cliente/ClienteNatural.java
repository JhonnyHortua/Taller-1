package uniandes.dpoo.aerolinea.modelo.cliente;

public class ClienteNatural extends Cliente {
    private static final long serialVersionUID = 1L;
    public static final String NATURAL = "Natural";
    private String nombre;

    public ClienteNatural(String nombre) {
        super();
        this.nombre = nombre;
    }

    public String getIdentificador() {
        return nombre;
    }

    public String getTipoCliente() {
        return NATURAL;
    }
}
