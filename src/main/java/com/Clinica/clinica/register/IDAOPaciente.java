package com.Clinica.clinica.register;

import com.Clinica.clinica.model.Odontologo;
import com.Clinica.clinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IDAOPaciente extends JpaRepository<Paciente, Long> {



}
