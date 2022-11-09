package com.marco.helpdesk.services;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.marco.helpdesk.domain.Pessoa;
import com.marco.helpdesk.domain.Cliente;
import com.marco.helpdesk.domain.dtos.ClienteDTO;
import com.marco.helpdesk.repositories.PessoaRepository;
import com.marco.helpdesk.repositories.ClienteRepository;
import com.marco.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.marco.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Cliente findById(Integer id) {
			Optional<Cliente> obj = repository.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id ));		
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente create(ClienteDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Cliente newOBJ = new Cliente(objDTO);
		return repository.save(newOBJ);
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = this.findById(id);
		
		if(!objDTO.getSenha().equals(oldObj.getSenha())) 
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Cliente(objDTO);
		return repository.save(oldObj);
		
	}

	private void validaPorCpfEEmail(ClienteDTO objDTO) {
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
		Cliente obj = findById(id);
		if(obj.getChamados().size()>0) {
			throw new DataIntegrityViolationException("O cliente possui ordens de serviço e não pode ser deletado!");
		}
		repository.deleteById(id);		
	}
	
}
