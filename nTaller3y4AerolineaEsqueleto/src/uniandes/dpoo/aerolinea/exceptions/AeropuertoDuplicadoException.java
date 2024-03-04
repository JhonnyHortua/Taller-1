package uniandes.dpoo.aerolinea.exceptions;

@SuppressWarnings("serial")
public class AeropuertoDuplicadoException extends Exception {

    private String codigoDuplicado;

    public AeropuertoDuplicadoException(String codigo) {
        super();
        this.codigoDuplicado = codigo;
    }

    @Override
    public String getMessage() {
        return "El código '" + codigoDuplicado + "' está duplicado en dos aeropuertos";
    }
}