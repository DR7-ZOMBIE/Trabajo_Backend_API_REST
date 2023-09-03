package com.Clinica.clinica.model;


import com.Clinica.clinica.dto.PacienteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity //Que actua como una entidad con la base de datos
@Table(name = "odontologos") // Creando la tabla odontologos
public class Odontologo implements Serializable, Comparable<Odontologo> {

    //  Columna de id - Llaves primarias
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column ( unique = true , nullable = false)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String matricula;

    @Override
    public int compareTo(Odontologo o) {
        return this.nombre.compareToIgnoreCase(o.getNombre());
    }

    // Ir apprendiendo un poco de los Joins poco a poco
    // El JoinColumn va en el lado inverso de la relacion por ejemplo aca quiero unir
    // Odontologos y pacientes el Join debe ir en pacientes
    @JsonIgnore
    @OneToMany(mappedBy = "odontologo" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Paciente> pacientes = new HashSet<Paciente>();

    @JsonIgnore
    @OneToOne (mappedBy = "odontologo" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Turno turno;


}
