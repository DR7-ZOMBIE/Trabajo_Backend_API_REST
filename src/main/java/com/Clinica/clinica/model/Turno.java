package com.Clinica.clinica.model;

import com.Clinica.clinica.dto.OdontologoDTO;
import com.Clinica.clinica.dto.PacienteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "turnos")
public class Turno implements Serializable, Comparable<Turno> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( unique = true , nullable = false )
    private Long id;

    @Column
    private Date fechaHora;

    @JsonIgnore
    @OneToOne (cascade = CascadeType.ALL ,  fetch = FetchType.LAZY  )
    @JoinColumn( name = "odontologo_id", referencedColumnName = "id")
    private Odontologo odontologo;

    @JsonIgnore
    @OneToOne ( cascade = CascadeType.ALL ,  fetch = FetchType.LAZY)
    @JoinColumn( name = "paciente_id", referencedColumnName = "id")
    private Paciente paciente;

    @Override
    public int compareTo(Turno o) {return this.fechaHora.compareTo(o.getFechaHora());}

}
