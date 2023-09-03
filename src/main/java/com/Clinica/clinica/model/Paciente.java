package com.Clinica.clinica.model;

import com.Clinica.clinica.dto.OdontologoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@ToString
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pacientes")
public class Paciente implements Serializable , Comparable<Paciente> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( unique = true, nullable = false)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String domicilio;

    @Column
    private String DNI;

    @Column
    private Date fechaDeAlta;

    @Override
    public int compareTo(Paciente o) {
        return this.nombre.compareToIgnoreCase(o.getNombre());
    }


    //JoinColumn es una relacion unidireccional mappedBy Es bidireccional las dos no se pueden manejar al tiempo

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn( name = "odontologo_id", nullable = false)
    private Odontologo odontologo;

    @JsonIgnore
    @OneToOne (mappedBy = "paciente" ,cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Turno turno;


}
