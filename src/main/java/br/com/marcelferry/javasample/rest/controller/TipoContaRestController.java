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

import br.com.marcelferry.javasample.business.service.TipoContaService;
import br.com.marcelferry.javasample.data.model.TipoConta;
import br.com.marcelferry.javasample.web.listitem.TipoContaListItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Spring MVC controller for 'TipoConta' management.
 */
@RestController
@RequestMapping("/api/v1")
@Api( value = "/api/v1/tipoConta", description = "Manipulação de Conta Corrente")
public class TipoContaRestController {

	private final Logger LOG = LoggerFactory.getLogger(TipoContaRestController.class);
	
	@Resource
	private TipoContaService tipoContaService;
	
	@RequestMapping( value="/items/tipoConta",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TipoContaListItem>> findAllAsListItems() {
		List<TipoConta> list = tipoContaService.findAll();
		List<TipoContaListItem> items = new LinkedList<TipoContaListItem>();
		
		if (list == null || list.isEmpty()){
            LOG.info("no TipoConta found");
            return new ResponseEntity<List<TipoContaListItem>>(HttpStatus.NO_CONTENT);
        }
		
		for ( TipoConta tipoConta : list ) {
			items.add(new TipoContaListItem( tipoConta ) );
		}
		return new ResponseEntity<List<TipoContaListItem>>(items,  HttpStatus.OK);
	}
	
	@ApiOperation(value = "Visualizar uma lista de todas as contas correntes disponíveis", response = TipoConta.class, responseContainer="List")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Lista retornada com sucesso"),
	        @ApiResponse(code = 404, message = "O recurso que está tentando acessar não existe")
	}
	)
	@RequestMapping( value="/tipoConta",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TipoConta>> findAll() {
		LOG.info("getting all TipoConta");
		List<TipoConta> list = tipoContaService.findAll();
		
		if (list == null || list.isEmpty()){
            LOG.info("no TipoConta found");
            return new ResponseEntity<List<TipoConta>>(HttpStatus.NO_CONTENT);
        }
		
		return new ResponseEntity<List<TipoConta>>(list,  HttpStatus.OK);
	}

	@ApiOperation(value = "Buscar uma conta corrente pelo ID", response = TipoConta.class)
	@RequestMapping( value="/tipoConta/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipoConta> findOne(@PathVariable("id") Long id) {
		LOG.info("getting TipoConta with id: {}", id);
		TipoConta tipoConta = tipoContaService.findById(id);
		if (tipoConta == null){
            LOG.info("TipoConta with id {} not found", id);
            return new ResponseEntity<TipoConta>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<TipoConta>(tipoConta, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Adicionar uma conta corrente", response = TipoConta.class)
	@RequestMapping( value="/tipoConta",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@RequestBody TipoConta tipoConta, UriComponentsBuilder ucBuilder) {
		LOG.info("creating new tipoConta: {}", tipoConta);

        if (tipoContaService.exists(tipoConta)){
            LOG.info("a tipoConta with name " + tipoConta.getDescricao() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        
        TipoConta tipoContaCreated = tipoContaService.create(tipoConta);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/v1/tipoConta/{id}").buildAndExpand(tipoContaCreated.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Atualizar uma conta corrente", response = TipoConta.class)
	@RequestMapping( value="/tipoConta/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipoConta> update(@PathVariable("id") Long id, @RequestBody TipoConta tipoConta) {
		LOG.info("updating tipoConta: {}", tipoConta);
		TipoConta tipoContaLoaded = tipoContaService.findById(tipoConta.getId());
		
		if (tipoContaLoaded == null){
            LOG.info("TipoConta with id {} not found", id);
            return new ResponseEntity<TipoConta>(HttpStatus.NOT_FOUND);
        }
		
		TipoConta tipoContaSaved = tipoContaService.update(tipoConta);
		
		return new ResponseEntity<TipoConta>(tipoContaSaved, HttpStatus.OK);
	}

	@ApiOperation(value = "Excluir uma conta corrente")
	@RequestMapping( value="/tipoConta/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		LOG.info("deleting tipoConta with id: {}", id);
		
		TipoConta tipoContaLoaded = tipoContaService.findById(id);
		
		if (tipoContaLoaded == null){
            LOG.info("TipoConta with id {} not found", id);
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
		
		tipoContaService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
