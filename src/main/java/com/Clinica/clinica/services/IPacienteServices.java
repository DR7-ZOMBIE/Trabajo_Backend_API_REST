package com.Clinica.clinica.services;


import com.Clinica.clinica.dto.PacienteDTO;
import com.Clinica.clinica.model.Paciente;

import javax.validation.Valid;
import java.util.Set;

public interface IPacienteServices<T> {

    PacienteDTO save(T t);

    void delete(Long id);

    PacienteDTO findById(Long id);

    Set<PacienteDTO> findAll();

    void deleteAll();
}
