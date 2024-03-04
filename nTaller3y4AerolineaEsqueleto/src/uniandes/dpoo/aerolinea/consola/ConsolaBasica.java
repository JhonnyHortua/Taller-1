package uniandes.dpoo.aerolinea.consola;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;

/**
 * Clase abstracta que proporciona métodos útiles para las consolas de la aplicación.
 */
@SuppressWarnings("unused")
public abstract class ConsolaBasica {
    protected String pedirCadenaAlUsuario(String mensaje) {
        try {
            System.out.print(mensaje + ": ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error leyendo de la consola");
        }
        return "error";
    }

    protected boolean pedirConfirmacionAlUsuario(String mensaje) {
        try {
            System.out.print(mensaje + " (Responda 'si' o 'no'): ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine().toLowerCase();
            return input.equals("si") || input.equals("sí") || input.equals("s");
        } catch (IOException e) {
            System.out.println("Error leyendo de la consola");
        }
        return false;
    }

    protected int pedirEnteroAlUsuario(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + ": ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                return Integer.parseInt(reader.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("El valor digitado no es un entero");
            }
        }
    }

    protected double pedirNumeroAlUsuario(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + ": ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                return Double.parseDouble(reader.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("El valor digitado no es un número");
            }
        }
    }

    protected String pedirOpcionAlUsuario(Collection<? extends Object> coleccionOpciones) {
        String[] opciones = new String[coleccionOpciones.size()];
        int pos = 0;
        for (Object opcion : coleccionOpciones) {
            opciones[pos++] = opcion.toString();
        }

        System.out.println("Seleccione una de las siguientes opciones:");
        for (int i = 1; i <= opciones.length; i++) {
            System.out.println(" " + i + ". " + opciones[i - 1]);
        }

        while (true) {
            try {
                int opcionSeleccionada = pedirEnteroAlUsuario("Escriba el número que corresponde a la opción deseada");
                if (opcionSeleccionada > 0 && opcionSeleccionada <= opciones.length) {
                    return opciones[opcionSeleccionada - 1];
                } else {
                    System.out.println("Esa no es una opción válida. Digite solamente números entre 1 y " + opciones.length);
                }
            } catch (NumberFormatException e) {
                System.out.println("Esa no es una opción válida. Digite solamente números.");
            }
        }
    }

    protected int mostrarMenu(String nombreMenu, String[] opciones) {
        System.out.println("\n---------------------");
        System.out.println(nombreMenu);
        System.out.println("---------------------");

        for (int i = 1; i <= opciones.length; i++) {
            System.out.println(" " + i + ". " + opciones[i - 1]);
        }

        while (true) {
            try {
                int opcionSeleccionada = pedirEnteroAlUsuario("Escoja la opción deseada");
                if (opcionSeleccionada > 0 && opcionSeleccionada <= opciones.length) {
                    return opcionSeleccionada;
                } else {
                    System.out.println("Esa no es una opción válida. Digite solamente números entre 1 y " + opciones.length);
                }
            } catch (NumberFormatException e) {
                System.out.println("Esa no es una opción válida. Digite solamente números.");
            }
        }
    }
}

