package com.Clinica.clinica.services;

import com.Clinica.clinica.dto.OdontologoDTO;
import com.Clinica.clinica.model.Odontologo;
import com.Clinica.clinica.register.IDAOOdontologo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OdontologoServices implements IOdontologoServices<OdontologoDTO> {

    // Dentro de esta clase estamos agregando una capa de persistencia de seguridad
    // Para acceder a los atributos que si sean necesarios
    @Autowired
    private IDAOOdontologo idaoOdontologo;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public OdontologoDTO save(OdontologoDTO odontologoDTO) {
        Odontologo odontologo =  mapper.convertValue(odontologoDTO, Odontologo.class);
        idaoOdontologo.save(odontologo);
        return odontologoDTO;
    }

    @Override
    public void delete(Long id) {
       idaoOdontologo.deleteById(id);
    }

    @Override
    public OdontologoDTO findById(Long id) {
        Optional<Odontologo> odontologo = idaoOdontologo.findById(id);
        OdontologoDTO odontologoDTO =  null;
        if (odontologo.isPresent())
            odontologoDTO =  mapper.convertValue(odontologo, OdontologoDTO.class);

        return odontologoDTO;
    }

    @Override
    public Set<OdontologoDTO> findAll() {
        List<Odontologo> odontologos = idaoOdontologo.findAll();
        Set<OdontologoDTO> odontologoDTOS = new HashSet<>();

        for (Odontologo i: odontologos) {
            odontologoDTOS.add(mapper.convertValue(i, OdontologoDTO.class));
        }
        return odontologoDTOS;
    }

    @Override
    public void deleteAll() {
        idaoOdontologo.deleteAll();
    }
}
