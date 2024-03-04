package uniandes.dpoo.aerolinea.modelo.cliente;

import org.json.JSONObject;

/**
 * Clase que representa clientes corporativos de la aerolínea.
 */
public class ClienteCorporativo extends Cliente {
    private static final long serialVersionUID = 1L;
    public static final String CORPORATIVO = "Corporativo";
    public static final int GRANDE = 1;
    public static final int MEDIANA = 2;
    public static final int PEQUENA = 3;

    private String nombreEmpresa;
    private int tamanoEmpresa;

    public ClienteCorporativo(String nombreEmpresa, int tamano) {
        super();
        this.nombreEmpresa = nombreEmpresa;
        tamanoEmpresa = tamano;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public int getTamanoEmpresa() {
        return tamanoEmpresa;
    }

    public String getTipoCliente() {
        return CORPORATIVO;
    }

    public String getIdentificador() {
        return nombreEmpresa;
    }

    public static ClienteCorporativo cargarDesdeJSON(JSONObject cliente) {
        String nombreEmpresa = cliente.getString("nombreEmpresa");
        int tam = cliente.getInt("tamanoEmpresa");
        return new ClienteCorporativo(nombreEmpresa, tam);
    }

    public JSONObject salvarEnJSON() {
        JSONObject jobject = new JSONObject();
        jobject.put("nombreEmpresa", this.nombreEmpresa);
        jobject.put("tamanoEmpresa", this.tamanoEmpresa);
        jobject.put("tipo", CORPORATIVO);
        return jobject;
    }
}
