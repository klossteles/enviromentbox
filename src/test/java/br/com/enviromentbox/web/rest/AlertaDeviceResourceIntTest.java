package br.com.enviromentbox.web.rest;

import br.com.enviromentbox.EnviromentBoxApp;

import br.com.enviromentbox.domain.AlertaDevice;
import br.com.enviromentbox.domain.Device;
import br.com.enviromentbox.domain.Sensor;
import br.com.enviromentbox.repository.AlertaDeviceRepository;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AlertaDeviceResource REST controller.
 *
 * @see AlertaDeviceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EnviromentBoxApp.class)
public class AlertaDeviceResourceIntTest {

    private static final Calendar cal = Calendar.getInstance();
    private static final Timestamp DEFAULT_DATA_HORA = new Timestamp(cal.getTimeInMillis());
    private static final Timestamp UPDATED_DATA_HORA = new Timestamp(cal.getTimeInMillis());

    private static final Integer DEFAULT_PROCESSADO = 0;
    private static final Integer UPDATED_PROCESSADO = 1;

    @Autowired
    private AlertaDeviceRepository alertaDeviceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAlertaDeviceMockMvc;

    private AlertaDevice alertaDevice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlertaDeviceResource alertaDeviceResource = new AlertaDeviceResource(alertaDeviceRepository);
        this.restAlertaDeviceMockMvc = MockMvcBuilders.standaloneSetup(alertaDeviceResource)
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
    public static AlertaDevice createEntity(EntityManager em) {
        AlertaDevice alertaDevice = new AlertaDevice()
            .data_hora(DEFAULT_DATA_HORA)
            .processado(DEFAULT_PROCESSADO);
        // Add required entity
        Device device = DeviceResourceIntTest.createEntity(em);
        em.persist(device);
        em.flush();
        alertaDevice.setDevice(device);
        // Add required entity
        Sensor sensor = SensorResourceIntTest.createEntity(em);
        em.persist(sensor);
        em.flush();
        alertaDevice.setSensor(sensor);
        return alertaDevice;
    }

    @Before
    public void initTest() {
        alertaDevice = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlertaDevice() throws Exception {
        int databaseSizeBeforeCreate = alertaDeviceRepository.findAll().size();

        // Create the AlertaDevice
        restAlertaDeviceMockMvc.perform(post("/api/alerta-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertaDevice)))
            .andExpect(status().isCreated());

        // Validate the AlertaDevice in the database
        List<AlertaDevice> alertaDeviceList = alertaDeviceRepository.findAll();
        assertThat(alertaDeviceList).hasSize(databaseSizeBeforeCreate + 1);
        AlertaDevice testAlertaDevice = alertaDeviceList.get(alertaDeviceList.size() - 1);
        assertThat(testAlertaDevice.getData_hora()).isEqualTo(DEFAULT_DATA_HORA);
        assertThat(testAlertaDevice.getProcessado()).isEqualTo(DEFAULT_PROCESSADO);
    }

    @Test
    @Transactional
    public void createAlertaDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertaDeviceRepository.findAll().size();

        // Create the AlertaDevice with an existing ID
        alertaDevice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertaDeviceMockMvc.perform(post("/api/alerta-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertaDevice)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AlertaDevice> alertaDeviceList = alertaDeviceRepository.findAll();
        assertThat(alertaDeviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkData_horaIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaDeviceRepository.findAll().size();
        // set the field null
        alertaDevice.setData_hora(null);

        // Create the AlertaDevice, which fails.

        restAlertaDeviceMockMvc.perform(post("/api/alerta-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertaDevice)))
            .andExpect(status().isBadRequest());

        List<AlertaDevice> alertaDeviceList = alertaDeviceRepository.findAll();
        assertThat(alertaDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProcessadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertaDeviceRepository.findAll().size();
        // set the field null
        alertaDevice.setProcessado(null);

        // Create the AlertaDevice, which fails.

        restAlertaDeviceMockMvc.perform(post("/api/alerta-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertaDevice)))
            .andExpect(status().isBadRequest());

        List<AlertaDevice> alertaDeviceList = alertaDeviceRepository.findAll();
        assertThat(alertaDeviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlertaDevices() throws Exception {
        // Initialize the database
        alertaDeviceRepository.saveAndFlush(alertaDevice);

        // Get all the alertaDeviceList
        restAlertaDeviceMockMvc.perform(get("/api/alerta-devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertaDevice.getId().intValue())))
            .andExpect(jsonPath("$.[*].data_hora").value(hasItem(DEFAULT_DATA_HORA.toString())))
            .andExpect(jsonPath("$.[*].processado").value(hasItem(DEFAULT_PROCESSADO)));
    }

    @Test
    @Transactional
    public void getAlertaDevice() throws Exception {
        // Initialize the database
        alertaDeviceRepository.saveAndFlush(alertaDevice);

        // Get the alertaDevice
        restAlertaDeviceMockMvc.perform(get("/api/alerta-devices/{id}", alertaDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alertaDevice.getId().intValue()))
            .andExpect(jsonPath("$.data_hora").value(DEFAULT_DATA_HORA.toString()))
            .andExpect(jsonPath("$.processado").value(DEFAULT_PROCESSADO));
    }

    @Test
    @Transactional
    public void getNonExistingAlertaDevice() throws Exception {
        // Get the alertaDevice
        restAlertaDeviceMockMvc.perform(get("/api/alerta-devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlertaDevice() throws Exception {
        // Initialize the database
        alertaDeviceRepository.saveAndFlush(alertaDevice);
        int databaseSizeBeforeUpdate = alertaDeviceRepository.findAll().size();

        // Update the alertaDevice
        AlertaDevice updatedAlertaDevice = alertaDeviceRepository.findOne(alertaDevice.getId());
        updatedAlertaDevice
            .data_hora(UPDATED_DATA_HORA)
            .processado(UPDATED_PROCESSADO);

        restAlertaDeviceMockMvc.perform(put("/api/alerta-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlertaDevice)))
            .andExpect(status().isOk());

        // Validate the AlertaDevice in the database
        List<AlertaDevice> alertaDeviceList = alertaDeviceRepository.findAll();
        assertThat(alertaDeviceList).hasSize(databaseSizeBeforeUpdate);
        AlertaDevice testAlertaDevice = alertaDeviceList.get(alertaDeviceList.size() - 1);
        assertThat(testAlertaDevice.getData_hora()).isEqualTo(UPDATED_DATA_HORA);
        assertThat(testAlertaDevice.getProcessado()).isEqualTo(UPDATED_PROCESSADO);
    }

    @Test
    @Transactional
    public void updateNonExistingAlertaDevice() throws Exception {
        int databaseSizeBeforeUpdate = alertaDeviceRepository.findAll().size();

        // Create the AlertaDevice

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlertaDeviceMockMvc.perform(put("/api/alerta-devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alertaDevice)))
            .andExpect(status().isCreated());

        // Validate the AlertaDevice in the database
        List<AlertaDevice> alertaDeviceList = alertaDeviceRepository.findAll();
        assertThat(alertaDeviceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAlertaDevice() throws Exception {
        // Initialize the database
        alertaDeviceRepository.saveAndFlush(alertaDevice);
        int databaseSizeBeforeDelete = alertaDeviceRepository.findAll().size();

        // Get the alertaDevice
        restAlertaDeviceMockMvc.perform(delete("/api/alerta-devices/{id}", alertaDevice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AlertaDevice> alertaDeviceList = alertaDeviceRepository.findAll();
        assertThat(alertaDeviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertaDevice.class);
    }
}
