package com.marco.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marco.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{

}
