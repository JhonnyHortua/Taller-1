package uniandes.dpoo.aerolinea.consola;

import java.io.IOException;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.persistencia.CentralPersistencia;
import uniandes.dpoo.aerolinea.persistencia.TipoInvalidoException;

public class ConsolaAerolinea extends ConsolaBasica {
    private Aerolinea unaAerolinea;

    public void correrAplicacion(ConsolaAerolinea ca) {
        try {
            if (unaAerolinea == null) {
                unaAerolinea = new Aerolinea();
            }

            String menuInput = pedirCadenaAlUsuario(
                "\n<<< Bienvenido al menú principal >>>\n" +
                "\n" +
                "1- Cargar aerolínea\n" +
                "2- Salvar aerolínea\n" +
                "3- Cargar tiquetes\n" +
                "4- Salvar tiquetes\n" +
                "5- Programar vuelo\n" +
                "6- Vender tiquetes\n" +
                "7- Registrar vuelo realizado\n" +
                "8- Consultar saldo pendiente cliente\n" +
                "0- Salir\n" +
                "\n" +
                "Elija una opción"
            );

            switch (menuInput) {
                case "0":
                    break;
                case "1":
                    cargarAerolinea(ca);
                    break;
                case "2":
                    salvarAerolinea(ca);
                    break;
                case "3":
                    cargarTiquetes(ca);
                    break;
                case "4":
                    salvarTiquetes(ca);
                    break;
                case "5":
                    programarVuelo(ca);
                    break;
                case "6":
                    venderTiquetes(ca);
                    break;
                case "7":
                    registrarVueloRealizado(ca);
                    break;
                case "8":
                    consultarSaldoPendienteCliente(ca);
                    break;
                default:
                    System.out.println("\nOpción inválida");
                    ca.correrAplicacion(ca);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarAerolinea(ConsolaAerolinea ca) throws Exception {
        String archivoCargarAerolinea = pedirCadenaAlUsuario("\nIngrese nombre del archivo serial con la información de una aerolínea");
        unaAerolinea.cargarAerolinea("./datos/" + archivoCargarAerolinea, CentralPersistencia.SERIAL);
        System.out.println("\nArchivo cargado (deserializado)");
        ca.correrAplicacion(ca);
    }

    private void salvarAerolinea(ConsolaAerolinea ca) throws Exception {
        String archivoSalvarAerolinea = pedirCadenaAlUsuario("\nIngrese nombre del archivo serial para salvar");
        unaAerolinea.salvarAerolinea("./datos/" + archivoSalvarAerolinea, CentralPersistencia.SERIAL);
        System.out.println("\nArchivo guardado (serializado)");
        ca.correrAplicacion(ca);
    }

    private void cargarTiquetes(ConsolaAerolinea ca) throws Exception {
        String archivoCargarTiquetes = pedirCadenaAlUsuario("\nIngrese nombre del archivo json con la información de los tiquetes");
        unaAerolinea.cargarTiquetes("./datos/" + archivoCargarTiquetes, CentralPersistencia.JSON);
        System.out.println("\nArchivo cargado (desde JSON)");
        ca.correrAplicacion(ca);
    }

    private void salvarTiquetes(ConsolaAerolinea ca) throws Exception {
        String archivoSalvarTiquetes = pedirCadenaAlUsuario("\nIngrese nombre del archivo Json para salvar");
        unaAerolinea.salvarAerolinea("./datos/" + archivoSalvarTiquetes, CentralPersistencia.JSON);
        System.out.println("\nArchivo guardado (JSON)");
        ca.correrAplicacion(ca);
    }

    private void programarVuelo(ConsolaAerolinea ca) throws Exception {
        String fechaV = pedirCadenaAlUsuario("\nIngrese fecha del vuelo");
        String codigoRutaV = pedirCadenaAlUsuario("\nIngrese codigo de la ruta del vuelo");
        String nombreAvion = pedirCadenaAlUsuario("\nIngrese nombre del avión");
        unaAerolinea.programarVuelo(fechaV, codigoRutaV, nombreAvion);
        System.out.println("\nVuelo programado");
        ca.correrAplicacion(ca);
    }

    private void venderTiquetes(ConsolaAerolinea ca) throws Exception {
        String idCliente = pedirCadenaAlUsuario("\nIngrese identificador del cliente");
        String fechaC = pedirCadenaAlUsuario("\nIngrese fecha del vuelo");
        String codigoRutaC = pedirCadenaAlUsuario("\nIngrse codigo de la ruta del vuelo");
        int cantidad = pedirEnteroAlUsuario("\nIngrse cantidad de tiquetes a vender");
        int tarifa = unaAerolinea.venderTiquetes(idCliente, fechaC, codigoRutaC, cantidad);
        System.out.println("\nTiquetes vendidos por " + tarifa + " pesos.");
        ca.correrAplicacion(ca);
    }

    private void registrarVueloRealizado(ConsolaAerolinea ca) throws Exception {
        String fechaR = pedirCadenaAlUsuario("\nIngrese fecha del vuelo");
        String codigoRutaR = pedirCadenaAlUsuario("\nIngrese codigo de la ruta del vuelo");
        unaAerolinea.registrarVueloRealizado(fechaR, codigoRutaR);
        System.out.println("\nVuelo registrado como realizado");
        ca.correrAplicacion(ca);
    }

    private void consultarSaldoPendienteCliente(ConsolaAerolinea ca) throws Exception {
        String idClienteS = pedirCadenaAlUsuario("\nIngrese identificador del cliente");
        String saldo = unaAerolinea.consultarSaldoPendienteCliente(idClienteS);
        System.out.println("\nEl saldo pendiente del cliente " + idClienteS + " es " + saldo);
        ca.correrAplicacion(ca);
    }

    public static void main(String[] args) {
        ConsolaAerolinea ca = new ConsolaAerolinea();
        ca.correrAplicacion(ca);
    }
}
