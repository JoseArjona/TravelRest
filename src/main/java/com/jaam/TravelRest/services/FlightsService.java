package com.jaam.TravelRest.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jaam.TravelRest.entity.flights;
import com.jaam.TravelRest.repository.flights_Dao;
import jakarta.transaction.Transactional;

// Service nos permite definir la clase como un servicio para que pueda ser inyectado en
// otros componentes
@Service
public class FlightsService implements FlightsServiceInterface{
     // Aqui es donde se implementan los metodos de la interfaz
    // Autowired nos permite inyectar la dependencia dentro de otra
    @Autowired
    private flights_Dao flightsDao;
    // Transactional nos permite definir los metodos que van a ser transaccionales
    // es decir que se van a ejecutar en una transaccion de base de datos
    @Transactional
    public List<flights> findAll() {
        // El metodo findAll() retorna un Iterable, por lo tanto lo convertimos a List
        return (List<flights>) flightsDao.findAll();
    }
    @Transactional
    public flights findById(Long id) {
        // El metodo findById() retorna un Optional, por lo tanto lo convertimos a flights
        return flightsDao.findById(id).orElse(null);
    }
    @Transactional
    public flights save(flights flight) {
        // El metodo save() guarda un vuelo en la base de datos
        return flightsDao.save(flight);
    }
    @Transactional
    public void deleteById(Long id) {
        // El metodo deleteById() elimina un vuelo de la base de datos por su id
        flightsDao.deleteById(id);
    }
    @Transactional
    public List<flights> findByOrigin(String origin) {
        return flightsDao.findByOrigin(origin);
    }
    @Transactional
    public List<flights> findByDestination(String destination) {
        return flightsDao.findByDestination(destination);
    }
    @Transactional
    public List<flights> findByLayoverCount(int layoverCount) {
        return flightsDao.findByLayoverCount(layoverCount);
    }
    @Transactional
    public void deleteByDestination(String destination) {
        flightsDao.deleteByDestination(destination);
    }
}
