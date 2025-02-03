package com.Angelvf3839.tarea3dwesangel.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Angelvf3839.tarea3dwesangel.modelo.Planta;
import com.Angelvf3839.tarea3dwesangel.repositorios.PlantaRepository;

import jakarta.transaction.Transactional;

@Service
public class ServiciosPlanta {

    @Autowired
    private PlantaRepository plantaRepo;

    public void insertar(Planta p) {
    	plantaRepo.saveAndFlush(p);
    }

    public List<Planta> verTodas() {
    	return plantaRepo.findAllByOrderByNombreComunAsc();
    }

    public Planta buscarPorID(long id) {
        Optional<Planta> plantas = plantaRepo.findById(id);
        return plantas.orElse(null);
    }

    @Transactional
    public boolean actualizarNombreComun(String codigo, String nombreComun) { 
        Optional<Planta> plantas = plantaRepo.findByCodigo(codigo);
        if (plantas.isPresent()) {
            Planta planta = plantas.get();
            planta.setNombreComun(nombreComun);
            plantaRepo.saveAndFlush(planta);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean actualizarNombreCientifico(String codigo, String nombreCientifico) {
        Optional<Planta> plantas = plantaRepo.findByCodigo(codigo);
        if (plantas.isPresent()) {
            Planta p = plantas.get();
            p.setNombreCientifico(nombreCientifico);
            plantaRepo.saveAndFlush(p);
            return true;
        }
        return false;
    }

    public boolean codigoExistente(String codigo) {
        return plantaRepo.existsByCodigo(codigo);
    }

    /* Método para validar una planta */
    
    public boolean validarPlanta(Planta p) {
        if (p.getCodigo() == null || p.getCodigo().trim().isEmpty()) {
            return false;
        }
        if (!p.getCodigo().matches("^[A-Za-z0-9]+$")) {
            return false;
        }
        if (p.getNombreComun() == null || p.getNombreComun().trim().isEmpty()) {
            return false;
        }
        if (p.getNombreCientifico() == null || p.getNombreCientifico().trim().isEmpty()) {
            return false;
        }
        return true;
    }


    /* Método para validar el código de una planta */
    public boolean validarCodigo(String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            return false;
        }
        if (!codigo.matches("^[A-Za-z0-9]+$")) {
            return false;
        }
        if (codigo.length() < 3 || codigo.length() > 50) {
            return false;
        }
        return true;
    }
    
    public Planta buscarPorCodigo(String codigo) {
        Optional<Planta> plantaOptional = plantaRepo.findByCodigo(codigo);
        return plantaOptional.orElse(null);
    }
    
    public boolean eliminarPlanta(Long id) {
        try {
            Optional<Planta> plantaOptional = plantaRepo.findById(id);
            if (plantaOptional.isPresent()) {
                plantaRepo.deleteById(id);
                return true; 
            } else {
                System.out.println("No se encontró una planta con el ID: " + id);
                return false; 
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar la planta: " + e.getMessage());
            return false; 
        }
    }

}