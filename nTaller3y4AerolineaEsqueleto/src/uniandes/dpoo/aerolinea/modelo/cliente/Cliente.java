package uniandes.dpoo.aerolinea.modelo.cliente;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

/**
 * Clase abstracta que representa los clientes de la aerolínea
 */
public abstract class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Tiquete> tiquetesSinUsar;
    private List<Tiquete> tiquetesUsados;

    public Cliente() {
        tiquetesSinUsar = new ArrayList<>();
        tiquetesUsados = new ArrayList<>();
    }

    public abstract String getTipoCliente();

    public abstract String getIdentificador();

    public void agregarTiquete(Tiquete tiquete) {
        tiquetesSinUsar.add(tiquete);
    }

    public int calcularValorTotalTiquetes() {
        int total = 0;
        for (Tiquete tiquete : tiquetesSinUsar) {
            total += tiquete.getTarifa();
        }
        for (Tiquete tiquete : tiquetesUsados) {
            total += tiquete.getTarifa();
        }
        return total;
    }

    public void usarTiquetes(Vuelo vuelo) {
        Iterator<Tiquete> iterator = tiquetesSinUsar.iterator();
        while (iterator.hasNext()) {
            Tiquete tiquete = iterator.next();
            if (tiquete.getVuelo().equals(vuelo)) {
                tiquetesUsados.add(tiquete);
                tiquete.marcarComoUsado();
                iterator.remove();
            }
        }
    }
}
