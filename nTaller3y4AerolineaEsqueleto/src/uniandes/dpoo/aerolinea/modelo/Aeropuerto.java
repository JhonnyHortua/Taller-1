package uniandes.dpoo.aerolinea.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import uniandes.dpoo.aerolinea.exceptions.AeropuertoDuplicadoException;

public class Aeropuerto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codigo;
    private double latitud;
    private double longitud;
    private String nombre;
    private String nombreCiudad;

    private static Set<String> codigosUtilizados = new HashSet<>();
    private static final int RADIO_TERRESTRE = 6371;

    public Aeropuerto(String nombre, String codigo, String nombreCiudad, double latitud, double longitud) throws AeropuertoDuplicadoException {
        if (codigosUtilizados.contains(codigo)) {
            throw new AeropuertoDuplicadoException(codigo);
        }
        this.codigo = codigo;
        codigosUtilizados.add(codigo);
        this.nombre = nombre;
        this.nombreCiudad = nombreCiudad;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public static int calcularDistancia(Aeropuerto aeropuerto1, Aeropuerto aeropuerto2) {
        double latAeropuerto1 = Math.toRadians(aeropuerto1.getLatitud());
        double lonAeropuerto1 = Math.toRadians(aeropuerto1.getLongitud());
        double latAeropuerto2 = Math.toRadians(aeropuerto2.getLatitud());
        double lonAeropuerto2 = Math.toRadians(aeropuerto2.getLongitud());

        double deltaX = (lonAeropuerto2 - lonAeropuerto1) * Math.cos((latAeropuerto1 + latAeropuerto2) / 2);
        double deltaY = (latAeropuerto2 - latAeropuerto1);

        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY) * RADIO_TERRESTRE;

        return (int) Math.round(distancia);
    }
}
