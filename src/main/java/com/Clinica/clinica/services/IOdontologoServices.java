package com.Clinica.clinica.services;

import com.Clinica.clinica.dto.OdontologoDTO;
import com.Clinica.clinica.dto.PacienteDTO;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface IOdontologoServices<T> {

    OdontologoDTO save(T t); // Metodo para guardar con el servicio

    void delete (Long id); // Metodo para eliminar desde el servicio

    T findById(Long id); // Metodo para buscar desde el servicio

    Set<T> findAll(); // Metodo para listar todos los elementos de la tabla

    void deleteAll(); // Metodo para borrar todo de la base de datos
}
