package uniandes.dpoo.aerolinea.modelo;

import java.io.Serializable;

public class Avion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String nombre;
    private int capacidad;
    
    public Avion(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public int getCapacidad() {
        return capacidad;
    }
}
