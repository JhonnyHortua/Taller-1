package uniandes.dpoo.aerolinea.modelo;

import java.io.Serializable;

public class Ruta implements Serializable {
    private static final long serialVersionUID = 1L;

    private Aeropuerto origen;
    private Aeropuerto destino;
    private String horaSalida;
    private String horaLlegada;
    private String codigoRuta;

    public Ruta(Aeropuerto origen, Aeropuerto destino, String horaSalida, String horaLlegada, String codigoRuta) {
        this.origen = origen;
        this.destino = destino;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.codigoRuta = codigoRuta;
    }

    public Aeropuerto getOrigen() {
        return origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public String getCodigoRuta() {
        return codigoRuta;
    }

    public int getDuracion() {
        int salidaHoras = Integer.parseInt(horaSalida.substring(0, 2));
        int salidaMins = Integer.parseInt(horaSalida.substring(2));
        int llegadaHoras = Integer.parseInt(horaLlegada.substring(0, 2));
        int llegadaMins = Integer.parseInt(horaLlegada.substring(2));

        if (salidaHoras > llegadaHoras) {
            salidaHoras += 24;
        }

        int horasTotalesEnMinutos = Math.abs(llegadaHoras - salidaHoras) * 60;

        return horasTotalesEnMinutos + salidaMins + llegadaMins;
    }
}
