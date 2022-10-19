package com.marco.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marco.helpdesk.domain.Chamado;
import com.marco.helpdesk.repositories.ChamadoRepository;
import com.marco.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	
	public Chamado findById(Integer id) {		
		Optional<Chamado> obj = this.repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! " + id));
	}

	public List<Chamado> findAll() {		
		return repository.findAll();
	}
}
