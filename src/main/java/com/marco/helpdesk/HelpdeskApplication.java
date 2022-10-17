package com.marco.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marco.helpdesk.domain.Chamado;
import com.marco.helpdesk.domain.Cliente;
import com.marco.helpdesk.domain.Tecnico;
import com.marco.helpdesk.domain.enums.Perfil;
import com.marco.helpdesk.domain.enums.Prioridade;
import com.marco.helpdesk.domain.enums.Status;
import com.marco.helpdesk.repositories.ChamadoRepository;
import com.marco.helpdesk.repositories.ClienteRepository;
import com.marco.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication /*implements CommandLineRunner**/{
	
	/*@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired 
	private ClienteRepository clienteRepository;
	@Autowired 
	private ChamadoRepository chamadoRepository;**/

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	//@Override
	public void run(String... args) throws Exception {
		
		/**Tecnico tec1 = new Tecnico(null, "Valdir Cesar", "63653230268", "valdir@mail.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cl1 = new Cliente(null, "Linus Torvalds", "80527954780", "torvalds@mail.com", "123");
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cl1);
			
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cl1));
		chamadoRepository.saveAll(Arrays.asList(c1));**/
		
	}

}
