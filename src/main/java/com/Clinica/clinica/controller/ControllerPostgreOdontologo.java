package com.Clinica.clinica.controller;

import com.Clinica.clinica.dto.OdontologoDTO;
import com.Clinica.clinica.dto.PacienteDTO;
import com.Clinica.clinica.model.Odontologo;
import com.Clinica.clinica.model.Paciente;
import com.Clinica.clinica.services.IOdontologoServices;
import com.Clinica.clinica.services.IPacienteServices;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/odontologos")
public class ControllerPostgreOdontologo {

    @Autowired
    private IOdontologoServices<OdontologoDTO> odontologoServices;

    @Autowired
    private IPacienteServices<PacienteDTO> pacienteServices;

    @Autowired
    private ModelMapper mapper;

    // Metodo de buscar a todos los odontologos en la tabla de odontologos en la base de datos
    @GetMapping("/lista")
    public Set<OdontologoDTO> odontologoList() {return odontologoServices.findAll();}

    // Metodo para buscar un odontolo en la en la tabla de odontologos de la base de datos

    @GetMapping("/lista/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OdontologoDTO odontologoid(@PathVariable Long id) throws Exception {return odontologoServices.findById(id);}

    // Metodo eliminar en la tabla odontologos en la base de datos

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){odontologoServices.delete(id);}

    // Agregar a la tabla odontologos de la base de datos

    @PostMapping("/add")
    public ResponseEntity<OdontologoDTO> addOdontologo (@RequestBody OdontologoDTO odontologoDTO){
        odontologoServices.save(odontologoDTO);
        return ResponseEntity.ok(odontologoDTO);
    }

    // Agregar a la tabla odontologos de la base de datos si existen pacientes

    @PostMapping("/addPacienteExiste")
    public ResponseEntity<OdontologoDTO> addOdontologoPacienteExiste(@Valid @RequestBody OdontologoDTO odontologoDTO) {
        try {
            Set<Paciente> pacientes = odontologoDTO.getPacientes();
            boolean pacientesValidos = true;

            if (pacientes != null && !pacientes.isEmpty()) {
                for (Paciente paciente : pacientes) {
                    Long pacienteId = paciente.getId();
                    if (pacienteId == null || pacienteServices.findById(pacienteId) == null) {
                        // Si el ID del Paciente no es válido o el Paciente no existe, marca pacientesValidos como falso
                        pacientesValidos = false;
                        break; // No es necesario continuar verificando
                    }
                }
            } else {
                // Si la lista de pacientes está vacía, también marca pacientesValidos como falso
                pacientesValidos = false;
            }

            if (!pacientesValidos) {
                // Si hay pacientes inválidos, devolver un error
                return ResponseEntity.badRequest().build();
            }

            // Si todos los pacientes son válidos, puedes guardar el Odontólogo
            return ResponseEntity.ok(odontologoServices.save(odontologoDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





    // Metodo de actualizar la base de datos

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public OdontologoDTO update(@PathVariable Long id, @RequestBody OdontologoDTO odontologo){
        OdontologoDTO o = odontologoServices.findById(id);
        if (o != null){
            o.setNombre(odontologo.getNombre());
            o.setApellido(odontologo.getApellido());
        }
        return odontologoServices.save(o);
    }

    // Metodo para ordenar los odontologos por el nombre de forma ascendente si lo deseamos descendente
    // Se utuiliza el metodo Collections.reversed()
    @GetMapping("/listSortAsc")
    public ResponseEntity<Set<OdontologoDTO>> listSortOdontologoByNameAsc() {
        Set<OdontologoDTO> odontologos = odontologoServices.findAll();

        // Convierte el Set en una lista
        List<OdontologoDTO> odontologoList = new ArrayList<>(odontologos);

        // Ordena la lista por nombre ascendente
        odontologoList.sort(Comparator.comparing(OdontologoDTO::getNombre));

        // Crea un nuevo Set a partir de la lista ordenada
        Set<OdontologoDTO> odontologosOrdenados = new LinkedHashSet<>(odontologoList);

        return ResponseEntity.ok(odontologosOrdenados);
    }


    // Ordenar de forma descedente a los odontologos por el nombre
    @GetMapping("/listSortDesc")
    public ResponseEntity<Set<OdontologoDTO>> listSortOdontologoByNameDesc() {
        Set<OdontologoDTO> odontologos = odontologoServices.findAll();

        // Convierte el Set en una lista
        List<OdontologoDTO> odontologoList = new ArrayList<>(odontologos);

        // Ordena la lista por nombre en orden descendente
        odontologoList.sort(Comparator.comparing(OdontologoDTO::getNombre).reversed());

        // Convierte la lista ordenada nuevamente en un Set
        Set<OdontologoDTO> odontologoSortedSet = new LinkedHashSet<>(odontologoList);

        return ResponseEntity.ok(odontologoSortedSet);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll() {
        odontologoServices.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
