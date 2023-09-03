package com.Clinica.clinica.services;

import com.Clinica.clinica.dto.TurnoDTO;

import java.util.Set;

public interface ITurnoServices <T> {

    TurnoDTO save(T t);

    void delete(Long id);

    TurnoDTO findById(Long id);

    Set<TurnoDTO> findAll();

    void deleteAll();

}
