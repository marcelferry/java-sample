package br.com.marcelferry.javasample.rest.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.marcelferry.javasample.business.service.TipoContaService;
import br.com.marcelferry.javasample.data.model.TipoConta;
import br.com.marcelferry.javasample.rest.common.CORSFilter;
import br.com.marcelferry.javasample.rest.controller.TipoContaRestController;
import br.com.marcelferry.javasample.test.MockValues;

@RunWith(MockitoJUnitRunner.class)
public class TipoContaRestControllerTest {
	
	private MockMvc mockMvc;
	
	private MockValues mockValues = new MockValues();
	
	@InjectMocks
	private TipoContaRestController tipoContaRestController;
	@Mock
    private TipoContaService tipoContaService;


	@Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(tipoContaRestController)
                .addFilters(new CORSFilter())
                .build();
    }
	
	// =========================================== Get All TipoContas ==========================================

    @Test
    public void test_get_all_success() throws Exception {
        List<TipoConta> tipoContas = Arrays.asList(
        		newTipoConta(),
        		newTipoConta());

        when(tipoContaService.findAll()).thenReturn(tipoContas);

        mockMvc.perform(get("/api/v1/tipoConta"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is( tipoContas.get(0).getId().intValue() )))
                .andExpect(jsonPath("$[0].descricao", is(tipoContas.get(0).getDescricao())))
                .andExpect(jsonPath("$[1].id", is(tipoContas.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].descricao", is(tipoContas.get(1).getDescricao())));

        verify(tipoContaService, times(1)).findAll();
        verifyNoMoreInteractions(tipoContaService);
    }

    // =========================================== Get TipoConta By ID =========================================

    @Test
    public void test_get_by_id_success() throws Exception {
        TipoConta tipoConta = newTipoConta();

        when(tipoContaService.findById(tipoConta.getId())).thenReturn(tipoConta);

        mockMvc.perform(get("/api/v1/tipoConta/{id}", tipoConta.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is( tipoConta.getId().intValue() )))
                .andExpect(jsonPath("$.descricao", is(tipoConta.getDescricao())));

        verify(tipoContaService, times(1)).findById(tipoConta.getId());
        verifyNoMoreInteractions(tipoContaService);
    }

    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {

        when(tipoContaService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/tipoConta/{id}", 1))
                .andExpect(status().isNotFound());

        verify(tipoContaService, times(1)).findById(1L);
        verifyNoMoreInteractions(tipoContaService);
    }

    // =========================================== Create New TipoConta ========================================

    @Test
    public void test_create_tipoConta_success() throws Exception {
        TipoConta tipoConta = new TipoConta();
        tipoConta.setDescricao("Arya Stark");

        when(tipoContaService.exists(tipoConta)).thenReturn(false);
        when(tipoContaService.create(tipoConta)).thenReturn(tipoConta);
        //doNothing().when(tipoContaService).create(tipoConta);

        mockMvc.perform(
                post("/api/v1/tipoConta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tipoConta)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost/api/v1/tipoConta/")));

        verify(tipoContaService, times(1)).exists(tipoConta);
        verify(tipoContaService, times(1)).create(tipoConta);
        verifyNoMoreInteractions(tipoContaService);
    }

    @Test
    public void test_create_tipoConta_fail_409_conflict() throws Exception {
        TipoConta tipoConta = new TipoConta();
        tipoConta.setDescricao("tipoContaname exists");

        when(tipoContaService.exists(tipoConta)).thenReturn(true);

        mockMvc.perform(
                post("/api/v1/tipoConta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tipoConta)))
                .andExpect(status().isConflict());

        verify(tipoContaService, times(1)).exists(tipoConta);
        verifyNoMoreInteractions(tipoContaService);
    }

    // =========================================== Update Existing TipoConta ===================================

    @Test
    public void test_update_tipoConta_success() throws Exception {
        TipoConta tipoConta = newTipoConta();

        when(tipoContaService.findById(tipoConta.getId())).thenReturn(tipoConta);
        when(tipoContaService.update(tipoConta)).thenReturn(tipoConta);
        //doNothing().when(tipoContaService).update(tipoConta);

        mockMvc.perform(
                put("/api/v1/tipoConta/{id}", tipoConta.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tipoConta)))
                .andExpect(status().isOk());

        verify(tipoContaService, times(1)).findById(tipoConta.getId());
        verify(tipoContaService, times(1)).update(tipoConta);
        verifyNoMoreInteractions(tipoContaService);
    }

    @Test
    public void test_update_tipoConta_fail_404_not_found() throws Exception {
        TipoConta tipoConta = new TipoConta();
        tipoConta.setId(Long.MAX_VALUE);
        tipoConta.setDescricao("tipoConta not found");

        when(tipoContaService.findById(tipoConta.getId())).thenReturn(null);

        mockMvc.perform(
                put("/api/v1/tipoConta/{id}", tipoConta.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tipoConta)))
                .andExpect(status().isNotFound());

        verify(tipoContaService, times(1)).findById(tipoConta.getId());
        verifyNoMoreInteractions(tipoContaService);
    }

    // =========================================== Delete TipoConta ============================================

    @Test
    public void test_delete_tipoConta_success() throws Exception {
        TipoConta tipoConta = newTipoConta();

        when(tipoContaService.findById(tipoConta.getId())).thenReturn(tipoConta);
        doNothing().when(tipoContaService).delete(tipoConta.getId());

        mockMvc.perform(
                delete("/api/v1/tipoConta/{id}", tipoConta.getId()))
                .andExpect(status().isOk());

        verify(tipoContaService, times(1)).findById(tipoConta.getId());
        verify(tipoContaService, times(1)).delete(tipoConta.getId());
        verifyNoMoreInteractions(tipoContaService);
    }

    @Test
    public void test_delete_tipoConta_fail_404_not_found() throws Exception {
    	TipoConta tipoConta = new TipoConta();
        tipoConta.setId(Long.MAX_VALUE);
        tipoConta.setDescricao("tipoConta not found");

        when(tipoContaService.findById(tipoConta.getId())).thenReturn(null);

        mockMvc.perform(
                delete("/api/v1/tipoConta/{id}", tipoConta.getId()))
                .andExpect(status().isNotFound());

        verify(tipoContaService, times(1)).findById(tipoConta.getId());
        verifyNoMoreInteractions(tipoContaService);
    }

    // =========================================== CORS Headers ===========================================

    @Test
    public void test_cors_headers() throws Exception {
        mockMvc.perform(get("/api/v1/tipoConta"))
                .andExpect(header().string("Access-Control-Allow-Origin", "*"))
                .andExpect(header().string("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"))
                .andExpect(header().string("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept"))
                .andExpect(header().string("Access-Control-Max-Age", "1800"));
    }

    /*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
    public TipoConta newTipoConta() {

		Long id = mockValues.nextLong();
		String descricao = mockValues.nextString(20);

		TipoConta tipoConta = new TipoConta();
		tipoConta.setId(id);
		tipoConta.setDescricao(descricao);
		return tipoConta;
	}

}
