package uniandes.dpoo.aerolinea.persistencia;

@SuppressWarnings("serial")
public class TipoInvalidoException extends Exception {
    public TipoInvalidoException(String tipoArchivo) {
        super("El tipo de archivo '" + tipoArchivo + "' no es válido para la persistencia");
    }
}
