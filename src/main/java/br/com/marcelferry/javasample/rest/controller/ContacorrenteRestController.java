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

import br.com.marcelferry.javasample.business.service.ContacorrenteService;
import br.com.marcelferry.javasample.data.model.Contacorrente;
import br.com.marcelferry.javasample.web.listitem.ContacorrenteListItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Spring MVC controller for 'Contacorrente' management.
 */
@RestController
@RequestMapping("/api/v1")
@Api( value = "/api/v1/contacorrente", description = "Manipulação de Conta Corrente")
public class ContacorrenteRestController {

	private final Logger LOG = LoggerFactory.getLogger(ContacorrenteRestController.class);
	
	@Resource
	private ContacorrenteService contacorrenteService;
	
	@RequestMapping( value="/items/contacorrente",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContacorrenteListItem>> findAllAsListItems() {
		List<Contacorrente> list = contacorrenteService.findAll();
		List<ContacorrenteListItem> items = new LinkedList<ContacorrenteListItem>();
		
		if (list == null || list.isEmpty()){
            LOG.info("no Contacorrente found");
            return new ResponseEntity<List<ContacorrenteListItem>>(HttpStatus.NO_CONTENT);
        }
		
		for ( Contacorrente contacorrente : list ) {
			items.add(new ContacorrenteListItem( contacorrente ) );
		}
		return new ResponseEntity<List<ContacorrenteListItem>>(items,  HttpStatus.OK);
	}
	
	@ApiOperation(value = "Visualizar uma lista de todas as contas correntes disponíveis", response = Contacorrente.class, responseContainer="List")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Lista retornada com sucesso"),
	        @ApiResponse(code = 404, message = "O recurso que está tentando acessar não existe")
	}
	)
	@RequestMapping( value="/contacorrente",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Contacorrente>> findAll() {
		LOG.info("getting all Contacorrente");
		List<Contacorrente> list = contacorrenteService.findAll();
		
		if (list == null || list.isEmpty()){
            LOG.info("no Contacorrente found");
            return new ResponseEntity<List<Contacorrente>>(HttpStatus.NO_CONTENT);
        }
		
		return new ResponseEntity<List<Contacorrente>>(list,  HttpStatus.OK);
	}

	@ApiOperation(value = "Buscar uma conta corrente pelo ID", response = Contacorrente.class)
	@RequestMapping( value="/contacorrente/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Contacorrente> findOne(@PathVariable("id") Long id) {
		LOG.info("getting Contacorrente with id: {}", id);
		Contacorrente contacorrente = contacorrenteService.findById(id);
		if (contacorrente == null){
            LOG.info("Contacorrente with id {} not found", id);
            return new ResponseEntity<Contacorrente>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Contacorrente>(contacorrente, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Adicionar uma conta corrente", response = Contacorrente.class)
	@RequestMapping( value="/contacorrente",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@RequestBody Contacorrente contacorrente, UriComponentsBuilder ucBuilder) {
		LOG.info("creating new contacorrente: {}", contacorrente);

        if (contacorrenteService.exists(contacorrente)){
            LOG.info("a contacorrente with name " + contacorrente.getDescricao() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        
        Contacorrente contacorrenteCreated = contacorrenteService.create(contacorrente);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/v1/contacorrente/{id}").buildAndExpand(contacorrenteCreated.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Atualizar uma conta corrente", response = Contacorrente.class)
	@RequestMapping( value="/contacorrente/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Contacorrente> update(@PathVariable("id") Long id, @RequestBody Contacorrente contacorrente) {
		LOG.info("updating contacorrente: {}", contacorrente);
		Contacorrente contacorrenteLoaded = contacorrenteService.findById(contacorrente.getId());
		
		if (contacorrenteLoaded == null){
            LOG.info("Contacorrente with id {} not found", id);
            return new ResponseEntity<Contacorrente>(HttpStatus.NOT_FOUND);
        }
		
		Contacorrente contacorrenteSaved = contacorrenteService.update(contacorrente);
		
		return new ResponseEntity<Contacorrente>(contacorrenteSaved, HttpStatus.OK);
	}

	@ApiOperation(value = "Excluir uma conta corrente")
	@RequestMapping( value="/contacorrente/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		LOG.info("deleting contacorrente with id: {}", id);
		
		Contacorrente contacorrenteLoaded = contacorrenteService.findById(id);
		
		if (contacorrenteLoaded == null){
            LOG.info("Contacorrente with id {} not found", id);
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
		
		contacorrenteService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
