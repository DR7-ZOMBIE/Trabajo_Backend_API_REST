package com.Clinica.clinica.controller;

import com.Clinica.clinica.dto.OdontologoDTO;
import com.Clinica.clinica.dto.PacienteDTO;
import com.Clinica.clinica.dto.TurnoDTO;
import com.Clinica.clinica.model.Odontologo;
import com.Clinica.clinica.model.Paciente;
import com.Clinica.clinica.model.Turno;
import com.Clinica.clinica.register.IDAOTurno;
import com.Clinica.clinica.services.IOdontologoServices;
import com.Clinica.clinica.services.IPacienteServices;
import com.Clinica.clinica.services.ITurnoServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

@RestController // Solo devuelve o acepta JSONS
@RequestMapping(value = "/turnos")
public class ControllerPostgreTurno {

    // Autowired se encarga de hacer toda la comunicacion del DAO con las capas
    @Autowired
    private ITurnoServices<TurnoDTO> turnoServices;
    @Autowired
    private IPacienteServices<PacienteDTO> pacienteServices;
    @Autowired
    private IOdontologoServices<OdontologoDTO> odontologoServices;
    @Autowired
    private IDAOTurno turnoRepository; // Esto es nada mas para pprogar una Query con HQL
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/queryTurnos")
    public List<Turno> getTurnosConOdontologosYPacientes() {
        // Utiliza el método personalizado de tu repositorio para obtener los turnos con carga ansiosa
        List<Turno> turnos = turnoRepository.findAllWithOdontologoAndPaciente();

        return turnos;
    }

    Logger log = Logger.getLogger(String.valueOf(ControllerPostgreTurno.class));

    // Listar todos los turnos disponibles
    @GetMapping(value = "/list")
    public ResponseEntity<Set<TurnoDTO>> turnoList() {return ResponseEntity.ok(turnoServices.findAll());}

    // Agregar un turno a la tabla de turnos con un paciente y odontologo disponible

    @PostMapping(value = "/add/{idOdontologo}/{idPaciente}")
    public ResponseEntity<TurnoDTO> addTurno(@RequestBody TurnoDTO turnoDTO, @PathVariable Long idOdontologo, @PathVariable Long idPaciente) {

        if (idOdontologo == null || idPaciente == null) {
            return ResponseEntity.badRequest().build();
        }

        // Busca el odontólogo en la base de datos
        OdontologoDTO odontologoDTO = odontologoServices.findById(idOdontologo);

        if (odontologoDTO == null) {
            return ResponseEntity.notFound().build();
        }

        // Busca el paciente en la base de datos
        PacienteDTO pacienteDTO = pacienteServices.findById(idPaciente);
        if (pacienteDTO == null) {
            return ResponseEntity.notFound().build();
        }

        // Crea un nuevo turno con los IDs del odontólogo y del paciente
        Turno turno = new Turno();
        // Mapea los objetos DTO a entidades reales
        turno.setOdontologo(modelMapper.map(odontologoDTO, Odontologo.class));
        turno.setPaciente(modelMapper.map(pacienteDTO, Paciente.class));

        // Puedes asignar otras propiedades de turnoDTO a turno si es necesario

        // Guarda el turno en la base de datos
        TurnoDTO turnoGuardadoDTO = turnoServices.save(modelMapper.map(turno, TurnoDTO.class));

        return ResponseEntity.ok(turnoGuardadoDTO);
    }


    // Elminar un turno dentro de la tabla de turnos
    @DeleteMapping(value = "/delete/{id}")
    public void deleteTurno(@PathVariable Long id) {

        Optional<TurnoDTO> turno = Optional.ofNullable(turnoServices.findById(id));

        if (!turno.isPresent()){
            ResponseEntity.notFound();
        }

        ResponseEntity.ok();
        turnoServices.delete(id);
    }

    // Buscar un turno por id Especifico

    @GetMapping(value = "/list/{id}")
    public ResponseEntity<TurnoDTO> findByIdTurno(@PathVariable Long id) {

        Optional<TurnoDTO> turno = Optional.ofNullable(turnoServices.findById(id));

        if (!turno.isPresent()){
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(turnoServices.findById(id));
    }

    // Modificar los parametros de un turno especifico

    @PutMapping(value = "update/{id}")
    public ResponseEntity<TurnoDTO> updateTurno(@PathVariable Long id, @RequestBody Turno turno) {

        TurnoDTO t =  turnoServices.findById(id);

        if (t != null) {
            t.setFechaHora(turno.getFechaHora());
        }

        return ResponseEntity.ok(turnoServices.save(t));
    }

    // Metodo para ordenar los turnos por la fecha de forma ascendente si lo deseamos descendente
    // Se utuiliza el metodo Collections.reversed()

    @GetMapping("/listSortAsc")
    public ResponseEntity<Set<TurnoDTO>> listSortPacienteByNameAsc() {
        Set<TurnoDTO> turnos = turnoServices.findAll();

        // Convierte el Set en una lista
        List<TurnoDTO> turnoList = new ArrayList<>(turnos);

        // Ordena la lista por nombre ascendente
        turnoList.sort(Comparator.comparing(TurnoDTO::getFechaHora));

        // Crea un nuevo Set a partir de la lista ordenada
        Set<TurnoDTO> turnosOrdenados = new LinkedHashSet<>(turnoList);

        return ResponseEntity.ok(turnosOrdenados);
    }


    // Ordenar de forma descedente a los turnos por la fecha
    @GetMapping("/listSortDesc")
    public ResponseEntity<Set<TurnoDTO>> listSortOdontologoByNameDesc() {
        Set<TurnoDTO> turnos = turnoServices.findAll();

        // Convierte el Set en una lista
        List<TurnoDTO> turnoList = new ArrayList<>(turnos);

        // Ordena la lista por nombre en orden descendente
        turnoList.sort(Comparator.comparing(TurnoDTO::getFechaHora).reversed());

        // Convierte la lista ordenada nuevamente en un Set
        Set<TurnoDTO> turnosOrdenados = new LinkedHashSet<>(turnoList);

        return ResponseEntity.ok(turnosOrdenados);
    }

    @DeleteMapping("deleteAll")
    public ResponseEntity<Void> deleteAll(){
        turnoServices.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
