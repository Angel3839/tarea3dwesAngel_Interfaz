package com.Angelvf3839.tarea3dwesangel.controladores;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Angelvf3839.tarea3dwesangel.modelo.Credenciales;
import com.Angelvf3839.tarea3dwesangel.modelo.Ejemplar;
import com.Angelvf3839.tarea3dwesangel.modelo.Mensaje;
import com.Angelvf3839.tarea3dwesangel.modelo.Perfil;
import com.Angelvf3839.tarea3dwesangel.modelo.Persona;
import com.Angelvf3839.tarea3dwesangel.modelo.Planta;
import com.Angelvf3839.tarea3dwesangel.modelo.Sesion;
import com.Angelvf3839.tarea3dwesangel.repositorios.EjemplarRepository;
import com.Angelvf3839.tarea3dwesangel.repositorios.MensajeRepository;
import com.Angelvf3839.tarea3dwesangel.repositorios.PersonaRepository;
import com.Angelvf3839.tarea3dwesangel.repositorios.PlantaRepository;
import com.Angelvf3839.tarea3dwesangel.servicios.Controlador;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosCredenciales;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosEjemplar;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosMensaje;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosPersona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class MainController {

    @Autowired
    private PlantaRepository plantaRepository;
    
    @Autowired
    private PersonaRepository personaRepository;
    
    @Autowired
    private EjemplarRepository ejemplarRepository;
    
    @Autowired
    private MensajeRepository mensajesRepository;
    
    @Autowired
    @Lazy
    private Controlador controlador;
    
    @Autowired
    private ServiciosCredenciales serviciosCredenciales;
    
    @Autowired
    private ServiciosEjemplar serviciosEjemplar;

    @Autowired
    private ServiciosPersona serviciosPersona;
    
    @Autowired
    private ServiciosMensaje serviciosMensaje;
    
    @GetMapping("/verPlantas")
    public String verPlantas(Model model) {
        List<Planta> listaPlantas = plantaRepository.findAll();
        
        if (listaPlantas == null || listaPlantas.isEmpty()) {
            System.out.println("No hay plantas en la base de datos.");
        } else {
            System.out.println("Se encontraron " + listaPlantas.size() + " plantas.");
        }

        model.addAttribute("plantas", listaPlantas);
        return "verPlantas";
    }
    
    @GetMapping("/menuAdmin")
    public String menuAdmin() {
        return "menuAdmin"; 
    }
    
    @GetMapping("/menuPersonal")
    public String menuPersonal() {
        return "menuPersonal"; 
    }
    
    
    @GetMapping("/index")
    public String login(@RequestParam("usuario") String usuario, @RequestParam("password") String password) {
        try {
            boolean autenticado = serviciosCredenciales.autenticar(usuario, password);
            if (autenticado) {
                long idUsuario = serviciosPersona.idUsuarioAutenticado(usuario);
                if (idUsuario == -1) {
                    System.out.println("Error al obtener los datos del usuario.");
                    return "redirect:/login?error=datosIncorrectos";
                }

                Perfil perfil;
                if ("admin".equalsIgnoreCase(usuario)) {
                    perfil = Perfil.ADMIN;
                } else {
                    perfil = Perfil.PERSONAL;
                }

                controlador.setUsuarioAutenticado(new Sesion(idUsuario, usuario, perfil));
                System.out.println("Sesión iniciada con éxito como: " + usuario);

                if (perfil == Perfil.ADMIN) {
                    return "redirect:/menuAdmin";
                } else {
                    return "redirect:/menuPersonal";
                }
            } else {
                System.out.println("Usuario o contraseña incorrectos.");
                return "redirect:/login?error=credencialesIncorrectas";
            }
        } catch (Exception e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
            return "redirect:/login?error=errorInterno";
        }
    }


    @PostMapping("/ejemplares/guardar")
    public String guardarEjemplar(@RequestParam String nombre, @RequestParam String codigoPlanta) {
        Planta planta = plantaRepository.findByCodigo(codigoPlanta).orElse(null);

        if (planta == null) {
            System.out.println("Planta no encontrada con el código: " + codigoPlanta);
            return "redirect:/EjemplaresForm?error=PlantaNoEncontrada";
        }

        System.out.println("Planta encontrada: " + planta.getNombreComun());

        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setNombre(nombre);
        ejemplar.setPlanta(planta);

        ejemplarRepository.save(ejemplar);
        System.out.println("Ejemplar guardado correctamente.");

        return "redirect:/EjemplaresForm";
    }
    
    @GetMapping("/verEjemplares")
    public String verEjemplares(Model model) {
        List<Ejemplar> listaEjemplares = ejemplarRepository.findAll();
        model.addAttribute("ejemplares", listaEjemplares);
        return "EjemplaresForm";
    }
    
    @GetMapping("/EjemplaresForm")
    public String gestionEjemplares(Model model) {
        List<Planta> listaPlantas = plantaRepository.findAll();
        model.addAttribute("plantas", listaPlantas);

        List<Ejemplar> listaEjemplares = ejemplarRepository.findAll();
        model.addAttribute("ejemplares", listaEjemplares);

        return "EjemplaresForm";
    }

    
    
    
    @GetMapping("/PlantasForm")
    public String gestionPlantas(Model model) {
        List<Planta> listaPlantas = plantaRepository.findAll();
        model.addAttribute("plantas", listaPlantas);

        if (!model.containsAttribute("planta")) {
            model.addAttribute("planta", new Planta()); 
        }

        return "PlantasForm";
    }
    
    @PostMapping("/plantas/guardar")
    public String guardarPlanta(@RequestParam String codigo,
                                @RequestParam String nombreComun,
                                @RequestParam String nombreCientifico) {
        Planta planta = new Planta();
        planta.setCodigo(codigo);
        planta.setNombreComun(nombreComun);
        planta.setNombreCientifico(nombreCientifico);

        plantaRepository.save(planta);
        return "redirect:/PlantasForm";
    }
    

    @PostMapping("/mensajes/guardar")
    public String guardarMensaje(@RequestParam("idEjemplar") Long idEjemplar, 
                                 @RequestParam("mensajeTexto") String mensajeTexto) {
        try {
            System.out.println("Recibido ID Ejemplar: " + idEjemplar);
            System.out.println("Mensaje recibido: " + mensajeTexto);

            Ejemplar ejemplar = serviciosEjemplar.buscarPorID(idEjemplar);
            if (ejemplar == null) {
                System.out.println("No existe un ejemplar con el ID proporcionado.");
                return "redirect:/MensajesForm?error=EjemplarNoEncontrado";
            }

            if (!serviciosMensaje.validarMensaje(mensajeTexto)) {
                System.out.println("El mensaje no es válido (demasiado largo o vacío).");
                return "redirect:/MensajesForm?error=MensajeNoValido";
            }

            String nombreUsuario = controlador.getUsuarioAutenticado().getUsuarioAutenticado();

            Persona persona = serviciosPersona.buscarPorNombre(nombreUsuario);
            if (persona == null) {
                System.out.println("No se encontró la persona autenticada.");
                return "redirect:/MensajesForm?error=PersonaNoEncontrada";
            }

            Mensaje mensaje = new Mensaje(LocalDateTime.now(), mensajeTexto, persona, ejemplar);
            serviciosMensaje.insertar(mensaje);
            System.out.println("Mensaje añadido con éxito.");

            return "redirect:/MensajesForm?success=MensajeGuardado";

        } catch (Exception e) {
            System.out.println("Error al crear el mensaje: " + e.getMessage());
            return "redirect:/MensajesForm?error=ErrorGuardado";
        }
    }




    
    @GetMapping("/MensajesForm")
    public String gestionMensajes(Model model) {
    	List<Mensaje> listarMensajes = mensajesRepository.findAll();
    	model.addAttribute("mensajes", listarMensajes);
    	return "MensajesForm";
    }
 
    
    @GetMapping("/mensajes/fecha")
    public String listarMensajesPorFecha(@RequestParam("primeraFecha") String primeraFecha,
                                         @RequestParam("segundaFecha") String segundaFecha,
                                         Model model) {
        List<Mensaje> mensajes = serviciosMensaje.verMensajesRangoFechas(LocalDateTime.parse(primeraFecha), LocalDateTime.parse(segundaFecha));
        model.addAttribute("mensajesPorFecha", mensajes);
        return "MensajesForm";
    }

    @GetMapping("/mensajes/persona")
    public String listarMensajesPorPersona(@RequestParam("idPersona") Long idPersona, Model model) {
        List<Mensaje> mensajes = serviciosMensaje.verMensajesPorPersona(idPersona);
        model.addAttribute("mensajesPorPersona", mensajes);
        return "MensajesForm";
    }

    @GetMapping("/mensajes/planta")
    public String listarMensajesPorPlanta(@RequestParam("codigoPlanta") String codigoPlanta, Model model) {
        List<Mensaje> mensajes = serviciosMensaje.verMensajesPorCodigoPlanta(codigoPlanta);
        model.addAttribute("mensajesPorPlanta", mensajes);
        return "MensajesForm";
    }

    
    
    @PostMapping("/personas/guardar")
    public String guardarPersona(@RequestParam String nombre,
                                 @RequestParam String email,
                                 @RequestParam String usuario,
                                 @RequestParam String password) {
        
        Persona nuevaPersona = new Persona();
        nuevaPersona.setNombre(nombre);
        nuevaPersona.setEmail(email);
        
        Credenciales credenciales = new Credenciales();
        credenciales.setUsuario(usuario);
        credenciales.setPassword(password);
        credenciales.setPersona(nuevaPersona);
        
        nuevaPersona.setCredenciales(credenciales);
        
        personaRepository.save(nuevaPersona);
        return "redirect:/PersonasForm";
    }
    
    @GetMapping("/PersonasForm")
    public String gestionPersonas(Model model) {
    	List<Persona> listaPersonas = personaRepository.findAll();
        model.addAttribute("personas", listaPersonas);
    	return "PersonasForm";
    }
    
    @PostMapping("/personas/eliminar")
    public String eliminarPersona(@RequestParam Long idPersona, RedirectAttributes redirectAttributes) {
       try {
           boolean eliminado = serviciosPersona.eliminarPersona(idPersona);
           if (eliminado) {
               System.out.println("Persona eliminada con éxito.");
               redirectAttributes.addFlashAttribute("success", "Persona eliminada con éxito.");
           } else {
               System.out.println("No se encontró una persona con ese ID.");
               redirectAttributes.addFlashAttribute("error", "No se encontró una persona con ese ID.");
           }
       } catch (Exception e) {
           System.out.println("Error al borrar la persona: " + e.getMessage());
           redirectAttributes.addFlashAttribute("error", "Error al borrar la persona.");
       }
       return "redirect:/PersonasForm";
    }
    
}
