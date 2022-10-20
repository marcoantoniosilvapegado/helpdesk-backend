package com.marco.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marco.helpdesk.domain.Chamado;
import com.marco.helpdesk.domain.Cliente;
import com.marco.helpdesk.domain.Tecnico;
import com.marco.helpdesk.domain.dtos.ChamadoDTO;
import com.marco.helpdesk.domain.enums.Prioridade;
import com.marco.helpdesk.domain.enums.Status;
import com.marco.helpdesk.repositories.ChamadoRepository;
import com.marco.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;
	
	public Chamado findById(Integer id) {		
		Optional<Chamado> obj = this.repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! " + id));
	}

	public List<Chamado> findAll() {		
		return repository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO objDTO) {
		return repository.save( newChamado(objDTO));
				
	}
	
	private Chamado newChamado(ChamadoDTO obj) {
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());
		
		Chamado chamado = new Chamado();
		if(obj.getId() != null) {
			chamado.setId(obj.getId());			
		}
		
		if(obj.getStatus().equals(2)) {
			chamado.setDataFechamento(LocalDate.now());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		
		return chamado;
	}

	public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
		objDTO.setId(id);
		Chamado oldObj = findById(id);
		oldObj = newChamado(objDTO);
		return repository.save(oldObj);		
	}
}
















