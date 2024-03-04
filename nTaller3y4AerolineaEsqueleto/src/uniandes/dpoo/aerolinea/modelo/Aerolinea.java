package uniandes.dpoo.aerolinea.modelo;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import uniandes.dpoo.aerolinea.exceptions.*;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.*;
import uniandes.dpoo.aerolinea.persistencia.*;

public class Aerolinea implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Avion> aviones = new ArrayList<>();
    private Map<String, Ruta> rutas = new HashMap<>();
    private List<Vuelo> vuelos = new ArrayList<>();
    private Map<String, Cliente> clientes = new HashMap<>();

    public void agregarRuta(Ruta ruta) {
        rutas.put(ruta.getCodigoRuta(), ruta);
    }

    public void agregarAvion(Avion avion) {
        aviones.add(avion);
    }

    public void agregarCliente(Cliente cliente) {
        clientes.put(cliente.getIdentificador(), cliente);
    }

    public boolean existeCliente(String identificadorCliente) {
        return clientes.containsKey(identificadorCliente);
    }

    public Cliente getCliente(String identificadorCliente) {
        return clientes.get(identificadorCliente);
    }

    public Collection<Avion> getAviones() {
        return aviones;
    }

    public Collection<Ruta> getRutas() {
        return rutas.values();
    }

    public Ruta getRuta(String codigoRuta) {
        return rutas.get(codigoRuta);
    }

    public Collection<Vuelo> getVuelos() {
        return vuelos;
    }

    public Vuelo getVuelo(String codigoRuta, String fechaVuelo) {
        return vuelos.stream()
                     .filter(vuelo -> vuelo.getRuta().getCodigoRuta().equals(codigoRuta) && vuelo.getFecha().equals(fechaVuelo))
                     .findFirst().orElse(null);
    }

    public Collection<Cliente> getClientes() {
        return clientes.values();
    }

    public Collection<Tiquete> getTiquetes() {
        List<Tiquete> tiquetes = new ArrayList<>();
        vuelos.forEach(vuelo -> tiquetes.addAll(vuelo.getTiquetes()));
        return tiquetes;
    }

    public void cargarAerolinea(String archivo, String tipoArchivo) throws TipoInvalidoException, IOException, InformacionInconsistenteException, ClassNotFoundException {
        IPersistenciaAerolinea cargador = CentralPersistencia.getPersistenciaAerolinea(tipoArchivo);
        cargador.cargarAerolinea(archivo, this);
    }

    public void salvarAerolinea(String archivo, String tipoArchivo) throws TipoInvalidoException, IOException {
        IPersistenciaAerolinea cargador = CentralPersistencia.getPersistenciaAerolinea(tipoArchivo);
        cargador.salvarAerolinea(archivo, this);
    }

    public void cargarTiquetes(String archivo, String tipoArchivo) throws TipoInvalidoException, IOException, InformacionInconsistenteException {
        IPersistenciaTiquetes cargador = CentralPersistencia.getPersistenciaTiquetes(tipoArchivo);
        cargador.cargarTiquetes(archivo, this);
    }

    public void salvarTiquetes(String archivo, String tipoArchivo) throws TipoInvalidoException, IOException {
        IPersistenciaTiquetes cargador = CentralPersistencia.getPersistenciaTiquetes(tipoArchivo);
        cargador.salvarTiquetes(archivo, this);
    }

    public void programarVuelo(String fecha, String codigoRuta, String nombreAvion) throws Exception {
        Ruta rutaVuelo = rutas.get(codigoRuta);
        Avion avionVuelo = aviones.stream().filter(avion -> avion.getNombre().equals(nombreAvion)).findFirst().orElseThrow(() -> new Exception("Avión no encontrado."));
        Vuelo nuevoVuelo = new Vuelo(rutaVuelo, fecha, avionVuelo);
        vuelos.add(nuevoVuelo);
    }

    public int venderTiquetes(String identificadorCliente, String fecha, String codigoRuta, int cantidad) throws VueloSobrevendidoException, Exception {
        Vuelo vueloBuscado = getVuelo(codigoRuta, fecha);
        Cliente clienteBuscado = getCliente(identificadorCliente);
        CalculadoraTarifas calculadora = obtenerCalculadora(fecha);
        return vueloBuscado.venderTiquetes(clienteBuscado, calculadora, cantidad);
    }

    private CalculadoraTarifas obtenerCalculadora(String fecha) {
        String[] fechaArr = fecha.split(",");
        String mes = fechaArr[1];
        return (mes.equals("12") || mes.equals("06") || mes.equals("07") || mes.equals("08")) ?
                new CalculadoraTarifasTemporadaAlta() :
                new CalculadoraTarifasTemporadaBaja();
    }

    public void registrarVueloRealizado(String fecha, String codigoRuta) {
        Vuelo vueloBuscado = getVuelo(codigoRuta, fecha);
        clientes.values().forEach(cliente -> cliente.usarTiquetes(vueloBuscado));
    }

    public String consultarSaldoPendienteCliente(String identificadorCliente) {
        Cliente clienteBuscado = getCliente(identificadorCliente);
        return Integer.toString(clienteBuscado.calcularValorTotalTiquetes());
    }
}
