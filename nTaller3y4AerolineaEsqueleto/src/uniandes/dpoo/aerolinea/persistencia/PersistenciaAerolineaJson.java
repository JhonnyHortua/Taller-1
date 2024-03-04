package uniandes.dpoo.aerolinea.persistencia;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;

public class PersistenciaAerolineaJson implements IPersistenciaAerolinea {
    @Override
    public void cargarAerolinea(String archivo, Aerolinea aerolinea) throws IOException, InformacionInconsistenteException {
        String jsonCompleto = new String(Files.readAllBytes(Paths.get(archivo)));
        JSONObject raiz = new JSONObject(jsonCompleto);

        JSONArray jAviones = raiz.getJSONArray("aviones");
        JSONArray jRutas = raiz.getJSONArray("rutas");
        JSONArray jVuelos = raiz.getJSONArray("vuelos");

        if (jAviones.isEmpty() || jRutas.isEmpty() || jVuelos.isEmpty()) {
            throw new InformacionInconsistenteException("El JSON de la aerolínea está incompleto");
        }

        for (int i = 0; i < jAviones.length(); i++) {
            JSONObject avion = jAviones.getJSONObject(i);
            String nombreAvion = avion.getString("nombre");
            int capacidadAvion = avion.getInt("capacidad");
            Avion nuevoAvionObj = new Avion(nombreAvion, capacidadAvion);
            aerolinea.agregarAvion(nuevoAvionObj);
        }

        for (int i = 0; i < jRutas.length(); i++) {
            JSONObject ruta = jRutas.getJSONObject(i);
            Aeropuerto origenRuta = (Aeropuerto) ruta.get("origen");
            Aeropuerto destinoRuta = (Aeropuerto) ruta.get("destino");
            String llegadaRuta = ruta.getString("horaLlegada");
            String salidaRuta = ruta.getString("horaSalida");
            String codigoRuta = ruta.getString("codigoRuta");
            Ruta nuevoRutaObj = new Ruta(origenRuta, destinoRuta, llegadaRuta, salidaRuta, codigoRuta);
            aerolinea.agregarRuta(nuevoRutaObj);
        }

        for (int i = 0; i < jVuelos.length(); i++) {
            JSONObject vuelo = jVuelos.getJSONObject(i);
            Ruta rutaVuelo = (Ruta) vuelo.get("ruta");
            String fechaVuelo = vuelo.getString("fecha");
            Avion avionVuelo = (Avion) vuelo.get("avion");
            String codigoRuta = rutaVuelo.getCodigoRuta();
            String nombreAvion = avionVuelo.getNombre();
            aerolinea.programarVuelo(fechaVuelo, codigoRuta, nombreAvion);
        }
    }

    @Override
    public void salvarAerolinea(String archivo, Aerolinea aerolinea) throws IOException {
        JSONObject jobject = new JSONObject();
        // Aquí deberías crear la estructura JSON adecuada con los datos de la aerolínea
        // Por favor, completa esta parte según la estructura necesaria
        PrintWriter pw = new PrintWriter(archivo);
        jobject.write(pw, 2, 0);
        pw.close();
    }
}
