package uniandes.dpoo.aerolinea.tiquetes;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

import java.io.Serializable;

public class Tiquete implements Serializable {

    private static final long serialVersionUID = 1L;
    private Cliente cliente;
    private String codigo;
    private int tarifa;
    private boolean usado;
    private Vuelo vuelo;

    public Tiquete(String codigo, Vuelo vuelo, Cliente cliente, int tarifa) {
        this.codigo = codigo;
        this.vuelo = vuelo;
        this.cliente = cliente;
        this.tarifa = tarifa;
        this.usado = false;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getTarifa() {
        return tarifa;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void marcarComoUsado() {
        usado = true;
    }

    public boolean esUsado() {
        return usado;
    }
}
