package br.com.enviromentbox.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.enviromentbox.domain.AlertaDevice;

import br.com.enviromentbox.repository.AlertaDeviceRepository;
import br.com.enviromentbox.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AlertaDevice.
 */
@RestController
@RequestMapping("/api")
public class AlertaDeviceResource {

    private final Logger log = LoggerFactory.getLogger(AlertaDeviceResource.class);

    private static final String ENTITY_NAME = "alertaDevice";
        
    private final AlertaDeviceRepository alertaDeviceRepository;

    public AlertaDeviceResource(AlertaDeviceRepository alertaDeviceRepository) {
        this.alertaDeviceRepository = alertaDeviceRepository;
    }

    /**
     * POST  /alerta-devices : Create a new alertaDevice.
     *
     * @param alertaDevice the alertaDevice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alertaDevice, or with status 400 (Bad Request) if the alertaDevice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alerta-devices")
    @Timed
    public ResponseEntity<AlertaDevice> createAlertaDevice(@Valid @RequestBody AlertaDevice alertaDevice) throws URISyntaxException {
        log.debug("REST request to save AlertaDevice : {}", alertaDevice);
        if (alertaDevice.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new alertaDevice cannot already have an ID")).body(null);
        }
        AlertaDevice result = alertaDeviceRepository.save(alertaDevice);
        return ResponseEntity.created(new URI("/api/alerta-devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alerta-devices : Updates an existing alertaDevice.
     *
     * @param alertaDevice the alertaDevice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alertaDevice,
     * or with status 400 (Bad Request) if the alertaDevice is not valid,
     * or with status 500 (Internal Server Error) if the alertaDevice couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alerta-devices")
    @Timed
    public ResponseEntity<AlertaDevice> updateAlertaDevice(@Valid @RequestBody AlertaDevice alertaDevice) throws URISyntaxException {
        log.debug("REST request to update AlertaDevice : {}", alertaDevice);
        if (alertaDevice.getId() == null) {
            return createAlertaDevice(alertaDevice);
        }
        AlertaDevice result = alertaDeviceRepository.save(alertaDevice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, alertaDevice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alerta-devices : get all the alertaDevices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alertaDevices in body
     */
    @GetMapping("/alerta-devices")
    @Timed
    public List<AlertaDevice> getAllAlertaDevices() {
        log.debug("REST request to get all AlertaDevices");
        List<AlertaDevice> alertaDevices = alertaDeviceRepository.findAll();
        return alertaDevices;
    }

    /**
     * GET  /alerta-devices/:id : get the "id" alertaDevice.
     *
     * @param id the id of the alertaDevice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alertaDevice, or with status 404 (Not Found)
     */
    @GetMapping("/alerta-devices/{id}")
    @Timed
    public ResponseEntity<AlertaDevice> getAlertaDevice(@PathVariable Long id) {
        log.debug("REST request to get AlertaDevice : {}", id);
        AlertaDevice alertaDevice = alertaDeviceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(alertaDevice));
    }

    /**
     * DELETE  /alerta-devices/:id : delete the "id" alertaDevice.
     *
     * @param id the id of the alertaDevice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alerta-devices/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlertaDevice(@PathVariable Long id) {
        log.debug("REST request to delete AlertaDevice : {}", id);
        alertaDeviceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
