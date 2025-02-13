package com.Angelvf3839.tarea3dwesangel.repositorios;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Angelvf3839.tarea3dwesangel.modelo.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

	/* Consulta para buscar mensajes dentro de un rango de fechas */
    @Query("SELECT m FROM Mensaje m WHERE m.fechaHora BETWEEN :primeraFecha AND :segundaFecha")
    List<Mensaje> findMensajesBetweenFechas(@Param("primeraFecha") LocalDateTime primeraFecha,@Param("segundaFecha") LocalDateTime segundaFecha);
	
    @Query("SELECT m FROM Mensaje m WHERE m.persona.id = :idPersona")
    List<Mensaje> buscarPorPersona(@Param("idPersona") long idPersona);

    @Query("SELECT m FROM Mensaje m JOIN m.ejemplar e JOIN e.planta p WHERE p.codigo = :codigoPlanta")
    List<Mensaje> buscarMensajesPorCodigoPlanta(@Param("codigoPlanta") String codigoPlanta);

    @Query("SELECT m FROM Mensaje m WHERE m.ejemplar.id = :idEjemplar")
    List<Mensaje> mensajesPorEjemplar(@Param("idEjemplar") long idEjemplar);
    
    List<Mensaje> findByEjemplarIdOrderByFechaHoraAsc(Long idEjemplar);
}
