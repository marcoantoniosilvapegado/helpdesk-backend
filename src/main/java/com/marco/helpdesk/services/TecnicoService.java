package com.marco.helpdesk.services;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.marco.helpdesk.domain.Pessoa;
import com.marco.helpdesk.domain.Tecnico;
import com.marco.helpdesk.domain.dtos.TecnicoDTO;
import com.marco.helpdesk.repositories.PessoaRepository;
import com.marco.helpdesk.repositories.TecnicoRepository;
import com.marco.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.marco.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	public Tecnico findById(Integer id) {
			Optional<Tecnico> obj = repository.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id ));		
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Tecnico newOBJ = new Tecnico(objDTO);
		return repository.save(newOBJ);
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = this.findById(id);
		
		if(!objDTO.getSenha().equals(oldObj.getSenha())) 
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);
		
	}

	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
		
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
		}		
	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if(obj.getChamados().size()>0) {
			throw new DataIntegrityViolationException("O técnico possui ordens de serviço e não pode ser deletado!");
		}
		repository.deleteById(id);		
	}
	
}
