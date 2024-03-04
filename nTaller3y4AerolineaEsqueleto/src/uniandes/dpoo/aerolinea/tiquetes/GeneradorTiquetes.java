package uniandes.dpoo.aerolinea.tiquetes;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import java.util.HashSet;
import java.util.Set;

public class GeneradorTiquetes {
    private static Set<String> codigos = new HashSet<>();

    public static Tiquete generarTiquete(Vuelo vuelo, Cliente cliente, int tarifa) {
        String codigo;
        do {
            codigo = generarCodigoUnico();
        } while (codigos.contains(codigo));
        codigos.add(codigo);
        return new Tiquete(codigo, vuelo, cliente, tarifa);
    }

    public static void registrarTiquete(Tiquete tiquete) {
        codigos.add(tiquete.getCodigo());
    }

    public static boolean validarTiquete(String codigoTiquete) {
        return codigos.contains(codigoTiquete);
    }

    private static String generarCodigoUnico() {
        int numero = (int) (Math.random() * 10e7);
        String codigo = String.format("%07d", numero);
        return codigo;
    }
}
