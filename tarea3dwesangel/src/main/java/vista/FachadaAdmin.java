package vista;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.Angelvf3839.tarea3dwesangel.modelo.Credenciales;
import com.Angelvf3839.tarea3dwesangel.modelo.Ejemplar;
import com.Angelvf3839.tarea3dwesangel.modelo.Mensaje;
import com.Angelvf3839.tarea3dwesangel.modelo.Persona;
import com.Angelvf3839.tarea3dwesangel.modelo.Planta;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosCredenciales;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosEjemplar;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosMensaje;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosPersona;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosPlanta;
import com.Angelvf3839.tarea3dwesangel.servicios.Controlador;

@Component
public class FachadaAdmin {

    @Autowired
    private ServiciosPlanta servPlanta;

    @Autowired
    private ServiciosEjemplar servEjemplar;

    @Autowired
    private ServiciosMensaje servMensaje;

    @Autowired
    private ServiciosPersona servPersona;

    @Autowired
    private ServiciosCredenciales servCredenciales;

    @Autowired
    @Lazy
    private Controlador controlador;

    @Autowired
    @Lazy
    private FachadaInvitado fachadaInvitado;

    @Autowired
    @Lazy
    private FachadaPersonal fachadaPersonal;

    private Scanner in = new Scanner(System.in);

    public void menuAdmin() {
        int opcion = 0;
        do {
            System.out.println("------MENÚ DE ADMINISTRADOR------");
            System.out.println(" ");
            System.out.println("Selecciona una opción:");
            System.out.println(" ");
            System.out.println("1. GESTIÓN DE PLANTAS");
            System.out.println("2. GESTIÓN DE EJEMPLARES");
            System.out.println("3. GESTIÓN DE MENSAJES");
            System.out.println("4. GESTIÓN DE PERSONAS");
            System.out.println("5. CERRAR SESIÓN.");
            System.out.println(" ");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.out.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        menuAdminPlantas();
                        break;
                    case 2:
                        menuAdminEjemplares();
                        break;
                    case 3:
                        menuAdminMensajes();
                        break;
                    case 4:
                        menuAdminPersonas();
                        break;
                    case 5:
                        controlador.cerrarSesion();
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 5);
    }

