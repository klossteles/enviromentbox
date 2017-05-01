package br.com.enviromentbox.web.rest;

import br.com.enviromentbox.EnviromentBoxApp;

import br.com.enviromentbox.domain.Medicao;
import br.com.enviromentbox.domain.Sensor;
import br.com.enviromentbox.domain.Device;
import br.com.enviromentbox.repository.MedicaoRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MedicaoResource REST controller.
 *
 * @see MedicaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EnviromentBoxApp.class)
public class MedicaoResourceIntTest {

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    @Autowired
    private MedicaoRepository medicaoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicaoMockMvc;

    private Medicao medicao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MedicaoResource medicaoResource = new MedicaoResource(medicaoRepository);
        this.restMedicaoMockMvc = MockMvcBuilders.standaloneSetup(medicaoResource)
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
    public static Medicao createEntity(EntityManager em) {
        Medicao medicao = new Medicao()
            .valor(DEFAULT_VALOR);
        // Add required entity
        Sensor sensor = SensorResourceIntTest.createEntity(em);
        em.persist(sensor);
        em.flush();
        medicao.setSensor(sensor);
        // Add required entity
        Device device = DeviceResourceIntTest.createEntity(em);
        em.persist(device);
        em.flush();
        medicao.setDevice(device);
        return medicao;
    }

    @Before
    public void initTest() {
        medicao = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicao() throws Exception {
        int databaseSizeBeforeCreate = medicaoRepository.findAll().size();

        // Create the Medicao
        restMedicaoMockMvc.perform(post("/api/medicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicao)))
            .andExpect(status().isCreated());

        // Validate the Medicao in the database
        List<Medicao> medicaoList = medicaoRepository.findAll();
        assertThat(medicaoList).hasSize(databaseSizeBeforeCreate + 1);
        Medicao testMedicao = medicaoList.get(medicaoList.size() - 1);
        assertThat(testMedicao.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createMedicaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicaoRepository.findAll().size();

        // Create the Medicao with an existing ID
        medicao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicaoMockMvc.perform(post("/api/medicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicao)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Medicao> medicaoList = medicaoRepository.findAll();
        assertThat(medicaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicaoRepository.findAll().size();
        // set the field null
        medicao.setValor(null);

        // Create the Medicao, which fails.

        restMedicaoMockMvc.perform(post("/api/medicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicao)))
            .andExpect(status().isBadRequest());

        List<Medicao> medicaoList = medicaoRepository.findAll();
        assertThat(medicaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedicaos() throws Exception {
        // Initialize the database
        medicaoRepository.saveAndFlush(medicao);

        // Get all the medicaoList
        restMedicaoMockMvc.perform(get("/api/medicaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }

    @Test
    @Transactional
    public void getMedicao() throws Exception {
        // Initialize the database
        medicaoRepository.saveAndFlush(medicao);

        // Get the medicao
        restMedicaoMockMvc.perform(get("/api/medicaos/{id}", medicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicao.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicao() throws Exception {
        // Get the medicao
        restMedicaoMockMvc.perform(get("/api/medicaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicao() throws Exception {
        // Initialize the database
        medicaoRepository.saveAndFlush(medicao);
        int databaseSizeBeforeUpdate = medicaoRepository.findAll().size();

        // Update the medicao
        Medicao updatedMedicao = medicaoRepository.findOne(medicao.getId());
        updatedMedicao
            .valor(UPDATED_VALOR);

        restMedicaoMockMvc.perform(put("/api/medicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicao)))
            .andExpect(status().isOk());

        // Validate the Medicao in the database
        List<Medicao> medicaoList = medicaoRepository.findAll();
        assertThat(medicaoList).hasSize(databaseSizeBeforeUpdate);
        Medicao testMedicao = medicaoList.get(medicaoList.size() - 1);
        assertThat(testMedicao.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicao() throws Exception {
        int databaseSizeBeforeUpdate = medicaoRepository.findAll().size();

        // Create the Medicao

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedicaoMockMvc.perform(put("/api/medicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicao)))
            .andExpect(status().isCreated());

        // Validate the Medicao in the database
        List<Medicao> medicaoList = medicaoRepository.findAll();
        assertThat(medicaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedicao() throws Exception {
        // Initialize the database
        medicaoRepository.saveAndFlush(medicao);
        int databaseSizeBeforeDelete = medicaoRepository.findAll().size();

        // Get the medicao
        restMedicaoMockMvc.perform(delete("/api/medicaos/{id}", medicao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Medicao> medicaoList = medicaoRepository.findAll();
        assertThat(medicaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medicao.class);
    }
}
