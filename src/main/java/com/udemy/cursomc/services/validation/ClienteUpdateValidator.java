package com.udemy.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.udemy.cursomc.domain.Cliente;
import com.udemy.cursomc.dto.ClienteDTO;
import com.udemy.cursomc.repositories.ClienteRepository;
import com.udemy.cursomc.resources.exception.FieldMessage; 

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> { //nome da anotação e tipo de classe q vai aceitar

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private HttpServletRequest request;//tem uma função que me permite buscar o parâmetro da URI
	//na URI já tem o codigo do cliente, nao preciso ficar colocando no JSON

	@Override //aqui eu poderia colocar aqui alguma programação de inicialização
	public void initialize(ClienteUpdate ann) {   
	}
	
	@Override    //metodo da interface ConstraintValidator q verifica se o tipo vai ser válido ou nao, retorna boolean
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) { 
		//preciso dele pq quando faço a requisição mando com chave e valor por atributo chave: nome valor: joão
	
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		
		
		List<FieldMessage> list = new ArrayList<>();  //tipo que criei nas exceções pra carregar nome do campo e msg de erro do campo

				
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if(aux != null && !aux.getId().equals(uriId)) {
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




