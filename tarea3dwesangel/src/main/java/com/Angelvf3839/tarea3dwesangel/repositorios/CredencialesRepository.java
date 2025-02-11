package com.Angelvf3839.tarea3dwesangel.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Angelvf3839.tarea3dwesangel.modelo.Credenciales;

@Repository
public interface CredencialesRepository extends JpaRepository<Credenciales, Long> {

    boolean existsByUsuario(String usuario);
    
    @Query("SELECT c FROM Credenciales c WHERE c.usuario = :usuario")
    Credenciales buscarPorUsuario(@Param("usuario") String usuario);

    @Query("SELECT COUNT(c) > 0 FROM Credenciales c WHERE c.usuario = :usuario AND c.password = :password")
    boolean verificarCredenciales(@Param("usuario") String usuario, @Param("password") String password);
    
}