    public void menuAdminPlantas() {
        int opcion = 0;
        do {
            System.out.println("Selecciona una opción:");
            System.out.println(" ");
            System.out.println("1. Ver plantas");
            System.out.println("2. Crear nueva planta");
            System.out.println("3. Modificar datos de una planta");
            System.out.println("4. Eliminar una planta");
            System.out.println("5. Volver al menú principal");
            System.out.println(" ");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 4) {
                    System.out.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        fachadaInvitado.verTodasPlantas();
                        break;
                    case 2:
                        nuevaPlanta();
                        break;
                    case 3:
                        menuAdminModificarPlantas();
                        break;
                    case 4:
                        eliminarPlanta();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un númer.");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 5);
    }

    public void menuAdminModificarPlantas() {
        int opcion = 0;
        do {
            System.out.println("Selecciona una opción:");
            System.out.println(" ");
            System.out.println("1. Modificar nombre común");
            System.out.println("2. Modificar nombre científico");
            System.out.println("3. Volver al menú de plantas");
            System.out.println(" ");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 3) {
                    System.out.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        modificarNombreComun();
                        break;
                    case 2:
                        modificarNombreCientifico();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 3);
    }

    public void menuAdminEjemplares() {
        int opcion = 0;
        do {
            System.out.println("Selecciona una opción:");
            System.out.println(" ");
            System.out.println("1. Registrar nuevo ejemplar");
            System.out.println("2. Filtrar ejemplares por tipo de planta");
            System.out.println("3. Ver mensajes de un ejemplar");
            System.out.println("4. Borrar un ejemplar");
            System.out.println("5. Volver al menú principal");
            System.out.println(" ");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.out.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        nuevoEjemplar();
                        break;
                    case 2:
                        fachadaPersonal.filtrarEjemplaresPorTiposDePlantas();
                        break;
                    case 3:
                        verMensajesEjemplar();
                        break;
                    case 4:
                    	fachadaPersonal.borrarEjemplar();
                    	break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 5);
    }
    public void menuAdminPersonas() {
		int opcion = 0;
		do {
			System.out.println("Selecciona una opción:");
			System.out.println(" ");
			System.out.println("1. Registrar nueva persona");
			System.out.println("2. Ver todas las personas");
			System.out.println("3. Borrar una persona");
			System.out.println("4. Volver al menú principal");
			System.out.println(" ");
			try {
				opcion = in.nextInt();
				if (opcion < 1 || opcion > 4) {
					System.out.println("Opción incorrecta");
					continue;
				}
				switch (opcion) {
				case 1:
					nuevaPersona();
					break;
				case 2:
					verTodasPersonas();
					break;
				case 3:
					borrarPersona();
				}
			} catch (InputMismatchException e) {
				System.out.println("Debes ingresar un número");
				in.nextLine();
				opcion = 0;
			}
		} while (opcion != 4);
	}

    public void menuAdminMensajes() {
        int opcion = 0;
        do {
            System.out.println("Selecciona una opción:");
            System.out.println(" ");
            System.out.println("1. Nuevo mensaje");
            System.out.println("2. Ver mensajes");
            System.out.println("3. Volver al menú principal");
            System.out.println(" ");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 3) {
                    System.out.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        fachadaPersonal.nuevoMensaje();
                        break;
                    case 2:
                        menuAdminVerMensajes();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 3);
    }

    public void menuAdminVerMensajes() {
        int opcion = 0;
        do {
            System.out.println("Selecciona una opción:");
            System.out.println(" ");
            System.out.println("1. Ver todos los mensajes");
            System.out.println("2. Ver mensajes por persona");
            System.out.println("3. Ver mensajes por rango de fechas");
            System.out.println("4. Ver mensajes por tipo de planta");
            System.out.println("5. Volver al menú de mensajes");
            System.out.println(" ");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.out.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        verTodosMensajes();
                        break;
                    case 2:
                        fachadaPersonal.verMensajesPorPersona();
                        break;
                    case 3:
                        fachadaPersonal.verMensajePorFechas();
                        break;
                    case 4:
                        fachadaPersonal.verMensajePorTipoPlanta();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 5);
    }
    
    public void nuevaPlanta() {
        try {
            boolean inputValido = false;
            String codigo = "";

            while (!inputValido) {
                System.out.print("Introduce el código de la planta: ");
                codigo = in.nextLine().trim().toUpperCase();

                if (!servPlanta.validarCodigo(codigo)) {
                    System.out.println("El código no es válido. Por favor, intenta nuevamente.");
                } else if (servPlanta.codigoExistente(codigo)) {
                    System.out.println("El código ya está registrado para otra planta. Por favor, intenta con un código diferente.");
                } else {
                    inputValido = true;
                }
            }

            System.out.print("Introduce el nombre común: ");
            String nombreComun = in.nextLine().trim();
            System.out.print("Introduce el nombre científico: ");
            String nombreCientifico = in.nextLine().trim();

            Planta nuevaPlanta = new Planta(codigo, nombreComun, nombreCientifico);

            if (!servPlanta.validarPlanta(nuevaPlanta)) {
                System.out.println("Los datos de la planta no son válidos.");
                return;
            }

            servPlanta.insertar(nuevaPlanta);
            System.out.println("Planta creada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al crear la planta: " + e.getMessage());
        }
    }
    
    public void eliminarPlanta() {
        try {
            List<Planta> plantas = servPlanta.verTodas();
            if (plantas.isEmpty()) {
                System.out.println("No hay plantas registradas en el sistema.");
                return;
            }

            System.out.println("Lista de plantas disponibles:");
            for (int i = 0; i < plantas.size(); i++) {
                System.out.println((i + 1) + ". Código: " + plantas.get(i).getCodigo()
                                   + " | Nombre común: " + plantas.get(i).getNombreComun());
            }

            System.out.print("Introduce el número de la planta a eliminar o su código: ");
            String entrada = in.nextLine().trim(); 

            Planta plantaSeleccionada = null;

            if (entrada.matches("\\d+")) { 
                int indice = Integer.parseInt(entrada) - 1; 
                if (indice >= 0 && indice < plantas.size()) {
                    plantaSeleccionada = plantas.get(indice);
                } else {
                    System.out.println("El número introducido no corresponde a ninguna planta.");
                    return;
                }
            } else { 
                plantaSeleccionada = plantas.stream()
                                            .filter(p -> p.getCodigo().trim().equalsIgnoreCase(entrada))
                                            .findFirst()
                                            .orElse(null);
                if (plantaSeleccionada == null) {
                    System.out.println("No existe ninguna planta con ese código.");
                    return;
                }
            }

            servPlanta.eliminarPlanta(plantaSeleccionada.getId());
            System.out.println("Planta eliminada con éxito: " + plantaSeleccionada.getNombreComun());
        } catch (Exception e) {
            System.out.println("Error al eliminar la planta: " + e.getMessage());
        }
    }






    public void nuevoEjemplar() {
        try {
            List<Planta> plantas = servPlanta.verTodas();
            if (plantas.isEmpty()) {
                System.out.println("No hay plantas registradas en el sistema.");
                return;
            }

            System.out.println("Lista de plantas disponibles:");
            System.out.println();
            for (int i = 0; i < plantas.size(); i++) {
                System.out.println((i + 1) + ". Código: " + plantas.get(i).getCodigo()
                                   + " | Nombre común: " + plantas.get(i).getNombreComun());
            }

            System.out.println();
            System.out.print("Selecciona el número de la planta para registrar el ejemplar: ");
            int seleccion = in.nextInt();
            in.nextLine();

            if (seleccion < 1 || seleccion > plantas.size()) {
                System.out.println("Selección no válida.");
                System.out.println();
                return;
            }

            Planta plantaSeleccionada = plantas.get(seleccion - 1);

            Ejemplar nuevoEjemplar = new Ejemplar();
            nuevoEjemplar.setPlanta(plantaSeleccionada);

            String nombreEjemplar = plantaSeleccionada.getCodigo() + "_EJ" + System.currentTimeMillis();
            nuevoEjemplar.setNombre(nombreEjemplar);

            if (!servEjemplar.validarEjemplar(nuevoEjemplar)) {
                System.out.println("El ejemplar no es válido. Verifica los datos e intenta nuevamente.");
                System.out.println();
                return;
            }

            servEjemplar.insertar(nuevoEjemplar);
            System.out.println("Ejemplar registrado con éxito. Nombre: " + nombreEjemplar);
            System.out.println();

        } catch (Exception e) {
            System.out.println("Error al registrar el nuevo ejemplar: " + e.getMessage());
            System.out.println();
        }
    }


    public void nuevaPersona() {
        try {
            in.nextLine(); 

            System.out.print("Introduce el nombre de la persona: ");
            String nombre = in.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
                return;
            }

            System.out.print("Introduce el email: ");
            String email = in.nextLine().trim();
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                System.out.println("El email no tiene un formato válido.");
                return;
            }
            if (servPersona.emailExistente(email)) {
                System.out.println("El email ya está registrado en el sistema.");
                return;
            }

            System.out.print("Introduce el nombre de usuario de la persona: ");
            String usuario = in.nextLine().trim();
            if (usuario.contains(" ")) {
                System.out.println("El nombre de usuario no puede contener espacios.");
                return;
            }
            if (servCredenciales.usuarioExistente(usuario)) {
                System.out.println("El nombre de usuario ya está registrado en el sistema.");
                return;
            }

            System.out.print("Introduce la contraseña: ");
            String password = in.nextLine().trim();
            if (!servCredenciales.validarContraseña(password)) {
                System.out.println("La contraseña no cumple con los requisitos mínimos.");
                return;
            }

            Persona nuevaPersona = new Persona();
            nuevaPersona.setNombre(nombre);
            nuevaPersona.setEmail(email);

            Credenciales credenciales = new Credenciales();
            credenciales.setUsuario(usuario);
            credenciales.setPassword(password);
            credenciales.setPersona(nuevaPersona);

            nuevaPersona.setCredenciales(credenciales);

            if (!servPersona.validarPersona(nuevaPersona)) {
                System.out.println("Los datos de la persona no son válidos.");
                return;
            }

            servPersona.insertar(nuevaPersona);
            System.out.println("Persona registrada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al registrar la persona: " + e.getMessage());
        }
    }

    public void verTodasPersonas() {
        ArrayList<Persona> personas = (ArrayList<Persona>) servPersona.verTodos();
        if (personas == null || personas.isEmpty()) {
            System.out.println("No hay personas registradas en el sistema.");
            return;
        }

        System.out.println("Lista de todas las personas:");
        System.out.println();
        for (Persona persona : personas) {
            System.out.println(persona);
            System.out.println();
        }
    }


    public void verTodosMensajes() {
        ArrayList<Mensaje> mensajes = (ArrayList<Mensaje>) servMensaje.verTodos();
        if (mensajes == null || mensajes.isEmpty()) {
            System.out.println("No hay mensajes registrados en el sistema.");
            return;
        }

        System.out.println("Lista de todos los mensajes:");
        System.out.println();
        for (Mensaje mensaje : mensajes) {
            System.out.println(mensaje);
            System.out.println();
        }
    }


    public void modificarNombreComun() {
        try {
            List<Planta> plantas = servPlanta.verTodas();
            if (plantas.isEmpty()) {
                System.out.println("No hay plantas registradas en el sistema.");
                return;
            }

            System.out.println("Lista de plantas disponibles:");
            for (int i = 0; i < plantas.size(); i++) {
                System.out.println((i + 1) + ". Código: " + plantas.get(i).getCodigo().trim()
                                   + " | Nombre común: " + plantas.get(i).getNombreComun());
            }

            System.out.print("Introduce el código de la planta: ");
            String codigo = in.nextLine().trim();

            Planta plantaSeleccionada = plantas.stream()
                                               .filter(p -> p.getCodigo().trim().equalsIgnoreCase(codigo))
                                               .findFirst()
                                               .orElse(null);

            if (plantaSeleccionada == null) {
                System.out.println("No existe una planta con ese código.");
                return;
            }

            System.out.print("Introduce el nuevo nombre común: ");
            String nuevoNombreComun = in.nextLine().trim();

            if (nuevoNombreComun.isEmpty()) {
                System.out.println("El nombre común no puede estar vacío.");
                return;
            }

            boolean actualizado = servPlanta.actualizarNombreComun(plantaSeleccionada.getCodigo().trim(), nuevoNombreComun);

            if (actualizado) {
                System.out.println("Nombre común actualizado con éxito a: " + nuevoNombreComun);
            } else {
                System.out.println("Error: No se pudo actualizar el nombre común.");
            }
        } catch (Exception e) {
            System.out.println("Error al modificar el nombre común: " + e.getMessage());
        }
    }

	public void modificarNombreCientifico() {
	    try {
	        System.out.print("Introduce el código de la planta: ");
	        String codigo = in.nextLine().trim().toUpperCase();

	        if (!servPlanta.codigoExistente(codigo)) {
	            System.out.println("No existe una planta con ese código.");
	            return;
	        }

	        System.out.print("Introduce el nuevo nombre científico: ");
	        String nuevoNombreCientifico = in.nextLine().trim();

	        if (servPlanta.actualizarNombreCientifico(codigo, nuevoNombreCientifico)) {
	            System.out.println("Nombre científico actualizado con éxito.");
	        } else {
	            System.out.println("Error al actualizar el nombre científico.");
	        }
	    } catch (Exception e) {
	        System.out.println("Error al modificar el nombre científico: " + e.getMessage());
	    }
	}

	public void verMensajesEjemplar() {
	    try {
	        Collection<Ejemplar> ejemplares = servEjemplar.verTodos();
	        if (ejemplares.isEmpty()) {
	            System.out.println("No hay ejemplares registrados en el sistema.");
	            return;
	        }

	        System.out.println("Lista de ejemplares disponibles:");
	        for (Ejemplar ejemplar : ejemplares) {
	            System.out.println("ID: " + ejemplar.getId() + " | Nombre: " + ejemplar.getNombre());
	        }

	        System.out.print("Introduce el ID del ejemplar para ver sus mensajes: ");
	        long idEjemplar = in.nextLong();
	        in.nextLine(); 

	        Ejemplar ejemplarSeleccionado = servEjemplar.buscarPorID(idEjemplar);
	        if (ejemplarSeleccionado == null) {
	            System.out.println("No existe un ejemplar con el ID proporcionado.");
	            return;
	        }

	        List<Mensaje> mensajes = servMensaje.verMensajesPorEjemplar(idEjemplar);
	        if (mensajes.isEmpty()) {
	            System.out.println("No hay mensajes asociados al ejemplar seleccionado.");
	        } else {
	            System.out.println("Mensajes del ejemplar: " + ejemplarSeleccionado.getNombre());
	            System.out.println("-----------------------------------------------------");

	            mensajes.stream()
	                    .sorted(Comparator.comparing(Mensaje::getFechaHora))
	                    .forEach(mensaje -> {
	                        System.out.println("Fecha/Hora: " + mensaje.getFechaHora());
	                        System.out.println("Persona: " + mensaje.getPersona().getNombre());
	                        System.out.println("Mensaje: " + mensaje.getMensaje());
	                        System.out.println("-----------------------------------------------------");
	                    });
	        }
	    } catch (InputMismatchException e) {
	        System.out.println("Error: Debes introducir un número válido para el ID del ejemplar.");
	        in.nextLine(); 
	    } catch (Exception e) {
	        System.out.println("Error al ver los mensajes del ejemplar: " + e.getMessage());
	    }
	}

	public void borrarPersona() {
	    try {
	        System.out.print("Introduce el ID de la persona a borrar: ");
	        long idPersona = in.nextLong();

	        if (servPersona.eliminarPersona(idPersona)) {
	            System.out.println("Persona eliminada con éxito.");
	        } else {
	            System.out.println("No se encontró una persona con ese ID.");
	        }
	    } catch (Exception e) {
	        System.out.println("Error al borrar la persona: " + e.getMessage());
	    }
	}

}