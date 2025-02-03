package com.Angelvf3839.tarea3dwesangel.controladores;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Angelvf3839.tarea3dwesangel.modelo.Credenciales;
import com.Angelvf3839.tarea3dwesangel.modelo.Ejemplar;
import com.Angelvf3839.tarea3dwesangel.modelo.Mensaje;
import com.Angelvf3839.tarea3dwesangel.modelo.Persona;
import com.Angelvf3839.tarea3dwesangel.modelo.Planta;
import com.Angelvf3839.tarea3dwesangel.repositorios.EjemplarRepository;
import com.Angelvf3839.tarea3dwesangel.repositorios.MensajeRepository;
import com.Angelvf3839.tarea3dwesangel.repositorios.PersonaRepository;
import com.Angelvf3839.tarea3dwesangel.repositorios.PlantaRepository;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosCredenciales;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosMensaje;
import com.Angelvf3839.tarea3dwesangel.servicios.ServiciosPersona;

import org.springframework.beans.factory.annotation.Autowired;
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
    private ServiciosCredenciales serviciosCredenciales;

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
    public String login(@RequestParam String usuario, @RequestParam String password) {
        if ("admin".equals(usuario) && "admin".equals(password)) {
            return "redirect:/menuAdmin";
        } else if (serviciosCredenciales.autenticar(usuario, password)) {
            return "redirect:/menuPersonal";
        }
        return "redirect:/index?error=true";
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
    public String guardarMensaje(@RequestParam Long idEjemplar, 
                                 @RequestParam String mensajeTexto) {
        System.out.println("Recibido ID Ejemplar: " + idEjemplar);
        System.out.println("Mensaje recibido: " + mensajeTexto);

        Ejemplar ejemplar = ejemplarRepository.findById(idEjemplar).orElse(null);
        if (ejemplar == null) {
            System.out.println("No existe un ejemplar con el ID proporcionado.");
            return "redirect:/MensajesForm?error=EjemplarNoEncontrado";
        }

        String nombreUsuario = "usuarioAutenticado";
        Persona persona = serviciosPersona.buscarPorNombre(nombreUsuario);
        if (persona == null) {
            System.out.println("No se encontró la persona autenticada.");
            return "redirect:/MensajesForm?error=UsuarioNoEncontrado";
        }

        Mensaje mensaje = new Mensaje(LocalDateTime.now(), mensajeTexto, persona, ejemplar);
        serviciosMensaje.insertar(mensaje);
        
        System.out.println("✔️ Mensaje guardado correctamente en la BD");

        return "redirect:/MensajesForm";
    }


    
    @GetMapping("/MensajesForm")
    public String gestionMensajes(Model model) {
    	List<Mensaje> listarMensajes = mensajesRepository.findAll();
    	model.addAttribute("mensajes", listarMensajes);
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
    
}
