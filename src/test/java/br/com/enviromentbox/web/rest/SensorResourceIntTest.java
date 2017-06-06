package br.com.enviromentbox.web.rest;

import br.com.enviromentbox.EnviromentBoxApp;

import br.com.enviromentbox.domain.Sensor;
import br.com.enviromentbox.domain.Device;
import br.com.enviromentbox.domain.TipoSensor;
import br.com.enviromentbox.repository.SensorRepository;
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
 * Test class for the SensorResource REST controller.
 *
 * @see SensorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EnviromentBoxApp.class)
public class SensorResourceIntTest {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSensorMockMvc;

    private Sensor sensor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SensorResource sensorResource = new SensorResource(sensorRepository);
        this.restSensorMockMvc = MockMvcBuilders.standaloneSetup(sensorResource)
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
    public static Sensor createEntity(EntityManager em) {
        Sensor sensor = new Sensor();
        // Add required entity
        Device id_device = DeviceResourceIntTest.createEntity(em);
        em.persist(id_device);
        em.flush();
        sensor.setId_device(id_device);
        // Add required entity
        TipoSensor tipoSensor = TipoSensorResourceIntTest.createEntity(em);
        em.persist(tipoSensor);
        em.flush();
        sensor.setTipoSensor(tipoSensor);
        return sensor;
    }

    @Before
    public void initTest() {
        sensor = createEntity(em);
    }

    @Test
    @Transactional
    public void createSensor() throws Exception {
        int databaseSizeBeforeCreate = sensorRepository.findAll().size();

        // Create the Sensor
        restSensorMockMvc.perform(post("/api/sensors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensor)))
            .andExpect(status().isCreated());

        // Validate the Sensor in the database
        List<Sensor> sensorList = sensorRepository.findAll();
        assertThat(sensorList).hasSize(databaseSizeBeforeCreate + 1);
        Sensor testSensor = sensorList.get(sensorList.size() - 1);
    }

    @Test
    @Transactional
    public void createSensorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sensorRepository.findAll().size();

        // Create the Sensor with an existing ID
        sensor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSensorMockMvc.perform(post("/api/sensors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensor)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Sensor> sensorList = sensorRepository.findAll();
        assertThat(sensorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSensors() throws Exception {
        // Initialize the database
        sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList
        restSensorMockMvc.perform(get("/api/sensors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensor.getId().intValue())));
    }

    @Test
    @Transactional
    public void getSensor() throws Exception {
        // Initialize the database
        sensorRepository.saveAndFlush(sensor);

        // Get the sensor
        restSensorMockMvc.perform(get("/api/sensors/{id}", sensor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sensor.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSensor() throws Exception {
        // Get the sensor
        restSensorMockMvc.perform(get("/api/sensors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSensor() throws Exception {
        // Initialize the database
        sensorRepository.saveAndFlush(sensor);
        int databaseSizeBeforeUpdate = sensorRepository.findAll().size();

        // Update the sensor
        Sensor updatedSensor = sensorRepository.findOne(sensor.getId());

        restSensorMockMvc.perform(put("/api/sensors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSensor)))
            .andExpect(status().isOk());

        // Validate the Sensor in the database
        List<Sensor> sensorList = sensorRepository.findAll();
        assertThat(sensorList).hasSize(databaseSizeBeforeUpdate);
        Sensor testSensor = sensorList.get(sensorList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSensor() throws Exception {
        int databaseSizeBeforeUpdate = sensorRepository.findAll().size();

        // Create the Sensor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSensorMockMvc.perform(put("/api/sensors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sensor)))
            .andExpect(status().isCreated());

        // Validate the Sensor in the database
        List<Sensor> sensorList = sensorRepository.findAll();
        assertThat(sensorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSensor() throws Exception {
        // Initialize the database
        sensorRepository.saveAndFlush(sensor);
        int databaseSizeBeforeDelete = sensorRepository.findAll().size();

        // Get the sensor
        restSensorMockMvc.perform(delete("/api/sensors/{id}", sensor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sensor> sensorList = sensorRepository.findAll();
        assertThat(sensorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sensor.class);
    }
}
