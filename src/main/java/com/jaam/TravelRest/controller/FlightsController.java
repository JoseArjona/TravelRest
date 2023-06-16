package com.jaam.TravelRest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaam.TravelRest.entity.flights;
import com.jaam.TravelRest.services.FlightsServiceInterface;

@RestController
@RequestMapping("/api")
public class FlightsController {
    @Autowired
    private FlightsServiceInterface flightsService;

    // Obtener todos los vuelos
    @GetMapping(value = "/flights")
    // ResponseEntity nos permite devolver una respuesta http
    public ResponseEntity<Object> getFlights(){
        // Map nos permite definir una coleccion de objetos
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            List<flights> flights = flightsService.findAll();
            return new ResponseEntity<Object>(flights, HttpStatus.OK);
        } catch (Exception e) {
             map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener un vuelo por su id
    @GetMapping(value = "/flights/{id}")
    public ResponseEntity<Object> getFlightById(@PathVariable("id") Long id){
         Map<String, Object> map = new HashMap<String,Object>();
        try {
            flights flight = flightsService.findById(id);
            return new ResponseEntity<Object>(flight, HttpStatus.OK);
        } catch (Exception e) {
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Guardar un vuelo
    @PostMapping(value = "/flights")
    public ResponseEntity<Object> create(@RequestBody flights fg){
        Map<String, Object> map = new HashMap<String,Object>();
        if (fg.getOrigin() == null|| fg.getOrigin().isEmpty() || fg.getDestination().isEmpty() ||
         fg.getDestination() == null || fg.getLayoverCount() <= 0 || fg.getAirline().isEmpty() || 
         fg.getAirline() == null || fg.getPrice() <= 0) {
            map.put("error", "Los campos no pueden estar vacios");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        try {
            flights flight = flightsService.save(fg);
            return new ResponseEntity<Object>(flight, HttpStatus.OK);
        } catch (Exception e) {
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar un vuelo
    @PutMapping(value = "/flights/{id}")
    public ResponseEntity <Object> update(@RequestBody flights fg, @PathVariable("id") Long id){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            flights currentFlight = flightsService.findById(id);
            if (currentFlight == null) {
                map.put("error", "El vuelo no existe");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            currentFlight.setOrigin(fg.getOrigin());
            currentFlight.setDestination(fg.getDestination());
            currentFlight.setLayoverCount(fg.getLayoverCount());
            currentFlight.setAirline(fg.getAirline());
            currentFlight.setPrice(fg.getPrice());
            flightsService.save(currentFlight);
            return new ResponseEntity<Object>(currentFlight, HttpStatus.OK);
        } catch (Exception e) {
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar un vuelo por su id
    @DeleteMapping(value = "/flights/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            flightsService.deleteById(id);
            map.put("success", "El vuelo ha sido eliminado");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener vuelos por origen
    @GetMapping(value = "/flights/origin/{origin}")
    public ResponseEntity<Object> getFlightsByOrigin(@PathVariable("origin") String origin){
        if (origin == null || origin.isEmpty()) {
            Map<String, Object> map = new HashMap<String,Object>();
            map.put("error", "El origen no puede estar vacio");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            List<flights> flights = flightsService.findByOrigin(origin);
            // Si no encuentra vuelos con ese origen
            if (flights.isEmpty()) {
                map.put("error", "No se encontraron vuelos con ese origen");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Object>(flights, HttpStatus.OK);
        } catch (Exception e) {
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener vuelos por destino
    @GetMapping(value = "/flights/destination/{destination}")
    public ResponseEntity<Object> getFlightsByDestination(@PathVariable("destination") String destination){
        if (destination == null || destination.isEmpty()) {
            Map<String, Object> map = new HashMap<String,Object>();
            map.put("error", "El destino no puede estar vacio");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            List<flights> flights = flightsService.findByDestination(destination);
            // Si no encuentra vuelos con ese destino
            if (flights.isEmpty()) {
                map.put("error", "No se encontraron vuelos con ese destino");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Object>(flights, HttpStatus.OK);
        } catch (Exception e) {
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener vuelos por número de escalas
    @GetMapping(value = "/flights/layoverCount/{layoverCount}")
    public ResponseEntity<Object> getFlightsByLayoverCount(@PathVariable("layoverCount") int layoverCount){
        if (layoverCount <= 0) {
            Map<String, Object> map = new HashMap<String,Object>();
            map.put("error", "El número de escalas no puede ser menor o igual a 0");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            List<flights> flights = flightsService.findByLayoverCount(layoverCount);
            // Si no encuentra vuelos con ese número de escalas
            if (flights.isEmpty()) {
                map.put("error", "No se encontraron vuelos con ese número de escalas");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Object>(flights, HttpStatus.OK);
        } catch (Exception e) {
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar todos los vuelos a un destino determinado
    @DeleteMapping(value = "/flights/destination/{destination}")
    public ResponseEntity<Object> deleteFlightsByDestination(@PathVariable("destination") String destination){
        if (destination == null || destination.isEmpty()) {
            Map<String, Object> map = new HashMap<String,Object>();
            map.put("error", "El destino no puede estar vacio");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> map = new HashMap<String,Object>();
        try {
             List<flights> flights = flightsService.findByDestination(destination);
            // Si no encuentra vuelos con ese destino
             if (flights.isEmpty()) {
                 map.put("error", "No se encontraron vuelos con ese destino");
                 return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
             }
            flightsService.deleteByDestination(destination);
            map.put("success", "Los vuelos han sido eliminados");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
