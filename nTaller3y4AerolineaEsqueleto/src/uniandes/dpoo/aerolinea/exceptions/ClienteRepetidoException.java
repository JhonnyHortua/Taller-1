package uniandes.dpoo.aerolinea.exceptions;

@SuppressWarnings("serial")
public class ClienteRepetidoException extends InformacionInconsistenteException 
{

    private String tipoCliente;
    private String identificador;

    public ClienteRepetidoException(String tipoCliente, String identificador) {
        super("");
        this.tipoCliente = tipoCliente;
        this.identificador = identificador;
    }

    /**
     * @return
     */
    @Override
    public String getMessage() {
        return "Cliente repetido: ya existe un cliente de tipo " + tipoCliente + " con el identificador " + identificador;
    }
}
