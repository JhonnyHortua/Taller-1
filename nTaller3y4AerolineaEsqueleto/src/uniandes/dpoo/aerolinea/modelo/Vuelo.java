package uniandes.dpoo.aerolinea.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class Vuelo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Avion avion;
    private String fecha;
    private Ruta ruta;
    private Map<String, Tiquete> tiquetes;

    public Vuelo(Ruta ruta, String fecha, Avion avion) {
        this.ruta = ruta;
        this.fecha = fecha;
        this.avion = avion;
        this.tiquetes = new HashMap<>();
    }

    public Avion getAvion() {
        return avion;
    }

    public String getFecha() {
        return fecha;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public Collection<Tiquete> getTiquetes() {
        return tiquetes.values();
    }

    public int venderTiquetes(Cliente cliente, CalculadoraTarifas calculadora, int cantidad) throws VueloSobrevendidoException {
        int capacidadMaxima = avion.getCapacidad();

        if (cantidad > capacidadMaxima || tiquetes.size() + cantidad > capacidadMaxima) {
            throw new VueloSobrevendidoException(this);
        }

        int valorTotal = 0;

        for (int i = 0; i < cantidad; i++) {
            int tarifaTiquete = calculadora.calcularTarifa(this, cliente);
            Tiquete tiquete = GeneradorTiquetes.generarTiquete(this, cliente, tarifaTiquete);
            GeneradorTiquetes.registrarTiquete(tiquete);
            String codigoTiquete = tiquete.getCodigo();
            tiquetes.put(codigoTiquete, tiquete);
            valorTotal += tarifaTiquete;
            cliente.agregarTiquete(tiquete);
        }

        return valorTotal;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vuelo)) {
            return false;
        }

        Vuelo other = (Vuelo) obj;
        return this.avion.equals(other.getAvion()) &&
               this.fecha.equals(other.getFecha()) &&
               this.ruta.equals(other.getRuta());
    }
}
