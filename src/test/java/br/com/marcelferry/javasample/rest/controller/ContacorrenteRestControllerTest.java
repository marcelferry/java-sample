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

import br.com.marcelferry.javasample.business.service.ContacorrenteService;
import br.com.marcelferry.javasample.data.model.Contacorrente;
import br.com.marcelferry.javasample.rest.common.CORSFilter;
import br.com.marcelferry.javasample.rest.controller.ContacorrenteRestController;
import br.com.marcelferry.javasample.test.MockValues;

@RunWith(MockitoJUnitRunner.class)
public class ContacorrenteRestControllerTest {
	
	private MockMvc mockMvc;
	
	private MockValues mockValues = new MockValues();
	
	@InjectMocks
	private ContacorrenteRestController contacorrenteRestController;
	@Mock
    private ContacorrenteService contacorrenteService;


	@Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(contacorrenteRestController)
                .addFilters(new CORSFilter())
                .build();
    }
	
	// =========================================== Get All Contacorrentes ==========================================

    @Test
    public void test_get_all_success() throws Exception {
        List<Contacorrente> contacorrentes = Arrays.asList(
        		newContacorrente(),
        		newContacorrente());

        when(contacorrenteService.findAll()).thenReturn(contacorrentes);

        mockMvc.perform(get("/api/v1/contacorrente"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is( contacorrentes.get(0).getId().intValue() )))
                .andExpect(jsonPath("$[0].descricao", is(contacorrentes.get(0).getDescricao())))
                .andExpect(jsonPath("$[1].id", is(contacorrentes.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].descricao", is(contacorrentes.get(1).getDescricao())));

        verify(contacorrenteService, times(1)).findAll();
        verifyNoMoreInteractions(contacorrenteService);
    }

    // =========================================== Get Contacorrente By ID =========================================

    @Test
    public void test_get_by_id_success() throws Exception {
        Contacorrente contacorrente = newContacorrente();

        when(contacorrenteService.findById(contacorrente.getId())).thenReturn(contacorrente);

        mockMvc.perform(get("/api/v1/contacorrente/{id}", contacorrente.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is( contacorrente.getId().intValue() )))
                .andExpect(jsonPath("$.descricao", is(contacorrente.getDescricao())));

        verify(contacorrenteService, times(1)).findById(contacorrente.getId());
        verifyNoMoreInteractions(contacorrenteService);
    }

    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {

        when(contacorrenteService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/contacorrente/{id}", 1))
                .andExpect(status().isNotFound());

        verify(contacorrenteService, times(1)).findById(1L);
        verifyNoMoreInteractions(contacorrenteService);
    }

    // =========================================== Create New Contacorrente ========================================

    @Test
    public void test_create_contacorrente_success() throws Exception {
        Contacorrente contacorrente = new Contacorrente();
        contacorrente.setDescricao("Arya Stark");

        when(contacorrenteService.exists(contacorrente)).thenReturn(false);
        when(contacorrenteService.create(contacorrente)).thenReturn(contacorrente);
        //doNothing().when(contacorrenteService).create(contacorrente);

        mockMvc.perform(
                post("/api/v1/contacorrente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(contacorrente)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost/api/v1/contacorrente/")));

        verify(contacorrenteService, times(1)).exists(contacorrente);
        verify(contacorrenteService, times(1)).create(contacorrente);
        verifyNoMoreInteractions(contacorrenteService);
    }

    @Test
    public void test_create_contacorrente_fail_409_conflict() throws Exception {
        Contacorrente contacorrente = new Contacorrente();
        contacorrente.setDescricao("contacorrentename exists");

        when(contacorrenteService.exists(contacorrente)).thenReturn(true);

        mockMvc.perform(
                post("/api/v1/contacorrente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(contacorrente)))
                .andExpect(status().isConflict());

        verify(contacorrenteService, times(1)).exists(contacorrente);
        verifyNoMoreInteractions(contacorrenteService);
    }

    // =========================================== Update Existing Contacorrente ===================================

    @Test
    public void test_update_contacorrente_success() throws Exception {
        Contacorrente contacorrente = newContacorrente();

        when(contacorrenteService.findById(contacorrente.getId())).thenReturn(contacorrente);
        when(contacorrenteService.update(contacorrente)).thenReturn(contacorrente);
        //doNothing().when(contacorrenteService).update(contacorrente);

        mockMvc.perform(
                put("/api/v1/contacorrente/{id}", contacorrente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(contacorrente)))
                .andExpect(status().isOk());

        verify(contacorrenteService, times(1)).findById(contacorrente.getId());
        verify(contacorrenteService, times(1)).update(contacorrente);
        verifyNoMoreInteractions(contacorrenteService);
    }

    @Test
    public void test_update_contacorrente_fail_404_not_found() throws Exception {
        Contacorrente contacorrente = new Contacorrente();
        contacorrente.setId(Long.MAX_VALUE);
        contacorrente.setDescricao("contacorrente not found");
        
        when(contacorrenteService.findById(contacorrente.getId())).thenReturn(null);

        mockMvc.perform(
                put("/api/v1/contacorrente/{id}", contacorrente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(contacorrente)))
                .andExpect(status().isNotFound());

        verify(contacorrenteService, times(1)).findById(contacorrente.getId());
        verifyNoMoreInteractions(contacorrenteService);
    }

    // =========================================== Delete Contacorrente ============================================

    @Test
    public void test_delete_contacorrente_success() throws Exception {
        Contacorrente contacorrente = newContacorrente();

        when(contacorrenteService.findById(contacorrente.getId())).thenReturn(contacorrente);
        doNothing().when(contacorrenteService).delete(contacorrente.getId());

        mockMvc.perform(
                delete("/api/v1/contacorrente/{id}", contacorrente.getId()))
                .andExpect(status().isOk());

        verify(contacorrenteService, times(1)).findById(contacorrente.getId());
        verify(contacorrenteService, times(1)).delete(contacorrente.getId());
        verifyNoMoreInteractions(contacorrenteService);
    }

    @Test
    public void test_delete_contacorrente_fail_404_not_found() throws Exception {
    		Contacorrente contacorrente = new Contacorrente();
        contacorrente.setId(Long.MAX_VALUE);
        contacorrente.setDescricao("contacorrente not found");
        
        when(contacorrenteService.findById(contacorrente.getId())).thenReturn(null);

        mockMvc.perform(
                delete("/api/v1/contacorrente/{id}", contacorrente.getId()))
                .andExpect(status().isNotFound());

        verify(contacorrenteService, times(1)).findById(contacorrente.getId());
        verifyNoMoreInteractions(contacorrenteService);
    }

    // =========================================== CORS Headers ===========================================

    @Test
    public void test_cors_headers() throws Exception {
        mockMvc.perform(get("/api/v1/contacorrente"))
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
	
    public Contacorrente newContacorrente() {

		Long id = mockValues.nextLong();
		String descricao = mockValues.nextString(20);

		Contacorrente contacorrente = new Contacorrente();
		contacorrente.setId(id);
		contacorrente.setDescricao(descricao);
		return contacorrente;
	}
	

}
