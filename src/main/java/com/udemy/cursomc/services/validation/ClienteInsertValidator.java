package com.udemy.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.udemy.cursomc.domain.Cliente;
import com.udemy.cursomc.domain.enums.TipoCliente;
//criando uma anotação 
import com.udemy.cursomc.dto.ClienteNewDTO;
import com.udemy.cursomc.repositories.ClienteRepository;
import com.udemy.cursomc.resources.exception.FieldMessage;
import com.udemy.cursomc.services.validation.util.BR; 

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> { //nome da anotação e tipo de classe q vai aceitar

	@Autowired
	private ClienteRepository repo;
	
	@Override //aqui eu poderia colocar aqui alguma programação de inicialização
	public void initialize(ClienteInsert ann) {   
	}       
	
	@Override    //metodo da interface ConstraintValidator q verifica se o tipo vai ser válido ou nao, retorna boolean
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) { 
		List<FieldMessage> list = new ArrayList<>();  //tipo que criei nas exceções pra carregar nome do campo e msg de erro do campo

		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())&& !BR.isValidCPF(objDto.getCpfOuCnpj())) {//se for do tipo PF e o CPF nao for valido
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
			
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())&& !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {//se for do tipo PF e o CPF nao for valido
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
			
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if(aux != null) {
			list.add(new FieldMessage("Email", "Email já existente"));
		}

		for (FieldMessage e : list) {  //esse for me permite transportar os objs de erro  que criei (FieldMessage) para os do framework         
			context.disableDefaultConstraintViolation();        
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}         
		return list.isEmpty();//se a lista retornar vazia será true pq não teve nenhum erro
	}

}




