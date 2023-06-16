package com.jaam.TravelRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaam.TravelRest.entity.flights;
import java.util.List;
// JpaRepository nos permite realizar las operaciones basicas de una base de datos
// como insertar, actualizar, eliminar y buscar
public interface flights_Dao extends JpaRepository<flights, Long>{
    // Consultas Personalizadas
    List<flights> findByOrigin(String origin);
    List<flights> findByDestination(String destination);
    List<flights> findByLayoverCount(int layoverCount);
    void deleteByDestination(String destination);
}
