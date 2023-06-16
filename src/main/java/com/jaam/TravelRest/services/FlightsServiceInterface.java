package com.jaam.TravelRest.services;
import java.util.List;
import com.jaam.TravelRest.entity.flights;

// Nos permite definir los metodos que va a tener nuestro servicio
public interface FlightsServiceInterface {  
    // Metodo para obtener todos los vuelos
    public List<flights> findAll();
    // Metodo para obtener un vuelo por su id
    public flights findById(Long id);
    // Metodo para guardar un vuelo
    public flights save(flights flight);
    // Metodo para eliminar un vuelo por su id
    public void deleteById(Long id);
     // Obtener vuelos por origen
    public List<flights> findByOrigin(String origin);
    // Obtener vuelos por destino
    public List<flights> findByDestination(String destination);
    // Obtener vuelos por número de escalas
    public List<flights> findByLayoverCount(int layoverCount);
    // Eliminar todos los vuelos a un destino determinado
    public void deleteByDestination(String destination);
}
