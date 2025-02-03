package com.Angelvf3839.tarea3dwesangel.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Angelvf3839.tarea3dwesangel.modelo.Ejemplar;

import jakarta.transaction.Transactional;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {

    @Query("SELECT COUNT(*) FROM Ejemplar")
    long obtenerTotalEjemplares();

    @Query("SELECT e FROM Ejemplar e LEFT JOIN FETCH e.mensajes WHERE e.planta.codigo = :codigo")
    List<Ejemplar> encontrarEjemplaresPorCodigoPlanta(@Param("codigo") String codigo);


    @Transactional
    @Modifying
    @Query("UPDATE Ejemplar e SET e.nombre = :nuevoNombre WHERE e.id = :idEjemplar")
    int actualizarNombre(@Param("idEjemplar") Long idEjemplar, @Param("nuevoNombre") String nuevoNombre);
    
}
