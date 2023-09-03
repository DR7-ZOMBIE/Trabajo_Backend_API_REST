package com.Clinica.clinica;

import com.Clinica.clinica.dto.OdontologoDTO;
import com.Clinica.clinica.dto.PacienteDTO;
import com.Clinica.clinica.model.Paciente;
import com.Clinica.clinica.services.IOdontologoServices;
import com.Clinica.clinica.services.IPacienteServices;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OdontologoServicesTest {

    @Autowired
    IOdontologoServices<OdontologoDTO> odontologoServices;

    @Autowired
    IPacienteServices<PacienteDTO> pacienteServices;
    @Autowired
    private ModelMapper mapper;

    Logger log = Logger.getLogger(String.valueOf(OdontologoServicesTest.class));

    @Test
    public void testInsertOdontologo() throws Exception {
        // Crear un nuevo odontólogo DTO
        OdontologoDTO odontologoDTO = new OdontologoDTO();
        odontologoDTO.setNombre("Manuel");
        odontologoDTO.setApellido("Turizo");

        // Crear un conjunto de pacientes y agregar algunos pacientes
        Set<PacienteDTO> pacientes = new HashSet<>();

        PacienteDTO paciente1 = new PacienteDTO();
        paciente1.setNombre("Pepito");
        paciente1.setApellido("Perez");
        pacientes.add(paciente1);

        PacienteDTO paciente2 = new PacienteDTO();
        paciente2.setNombre("Juanito");
        paciente2.setApellido("López");
        pacientes.add(paciente2);

        // Utilizar ModelMapper para convertir PacienteDTO a Paciente
        Set<Paciente> pacientesEntity = pacientes.stream()
                .map(pacienteDTO -> mapper.map(pacienteDTO, Paciente.class))
                .collect(Collectors.toSet());

        // Asignar el conjunto de pacientes al odontólogo DTO
        odontologoDTO.setPacientes(pacientesEntity);

        assertTrue(odontologoDTO != null);
    }



    @Test
    public void testDeleteOdontologo() throws Exception {

        Optional<OdontologoDTO> odontologo= Optional.ofNullable(odontologoServices.findById(35L));

        if (odontologo.isPresent()) {
            odontologoServices.delete(35L);

        }

        assertFalse(odontologo.isPresent());
    }

    @Test
    public void testFindOdontologoById() throws Exception {

        Optional<OdontologoDTO> odontologoDennis = Optional.ofNullable(odontologoServices.findById(31L));

        if (odontologoDennis.isPresent()) {
            assertTrue(odontologoDennis != null);
        }
    }

    @Test
    public void testFindAllOdontologos() throws Exception {

        Set<OdontologoDTO> odontologos = odontologoServices.findAll();

        for (OdontologoDTO i : odontologos) {
            // Aquí puedes imprimir información sobre cada odontólogo
            log.info("ID: " + i.getId());
            log.info("Nombre: " + i.getNombre());
            log.info("Apellido: " + i.getApellido());
        }

        assertFalse(odontologos.isEmpty());

    }



}