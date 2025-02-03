package vista;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.Angelvf3839.tarea3dwesangel.modelo.Ejemplar;
import com.Angelvf3839.tarea3dwesangel.modelo.Mensaje;
import com.Angelvf3839.tarea3dwesangel.modelo.Persona;
import com.Angelvf3839.tarea3dwesangel.modelo.Planta;
import com.Angelvf3839.tarea3dwesangel.servicios.Controlador;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosEjemplar;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosMensaje;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosPersona;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosPlanta;

@Component
public class FachadaPersonal {

    @Autowired
    @Lazy
    private Controlador controlador;

    @Autowired
    private ServiciosEjemplar serviciosEjemplar;

    @Autowired
    private ServiciosMensaje serviciosMensaje;

    @Autowired
    private ServiciosPersona serviciosPersona;

    @Autowired
    private ServiciosPlanta serviciosPlanta;

    @Autowired
    @Lazy
    private FachadaAdmin fachadaAdmin;

    @Autowired
    @Lazy
    private FachadaInvitado fachadaInvitado;

    private Scanner in = new Scanner(System.in);
    public void menuPersonal() {
        int opcion = 0;
        do {
            System.out.println("------MENÚ DEL PERSONAL------");
            System.out.println(" ");
            System.out.println("---- Seleccione una opción: ----");
            System.out.println(" ");
            System.out.println("1. VER TODAS LAS PLANTAS.");
            System.out.println("2. GESTIÓN DE EJEMPLARES");
            System.out.println("3. GESTIÓN DE MENSAJES");
            System.out.println("4. CERRAR SESIÓN.");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 4) {
                    System.out.println("Opción incorrecta.");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        fachadaInvitado.verTodasPlantas();
                        break;
                    case 2:
                        menuPersonalEjemplares();
                        break;
                    case 3:
                        menuPersonalMensajes();
                        break;
                    case 4:
                        controlador.cerrarSesion();
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número.");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 4);
    }

    public void menuPersonalEjemplares() {
        int opcion = 0;
        do {
            System.out.println("Seleccione una opción:");
            System.out.println(" ");
            System.out.println("1. Registrar nuevo ejemplar.");
            System.out.println("2. Filtrar ejemplares por tipo de planta.");
            System.out.println("3. Ver mensajes de un ejemplar.");
            System.out.println("4. Borrar un ejemplar.");
            System.out.println("5. Volver al menú principal.");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.out.println("Opción incorrecta.");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        fachadaAdmin.nuevoEjemplar();
                        break;
                    case 2:
                    	filtrarEjemplaresPorTiposDePlantas();
                        break;
                    case 3:
                        fachadaAdmin.verMensajesEjemplar();
                        break;
                    case 4:
                    	borrarEjemplar();
                    	break;
                    case 5:
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número.");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 4);
    }

    public void menuPersonalMensajes() {
        int opcion = 0;
        do {
            System.out.println("Seleccione una opción:");
            System.out.println(" ");
            System.out.println("1. Crear nuevo mensaje.");
            System.out.println("2. Ver todos los mensajes.");
            System.out.println("3. Ver mensajes por persona.");
            System.out.println("4. Ver mensajes por rango de fechas.");
            System.out.println("5. Ver mensajes por tipo de planta.");
            System.out.println("6. Volver al menú principal.");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 6) {
                    System.out.println("Opción incorrecta.");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        nuevoMensaje();
                        break;
                    case 2:
                        fachadaAdmin.verTodosMensajes();
                        break;
                    case 3:
                    	verMensajesPorPersona();
                        break;
                    case 4:
                    	verMensajePorFechas();
                        break;
                    case 5:
                    	verMensajePorTipoPlanta();
                        break;
                    case 6:
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número.");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 6);
    }

    public void verTodosEjemplares() {
        ArrayList<Ejemplar> ejemplares = (ArrayList<Ejemplar>) serviciosEjemplar.verTodos();
        if (ejemplares == null || ejemplares.isEmpty()) {
            System.out.println("No hay ejemplares para mostrar en la base de datos.");
            return;
        }

        System.out.println("Todos los ejemplares:");
        for (Ejemplar ejemplar : ejemplares) {
            System.out.println(ejemplar);
        }
    }

    public void nuevoMensaje() {
        try {
            System.out.print("Introduce el ID del ejemplar al que quieres agregar un mensaje: ");
            long idEjemplar = in.nextLong();
            in.nextLine(); 

            Ejemplar ejemplar = serviciosEjemplar.buscarPorID(idEjemplar);
            if (ejemplar == null) {
                System.out.println("No existe un ejemplar con el ID proporcionado.");
                return;
            }

            System.out.print("Escribe el mensaje: ");
            String textoMensaje = in.nextLine().trim();

            if (!serviciosMensaje.validarMensaje(textoMensaje)) {
                System.out.println("El mensaje no es válido (demasiado largo o vacío).");
                return;
            }

            String nombreUsuario = controlador.getUsuarioAutenticado().getUsuarioAutenticado();

            Persona persona = serviciosPersona.buscarPorNombre(nombreUsuario);
            if (persona == null) {
                System.out.println("No se encontró la persona autenticada.");
                return;
            }

            Mensaje mensaje = new Mensaje(LocalDateTime.now(), textoMensaje, persona, ejemplar);
            serviciosMensaje.insertar(mensaje);
            System.out.println("Mensaje añadido con éxito.");
        } catch (Exception e) {
            System.out.println("Error al crear el mensaje: " + e.getMessage());
        }
    }

    public void filtrarEjemplaresPorTiposDePlantas() {
        try {
            List<Planta> plantas = serviciosPlanta.verTodas();
            if (plantas.isEmpty()) {
                System.out.println("No hay plantas registradas en el sistema.");
                return;
            }

            System.out.println("Lista de plantas disponibles:");
            for (int i = 0; i < plantas.size(); i++) {
                System.out.println((i + 1) + ". Código: " + plantas.get(i).getCodigo()
                                   + " | Nombre común: " + plantas.get(i).getNombreComun());
            }

            System.out.print("Introduce los números de las plantas seleccionadas (separados por comas): ");
            String seleccion = in.nextLine().trim();

            String[] indices = seleccion.split(",");
            List<String> codigosSeleccionados = new ArrayList<>();
            for (String indice : indices) {
                int i = Integer.parseInt(indice.trim()) - 1;
                if (i >= 0 && i < plantas.size()) {
                    codigosSeleccionados.add(plantas.get(i).getCodigo());
                }
            }

            if (codigosSeleccionados.isEmpty()) {
                System.out.println("No seleccionaste ningún tipo de planta válido.");
                return;
            }

            System.out.printf("%-20s %-20s %-20s\n", "Nombre del Ejemplar", "Nº Mensajes", "Fecha/Hora Último Mensaje");
            System.out.println("----------------------------------------------------------------------");

            for (String codigo : codigosSeleccionados) {
                List<Ejemplar> ejemplares = serviciosEjemplar.ejemplaresPorTipoPlanta(codigo);

                for (Ejemplar ejemplar : ejemplares) {
                    int numeroMensajes = ejemplar.getMensajes().size();

                    LocalDateTime fechaUltimoMensaje = ejemplar.getMensajes().stream()
                            .map(Mensaje::getFechaHora)
                            .max(LocalDateTime::compareTo)
                            .orElse(null);

                    String fechaHoraStr = (fechaUltimoMensaje != null) ? fechaUltimoMensaje.toString() : "Sin mensajes";

                    System.out.printf("%-20s %-20d %-20s\n", ejemplar.getNombre(), numeroMensajes, fechaHoraStr);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Entrada inválida. Debes introducir números separados por comas.");
        } catch (Exception e) {
            System.out.println("Error al filtrar ejemplares: " + e.getMessage());
        }
    }

    public void verMensajesPorPersona() {
        try {
            List<Persona> personas = new ArrayList<>(serviciosPersona.verTodos());
            
            if (personas.isEmpty()) {
                System.out.println("No hay personas registradas en el sistema.");
                return;
            }

            System.out.println("Lista de personas disponibles:");
            for (Persona persona : personas) {
                System.out.println("ID: " + persona.getId() + " | Nombre: " + persona.getNombre());
            }

            System.out.print("Introduce el ID de la persona: ");
            long idPersona = in.nextLong();
            in.nextLine(); 

            List<Mensaje> mensajes = serviciosMensaje.verMensajesPorPersona(idPersona);
            if (mensajes.isEmpty()) {
                System.out.println("No hay mensajes asociados a la persona con ID: " + idPersona);
            } else {
                System.out.println("Mensajes de la persona con ID " + idPersona + ":");
                for (Mensaje mensaje : mensajes) {
                    System.out.println(mensaje);
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes introducir un número válido.");
            in.nextLine();
        } catch (Exception e) {
            System.out.println("Error al buscar mensajes por persona: " + e.getMessage());
        }
    }

    public void verMensajePorTipoPlanta() {
        try {
            List<Planta> plantas = serviciosPlanta.verTodas();
            if (plantas.isEmpty()) {
                System.out.println("No hay plantas registradas en el sistema.");
                return;
            }

            System.out.println("Lista de plantas disponibles:");
            for (Planta planta : plantas) {
                System.out.println("Código: " + planta.getCodigo() + " | Nombre común: " + planta.getNombreComun());
            }

            System.out.print("Introduce el código de la planta: ");
            String codigoPlanta = in.nextLine().trim().toUpperCase();

            if (!serviciosPlanta.codigoExistente(codigoPlanta)) {
                System.out.println("No existe una planta con el código proporcionado.");
                return;
            }

            List<Mensaje> mensajes = serviciosMensaje.verMensajesPorCodigoPlanta(codigoPlanta);
            if (mensajes.isEmpty()) {
                System.out.println("No hay mensajes asociados a la planta con código: " + codigoPlanta);
            } else {
                System.out.println("Mensajes asociados a la planta con código " + codigoPlanta + ":");
                for (Mensaje mensaje : mensajes) {
                    System.out.println(mensaje);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar mensajes por planta: " + e.getMessage());
        }
    }

    public void verMensajePorFechas() {
        try {
            System.out.print("Introduce la fecha inicial (formato: dd-MM-yyyy HH:mm): ");
            String fechaInicioStr = in.nextLine();
            LocalDateTime fechaInicio = LocalDateTime.parse(fechaInicioStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

            System.out.print("Introduce la fecha final (formato: dd-MM-yyyy HH:mm): ");
            String fechaFinStr = in.nextLine();
            LocalDateTime fechaFin = LocalDateTime.parse(fechaFinStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

            if (fechaFin.isBefore(fechaInicio)) {
                System.out.println("La fecha final no puede ser anterior a la fecha inicial.");
                return;
            }

            ArrayList<Mensaje> mensajes = serviciosMensaje.verMensajesRangoFechas(fechaInicio, fechaFin);
            if (mensajes.isEmpty()) {
                System.out.println("No hay mensajes en el rango de fechas especificado.");
                return;
            }

            System.out.println("Mensajes en el rango de fechas:");
            for (Mensaje mensaje : mensajes) {
                System.out.println(mensaje);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Usa el formato: dd-MM-yyyy HH:mm");
        } catch (Exception e) {
            System.out.println("Error al buscar mensajes por fechas: " + e.getMessage());
        }
    }

    
    public void borrarEjemplar() {
        try {
            System.out.print("Introduce el ID del ejemplar que quieres borrar: ");
            long idEjemplar = in.nextLong();

            boolean eliminado = serviciosEjemplar.borrarEjemplar(idEjemplar);
            if (eliminado) {
                System.out.println("Ejemplar con ID " + idEjemplar + " eliminado con éxito.");
            } else {
                System.out.println("No se encontró un ejemplar con el ID proporcionado.");
            }
        } catch (Exception e) {
            System.out.println("Error al borrar el ejemplar: " + e.getMessage());
        }
    }
}