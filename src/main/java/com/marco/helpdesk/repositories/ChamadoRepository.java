package com.marco.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marco.helpdesk.domain.Chamado;
public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}
