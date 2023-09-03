package com.Clinica.clinica.register;

import com.Clinica.clinica.model.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

@Repository
public interface IDAOOdontologo extends JpaRepository<Odontologo,Long> {

}
