package uniandes.dpoo.aerolinea.persistencia;

public class CentralPersistencia {
    public static final String JSON = "JSON";
    public static final String PLAIN = "PlainText";
    public static final String SERIAL = "Serial";

    public static IPersistenciaAerolinea getPersistenciaAerolinea(String tipoArchivo) throws TipoInvalidoException {
        switch (tipoArchivo) {
            case SERIAL:
                return new PersistenciaAerolineaSerial();
            case JSON:
                return new PersistenciaAerolineaJson();
            case PLAIN:
                return new PersistenciaAerolineaPlaintext();
            default:
                throw new TipoInvalidoException(tipoArchivo);
        }
    }

    public static IPersistenciaTiquetes getPersistenciaTiquetes(String tipoArchivo) throws TipoInvalidoException {
        if (JSON.equals(tipoArchivo))
            return new PersistenciaTiquetesJson();
        else
            throw new TipoInvalidoException(tipoArchivo);
    }
}
