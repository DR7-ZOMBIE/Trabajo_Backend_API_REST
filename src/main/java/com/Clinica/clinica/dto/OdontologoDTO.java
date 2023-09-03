package com.Clinica.clinica.dto;

import com.Clinica.clinica.model.Paciente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OdontologoDTO {

    private Long id;

    private String nombre;

    private String apellido;

    private Set<Paciente> pacientes = new HashSet<Paciente>();

}
