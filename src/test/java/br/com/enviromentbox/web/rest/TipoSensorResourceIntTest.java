package br.com.enviromentbox.web.rest;

import br.com.enviromentbox.EnviromentBoxApp;

import br.com.enviromentbox.domain.TipoSensor;
import br.com.enviromentbox.repository.TipoSensorRepository;
import br.com.enviromentbox.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TipoSensorResource REST controller.
 *
 * @see TipoSensorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EnviromentBoxApp.class)
public class TipoSensorResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private TipoSensorRepository tipoSensorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoSensorMockMvc;

    private TipoSensor tipoSensor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipoSensorResource tipoSensorResource = new TipoSensorResource(tipoSensorRepository);
        this.restTipoSensorMockMvc = MockMvcBuilders.standaloneSetup(tipoSensorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoSensor createEntity(EntityManager em) {
        TipoSensor tipoSensor = new TipoSensor()
            .nome(DEFAULT_NOME);
        return tipoSensor;
    }

    @Before
    public void initTest() {
        tipoSensor = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoSensor() throws Exception {
        int databaseSizeBeforeCreate = tipoSensorRepository.findAll().size();

        // Create the TipoSensor
        restTipoSensorMockMvc.perform(post("/api/tipo-sensors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSensor)))
            .andExpect(status().isCreated());

        // Validate the TipoSensor in the database
        List<TipoSensor> tipoSensorList = tipoSensorRepository.findAll();
        assertThat(tipoSensorList).hasSize(databaseSizeBeforeCreate + 1);
        TipoSensor testTipoSensor = tipoSensorList.get(tipoSensorList.size() - 1);
        assertThat(testTipoSensor.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createTipoSensorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoSensorRepository.findAll().size();

        // Create the TipoSensor with an existing ID
        tipoSensor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoSensorMockMvc.perform(post("/api/tipo-sensors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSensor)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TipoSensor> tipoSensorList = tipoSensorRepository.findAll();
        assertThat(tipoSensorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoSensorRepository.findAll().size();
        // set the field null
        tipoSensor.setNome(null);

        // Create the TipoSensor, which fails.

        restTipoSensorMockMvc.perform(post("/api/tipo-sensors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSensor)))
            .andExpect(status().isBadRequest());

        List<TipoSensor> tipoSensorList = tipoSensorRepository.findAll();
        assertThat(tipoSensorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoSensors() throws Exception {
        // Initialize the database
        tipoSensorRepository.saveAndFlush(tipoSensor);

        // Get all the tipoSensorList
        restTipoSensorMockMvc.perform(get("/api/tipo-sensors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoSensor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getTipoSensor() throws Exception {
        // Initialize the database
        tipoSensorRepository.saveAndFlush(tipoSensor);

        // Get the tipoSensor
        restTipoSensorMockMvc.perform(get("/api/tipo-sensors/{id}", tipoSensor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoSensor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoSensor() throws Exception {
        // Get the tipoSensor
        restTipoSensorMockMvc.perform(get("/api/tipo-sensors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoSensor() throws Exception {
        // Initialize the database
        tipoSensorRepository.saveAndFlush(tipoSensor);
        int databaseSizeBeforeUpdate = tipoSensorRepository.findAll().size();

        // Update the tipoSensor
        TipoSensor updatedTipoSensor = tipoSensorRepository.findOne(tipoSensor.getId());
        updatedTipoSensor
            .nome(UPDATED_NOME);

        restTipoSensorMockMvc.perform(put("/api/tipo-sensors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoSensor)))
            .andExpect(status().isOk());

        // Validate the TipoSensor in the database
        List<TipoSensor> tipoSensorList = tipoSensorRepository.findAll();
        assertThat(tipoSensorList).hasSize(databaseSizeBeforeUpdate);
        TipoSensor testTipoSensor = tipoSensorList.get(tipoSensorList.size() - 1);
        assertThat(testTipoSensor.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoSensor() throws Exception {
        int databaseSizeBeforeUpdate = tipoSensorRepository.findAll().size();

        // Create the TipoSensor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoSensorMockMvc.perform(put("/api/tipo-sensors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSensor)))
            .andExpect(status().isCreated());

        // Validate the TipoSensor in the database
        List<TipoSensor> tipoSensorList = tipoSensorRepository.findAll();
        assertThat(tipoSensorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoSensor() throws Exception {
        // Initialize the database
        tipoSensorRepository.saveAndFlush(tipoSensor);
        int databaseSizeBeforeDelete = tipoSensorRepository.findAll().size();

        // Get the tipoSensor
        restTipoSensorMockMvc.perform(delete("/api/tipo-sensors/{id}", tipoSensor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoSensor> tipoSensorList = tipoSensorRepository.findAll();
        assertThat(tipoSensorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoSensor.class);
    }
}
