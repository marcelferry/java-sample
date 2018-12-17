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

import br.com.marcelferry.javasample.business.service.InstituicaoService;
import br.com.marcelferry.javasample.data.model.Instituicao;
import br.com.marcelferry.javasample.rest.common.CORSFilter;
import br.com.marcelferry.javasample.rest.controller.InstituicaoRestController;
import br.com.marcelferry.javasample.test.MockValues;

@RunWith(MockitoJUnitRunner.class)
public class InstituicaoRestControllerTest {
	
	private MockMvc mockMvc;
	
	private MockValues mockValues = new MockValues();
	
	@InjectMocks
	private InstituicaoRestController instituicaoRestController;
	@Mock
    private InstituicaoService instituicaoService;

	@Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(instituicaoRestController)
                .addFilters(new CORSFilter())
                .build();
    }
	
	// =========================================== Get All Instituicaos ==========================================

    @Test
    public void test_get_all_success() throws Exception {
        List<Instituicao> instituicaos = Arrays.asList(
        		newInstituicao(),
        		newInstituicao());

        when(instituicaoService.findAll()).thenReturn(instituicaos);

        mockMvc.perform(get("/api/v1/instituicao"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is( instituicaos.get(0).getId().intValue() )))
                .andExpect(jsonPath("$[0].descricao", is(instituicaos.get(0).getDescricao())))
                .andExpect(jsonPath("$[1].id", is(instituicaos.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].descricao", is(instituicaos.get(1).getDescricao())));

        verify(instituicaoService, times(1)).findAll();
        verifyNoMoreInteractions(instituicaoService);
    }

    // =========================================== Get Instituicao By ID =========================================

    @Test
    public void test_get_by_id_success() throws Exception {
        Instituicao instituicao = newInstituicao();

        when(instituicaoService.findById(instituicao.getId())).thenReturn(instituicao);

        mockMvc.perform(get("/api/v1/instituicao/{id}", instituicao.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is( instituicao.getId().intValue() )))
                .andExpect(jsonPath("$.descricao", is(instituicao.getDescricao())));

        verify(instituicaoService, times(1)).findById(instituicao.getId());
        verifyNoMoreInteractions(instituicaoService);
    }

    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {

        when(instituicaoService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/instituicao/{id}", 1))
                .andExpect(status().isNotFound());

        verify(instituicaoService, times(1)).findById(1L);
        verifyNoMoreInteractions(instituicaoService);
    }

    // =========================================== Create New Instituicao ========================================

    @Test
    public void test_create_instituicao_success() throws Exception {
        Instituicao instituicao = new Instituicao();
        instituicao.setDescricao("Arya Stark");

        when(instituicaoService.exists(instituicao)).thenReturn(false);
        when(instituicaoService.create(instituicao)).thenReturn(instituicao);
        //doNothing().when(instituicaoService).create(instituicao);

        mockMvc.perform(
                post("/api/v1/instituicao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(instituicao)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost/api/v1/instituicao/")));

        verify(instituicaoService, times(1)).exists(instituicao);
        verify(instituicaoService, times(1)).create(instituicao);
        verifyNoMoreInteractions(instituicaoService);
    }

    @Test
    public void test_create_instituicao_fail_409_conflict() throws Exception {
        Instituicao instituicao = new Instituicao();
        instituicao.setDescricao("instituicaoname exists");

        when(instituicaoService.exists(instituicao)).thenReturn(true);

        mockMvc.perform(
                post("/api/v1/instituicao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(instituicao)))
                .andExpect(status().isConflict());

        verify(instituicaoService, times(1)).exists(instituicao);
        verifyNoMoreInteractions(instituicaoService);
    }

    // =========================================== Update Existing Instituicao ===================================

    @Test
    public void test_update_instituicao_success() throws Exception {
        Instituicao instituicao = newInstituicao();

        when(instituicaoService.findById(instituicao.getId())).thenReturn(instituicao);
        when(instituicaoService.update(instituicao)).thenReturn(instituicao);
        //doNothing().when(instituicaoService).update(instituicao);

        mockMvc.perform(
                put("/api/v1/instituicao/{id}", instituicao.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(instituicao)))
                .andExpect(status().isOk());

        verify(instituicaoService, times(1)).findById(instituicao.getId());
        verify(instituicaoService, times(1)).update(instituicao);
        verifyNoMoreInteractions(instituicaoService);
    }

    @Test
    public void test_update_instituicao_fail_404_not_found() throws Exception {
        Instituicao instituicao = new Instituicao();
        instituicao.setId(Long.MAX_VALUE);
        instituicao.setDescricao("instituicao not found");

        when(instituicaoService.findById(instituicao.getId())).thenReturn(null);

        mockMvc.perform(
                put("/api/v1/instituicao/{id}", instituicao.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(instituicao)))
                .andExpect(status().isNotFound());

        verify(instituicaoService, times(1)).findById(instituicao.getId());
        verifyNoMoreInteractions(instituicaoService);
    }

    // =========================================== Delete Instituicao ============================================

    @Test
    public void test_delete_instituicao_success() throws Exception {
        Instituicao instituicao = newInstituicao();

        when(instituicaoService.findById(instituicao.getId())).thenReturn(instituicao);
        doNothing().when(instituicaoService).delete(instituicao.getId());

        mockMvc.perform(
                delete("/api/v1/instituicao/{id}", instituicao.getId()))
                .andExpect(status().isOk());

        verify(instituicaoService, times(1)).findById(instituicao.getId());
        verify(instituicaoService, times(1)).delete(instituicao.getId());
        verifyNoMoreInteractions(instituicaoService);
    }

    @Test
    public void test_delete_instituicao_fail_404_not_found() throws Exception {
    		Instituicao instituicao = new Instituicao();
        instituicao.setId(Long.MAX_VALUE);
        instituicao.setDescricao("instituicao not found");

        when(instituicaoService.findById(instituicao.getId())).thenReturn(null);

        mockMvc.perform(
                delete("/api/v1/instituicao/{id}", instituicao.getId()))
                .andExpect(status().isNotFound());

        verify(instituicaoService, times(1)).findById(instituicao.getId());
        verifyNoMoreInteractions(instituicaoService);
    }

    // =========================================== CORS Headers ===========================================

    @Test
    public void test_cors_headers() throws Exception {
        mockMvc.perform(get("/api/v1/instituicao"))
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
    
    public Instituicao newInstituicao() {

		Long id = mockValues.nextLong();
		String descricao = mockValues.nextString(20);

		Instituicao instituicao = new Instituicao();
		instituicao.setId(id);
		instituicao.setDescricao(descricao);
		return instituicao;
	}

}
