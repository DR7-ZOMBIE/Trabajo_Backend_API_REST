package com.Clinica.clinica.register;

import com.Clinica.clinica.model.Paciente;
import com.Clinica.clinica.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IDAOTurno extends JpaRepository<Turno,Long> {

    @Query("SELECT t FROM Turno t LEFT JOIN FETCH t.odontologo o LEFT JOIN FETCH t.paciente p")
    List<Turno> findAllWithOdontologoAndPaciente();

}
