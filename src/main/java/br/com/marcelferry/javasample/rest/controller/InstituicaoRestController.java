/*
 * Created on 4 abr 2018 ( Time 10:40:10 )
 */
package br.com.marcelferry.javasample.rest.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.marcelferry.javasample.business.service.InstituicaoService;
import br.com.marcelferry.javasample.data.model.Instituicao;
import br.com.marcelferry.javasample.web.listitem.InstituicaoListItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Spring MVC controller for 'Instituicao' management.
 */
@RestController
@RequestMapping("/api/v1")
@Api( value = "/api/v1/instituicao", description = "Manipulação de Conta Corrente")
public class InstituicaoRestController {

	private final Logger LOG = LoggerFactory.getLogger(InstituicaoRestController.class);
	
	@Resource
	private InstituicaoService instituicaoService;
	
	@RequestMapping( value="/items/instituicao",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<InstituicaoListItem>> findAllAsListItems() {
		List<Instituicao> list = instituicaoService.findAll();
		List<InstituicaoListItem> items = new LinkedList<InstituicaoListItem>();
		
		if (list == null || list.isEmpty()){
            LOG.info("no Instituicao found");
            return new ResponseEntity<List<InstituicaoListItem>>(HttpStatus.NO_CONTENT);
        }
		
		for ( Instituicao instituicao : list ) {
			items.add(new InstituicaoListItem( instituicao ) );
		}
		return new ResponseEntity<List<InstituicaoListItem>>(items,  HttpStatus.OK);
	}
	
	@ApiOperation(value = "Visualizar uma lista de todas as contas correntes disponíveis", response = Instituicao.class, responseContainer="List")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Lista retornada com sucesso"),
	        @ApiResponse(code = 404, message = "O recurso que está tentando acessar não existe")
	}
	)
	@RequestMapping( value="/instituicao",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Instituicao>> findAll() {
		LOG.info("getting all Instituicao");
		List<Instituicao> list = instituicaoService.findAll();
		
		if (list == null || list.isEmpty()){
            LOG.info("no Instituicao found");
            return new ResponseEntity<List<Instituicao>>(HttpStatus.NO_CONTENT);
        }
		
		return new ResponseEntity<List<Instituicao>>(list,  HttpStatus.OK);
	}

	@ApiOperation(value = "Buscar uma conta corrente pelo ID", response = Instituicao.class)
	@RequestMapping( value="/instituicao/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Instituicao> findOne(@PathVariable("id") Long id) {
		LOG.info("getting Instituicao with id: {}", id);
		Instituicao instituicao = instituicaoService.findById(id);
		if (instituicao == null){
            LOG.info("Instituicao with id {} not found", id);
            return new ResponseEntity<Instituicao>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Instituicao>(instituicao, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Adicionar uma conta corrente", response = Instituicao.class)
	@RequestMapping( value="/instituicao",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@RequestBody Instituicao instituicao, UriComponentsBuilder ucBuilder) {
		LOG.info("creating new instituicao: {}", instituicao);

        if (instituicaoService.exists(instituicao)){
            LOG.info("a instituicao with name " + instituicao.getDescricao() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        
        Instituicao instituicaoCreated = instituicaoService.create(instituicao);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/v1/instituicao/{id}").buildAndExpand(instituicaoCreated.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Atualizar uma conta corrente", response = Instituicao.class)
	@RequestMapping( value="/instituicao/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Instituicao> update(@PathVariable("id") Long id, @RequestBody Instituicao instituicao) {
		LOG.info("updating instituicao: {}", instituicao);
		Instituicao instituicaoLoaded = instituicaoService.findById(instituicao.getId());
		
		if (instituicaoLoaded == null){
            LOG.info("Instituicao with id {} not found", id);
            return new ResponseEntity<Instituicao>(HttpStatus.NOT_FOUND);
        }
		
		Instituicao instituicaoSaved = instituicaoService.update(instituicao);
		
		return new ResponseEntity<Instituicao>(instituicaoSaved, HttpStatus.OK);
	}

	@ApiOperation(value = "Excluir uma conta corrente")
	@RequestMapping( value="/instituicao/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		LOG.info("deleting instituicao with id: {}", id);
		
		Instituicao instituicaoLoaded = instituicaoService.findById(id);
		
		if (instituicaoLoaded == null){
            LOG.info("Instituicao with id {} not found", id);
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
		
		instituicaoService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
