package uniandes.dpoo.aerolinea.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;

public class PersistenciaAerolineaSerial implements IPersistenciaAerolinea {

	@Override
	public void cargarAerolinea(String archivo, Aerolinea aerolinea) throws IOException, InformacionInconsistenteException, ClassNotFoundException {
		File file = new File(archivo);
		if (file.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			aerolinea = (Aerolinea) ois.readObject();
			ois.close();
		} else {
			aerolinea = new Aerolinea();
		}
	}

	@Override
	public void salvarAerolinea(String archivo, Aerolinea aerolinea) throws IOException {
		File file = new File(archivo);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(aerolinea);
		oos.close();
	}
}
