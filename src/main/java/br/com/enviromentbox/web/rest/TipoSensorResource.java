package br.com.enviromentbox.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.enviromentbox.domain.TipoSensor;

import br.com.enviromentbox.repository.TipoSensorRepository;
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
 * REST controller for managing TipoSensor.
 */
@RestController
@RequestMapping("/api")
public class TipoSensorResource {

    private final Logger log = LoggerFactory.getLogger(TipoSensorResource.class);

    private static final String ENTITY_NAME = "tipoSensor";
        
    private final TipoSensorRepository tipoSensorRepository;

    public TipoSensorResource(TipoSensorRepository tipoSensorRepository) {
        this.tipoSensorRepository = tipoSensorRepository;
    }

    /**
     * POST  /tipo-sensors : Create a new tipoSensor.
     *
     * @param tipoSensor the tipoSensor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoSensor, or with status 400 (Bad Request) if the tipoSensor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-sensors")
    @Timed
    public ResponseEntity<TipoSensor> createTipoSensor(@Valid @RequestBody TipoSensor tipoSensor) throws URISyntaxException {
        log.debug("REST request to save TipoSensor : {}", tipoSensor);
        if (tipoSensor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tipoSensor cannot already have an ID")).body(null);
        }
        TipoSensor result = tipoSensorRepository.save(tipoSensor);
        return ResponseEntity.created(new URI("/api/tipo-sensors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-sensors : Updates an existing tipoSensor.
     *
     * @param tipoSensor the tipoSensor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoSensor,
     * or with status 400 (Bad Request) if the tipoSensor is not valid,
     * or with status 500 (Internal Server Error) if the tipoSensor couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-sensors")
    @Timed
    public ResponseEntity<TipoSensor> updateTipoSensor(@Valid @RequestBody TipoSensor tipoSensor) throws URISyntaxException {
        log.debug("REST request to update TipoSensor : {}", tipoSensor);
        if (tipoSensor.getId() == null) {
            return createTipoSensor(tipoSensor);
        }
        TipoSensor result = tipoSensorRepository.save(tipoSensor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoSensor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-sensors : get all the tipoSensors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoSensors in body
     */
    @GetMapping("/tipo-sensors")
    @Timed
    public List<TipoSensor> getAllTipoSensors() {
        log.debug("REST request to get all TipoSensors");
        List<TipoSensor> tipoSensors = tipoSensorRepository.findAll();
        return tipoSensors;
    }

    /**
     * GET  /tipo-sensors/:id : get the "id" tipoSensor.
     *
     * @param id the id of the tipoSensor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoSensor, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-sensors/{id}")
    @Timed
    public ResponseEntity<TipoSensor> getTipoSensor(@PathVariable Long id) {
        log.debug("REST request to get TipoSensor : {}", id);
        TipoSensor tipoSensor = tipoSensorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoSensor));
    }

    /**
     * DELETE  /tipo-sensors/:id : delete the "id" tipoSensor.
     *
     * @param id the id of the tipoSensor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-sensors/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoSensor(@PathVariable Long id) {
        log.debug("REST request to delete TipoSensor : {}", id);
        tipoSensorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
