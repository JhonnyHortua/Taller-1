package uniandes.dpoo.aerolinea.persistencia;

import java.io.IOException;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;

public interface IPersistenciaAerolinea {
    /**
     * Carga la información de una aerolínea desde un archivo y la actualiza en la aerolínea dada.
     * 
     * @param archivo La ruta al archivo que contiene la información.
     * @param aerolinea La aerolínea en la cual se almacenará la información.
     * @throws IOException Si ocurre un error de lectura o escritura en el archivo.
     * @throws InformacionInconsistenteException Si la información cargada es inconsistente.
     * @throws ClassNotFoundException Si la clase requerida no puede ser encontrada durante la deserialización.
     */
    void cargarAerolinea(String archivo, Aerolinea aerolinea) throws IOException, InformacionInconsistenteException, ClassNotFoundException;
    
    /**
     * Guarda la información de una aerolínea en un archivo.
     * 
     * @param archivo La ruta al archivo donde se guardará la información.
     * @param aerolinea La aerolínea que contiene la información a guardar.
     * @throws IOException Si ocurre un error de lectura o escritura en el archivo.
     */
    void salvarAerolinea(String archivo, Aerolinea aerolinea) throws IOException;
}
