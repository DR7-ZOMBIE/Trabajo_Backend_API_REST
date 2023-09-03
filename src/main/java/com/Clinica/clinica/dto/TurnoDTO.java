package com.Clinica.clinica.dto;

import com.Clinica.clinica.model.Odontologo;
import com.Clinica.clinica.model.Paciente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoDTO  {

    private Long id;

    private Date fechaHora;

    private Odontologo odontologo;

    private Paciente paciente;


}
