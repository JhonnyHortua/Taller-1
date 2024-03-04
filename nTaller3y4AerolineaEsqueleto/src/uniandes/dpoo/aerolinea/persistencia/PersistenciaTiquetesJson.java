package uniandes.dpoo.aerolinea.persistencia;

import java.io.*;
import org.json.*;

import uniandes.dpoo.aerolinea.exceptions.*;
import uniandes.dpoo.aerolinea.modelo.*;
import uniandes.dpoo.aerolinea.modelo.cliente.*;
import uniandes.dpoo.aerolinea.tiquetes.*;

public class PersistenciaTiquetesJson implements IPersistenciaTiquetes {

    private static final String CLIENTES = "clientes";
    private static final String TIQUETES = "tiquetes";
    private static final String NOMBRE_CLIENTE = "nombre";
    private static final String TIPO_CLIENTE = "tipoCliente";
    private static final String CLIENTE = "cliente";
    private static final String USADO = "usado";
    private static final String TARIFA = "tarifa";
    private static final String CODIGO_TIQUETE = "codigoTiquete";
    private static final String FECHA = "fecha";
    private static final String CODIGO_RUTA = "codigoRuta";

    @Override
    public void cargarTiquetes(String archivo, Aerolinea aerolinea) throws IOException, InformacionInconsistenteException {
        String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
        JSONObject raiz = new JSONObject(jsonCompleto);
        cargarClientes(aerolinea, raiz.getJSONArray(CLIENTES));
        cargarTiquetes(aerolinea, raiz.getJSONArray(TIQUETES));
    }

    @Override
    public void salvarTiquetes(String archivo, Aerolinea aerolinea) throws IOException {
        JSONObject jobject = new JSONObject();
        salvarClientes(aerolinea, jobject);
        salvarTiquetes(aerolinea, jobject);
        PrintWriter pw = new PrintWriter(archivo);
        jobject.write(pw, 2, 0);
        pw.close();
    }

    private void cargarClientes(Aerolinea aerolinea, JSONArray jClientes) throws ClienteRepetidoException {
        for (int i = 0; i < jClientes.length(); i++) {
            JSONObject cliente = jClientes.getJSONObject(i);
            String tipoCliente = cliente.getString(TIPO_CLIENTE);
            Cliente nuevoCliente = null;
            if (ClienteNatural.NATURAL.equals(tipoCliente)) {
                String nombre = cliente.getString(NOMBRE_CLIENTE);
                nuevoCliente = new ClienteNatural(nombre);
            } else {
                nuevoCliente = ClienteCorporativo.cargarDesdeJSON(cliente);
            }
            if (!aerolinea.existeCliente(nuevoCliente.getIdentificador())) {
                aerolinea.agregarCliente(nuevoCliente);
            } else {
                throw new ClienteRepetidoException(nuevoCliente.getTipoCliente(), nuevoCliente.getIdentificador());
            }
        }
    }

    private void salvarClientes(Aerolinea aerolinea, JSONObject jobject) {
        JSONArray jClientes = new JSONArray();
        for (Cliente cliente : aerolinea.getClientes()) {
            JSONObject jCliente = new JSONObject();
            if (ClienteNatural.NATURAL.equals(cliente.getTipoCliente())) {
                jCliente.put(NOMBRE_CLIENTE, cliente.getIdentificador());
            } else {
                ClienteCorporativo cc = (ClienteCorporativo) cliente;
                JSONObject jsonCliente = cc.salvarEnJSON();
                jCliente.put(NOMBRE_CLIENTE, jsonCliente);
            }
            jClientes.put(jCliente);
        }
        jobject.put(CLIENTES, jClientes);
    }

    /**
     * @param aerolinea
     * @param jTiquetes
     * @throws InformacionInconsistenteTiqueteException
     */
    private void cargarTiquetes(Aerolinea aerolinea, JSONArray jTiquetes) throws InformacionInconsistenteTiqueteException {
        for (int i = 0; i < jTiquetes.length(); i++) {
            JSONObject tiquete = jTiquetes.getJSONObject(i);
            String codigoRuta = tiquete.getString(CODIGO_RUTA);
            Ruta laRuta = aerolinea.getRuta(codigoRuta);
            if (laRuta == null) {
                throw new InformacionInconsistenteTiqueteException("ruta", codigoRuta);
            }
            String fechaVuelo = tiquete.getString(FECHA);
            Vuelo elVuelo = aerolinea.getVuelo(codigoRuta, fechaVuelo);
            if (elVuelo == null) {
                throw new InformacionInconsistenteTiqueteException("vuelo", codigoRuta + " en " + fechaVuelo);
            }
            String codigoTiquete = tiquete.getString(CODIGO_TIQUETE);
            boolean existe = GeneradorTiquetes.validarTiquete(codigoTiquete);
            if (existe) {
                throw new InformacionInconsistenteTiqueteException("tiquete", codigoTiquete, false);
            }
            int tarifa = tiquete.getInt(TARIFA);
            boolean tiqueteUsado = tiquete.getBoolean(USADO);
            String identificadorCliente = tiquete.getString(CLIENTE);
            Cliente elCliente = aerolinea.getCliente(identificadorCliente);
            if (elCliente == null) {
                throw new InformacionInconsistenteTiqueteException("cliente", identificadorCliente);
            }
            Tiquete nuevoTiquete = new Tiquete(codigoTiquete, elVuelo, elCliente, tarifa);
            if (tiqueteUsado) {
                nuevoTiquete.marcarComoUsado();
            }
            GeneradorTiquetes.registrarTiquete(nuevoTiquete);
        }
    }

    private void salvarTiquetes(Aerolinea aerolinea, JSONObject jobject) {
        JSONArray jTiquetes = new JSONArray();
        for (Tiquete tiquete : aerolinea.getTiquetes()) {
            JSONObject jTiquete = new JSONObject();
            jTiquete.put(CODIGO_TIQUETE, tiquete.getCodigo());
            jTiquete.put(CODIGO_RUTA, tiquete.getVuelo().getRuta().getCodigoRuta());
            jTiquete.put(FECHA, tiquete.getVuelo().getFecha());
            jTiquete.put(TARIFA, tiquete.getTarifa());
            jTiquete.put(USADO, tiquete.esUsado());
            jTiquete.put(CLIENTE, tiquete.getCliente().getIdentificador());
            jTiquetes.put(jTiquete);
        }
        jobject.put(TIQUETES, jTiquetes);
    }
}
