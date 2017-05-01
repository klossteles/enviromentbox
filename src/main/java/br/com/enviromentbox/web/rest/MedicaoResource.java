package br.com.enviromentbox.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.enviromentbox.domain.Medicao;

import br.com.enviromentbox.repository.MedicaoRepository;
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
 * REST controller for managing Medicao.
 */
@RestController
@RequestMapping("/api")
public class MedicaoResource {

    private final Logger log = LoggerFactory.getLogger(MedicaoResource.class);

    private static final String ENTITY_NAME = "medicao";
        
    private final MedicaoRepository medicaoRepository;

    public MedicaoResource(MedicaoRepository medicaoRepository) {
        this.medicaoRepository = medicaoRepository;
    }

    /**
     * POST  /medicaos : Create a new medicao.
     *
     * @param medicao the medicao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicao, or with status 400 (Bad Request) if the medicao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medicaos")
    @Timed
    public ResponseEntity<Medicao> createMedicao(@Valid @RequestBody Medicao medicao) throws URISyntaxException {
        log.debug("REST request to save Medicao : {}", medicao);
        if (medicao.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new medicao cannot already have an ID")).body(null);
        }
        Medicao result = medicaoRepository.save(medicao);
        return ResponseEntity.created(new URI("/api/medicaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medicaos : Updates an existing medicao.
     *
     * @param medicao the medicao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicao,
     * or with status 400 (Bad Request) if the medicao is not valid,
     * or with status 500 (Internal Server Error) if the medicao couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medicaos")
    @Timed
    public ResponseEntity<Medicao> updateMedicao(@Valid @RequestBody Medicao medicao) throws URISyntaxException {
        log.debug("REST request to update Medicao : {}", medicao);
        if (medicao.getId() == null) {
            return createMedicao(medicao);
        }
        Medicao result = medicaoRepository.save(medicao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medicaos : get all the medicaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of medicaos in body
     */
    @GetMapping("/medicaos")
    @Timed
    public List<Medicao> getAllMedicaos() {
        log.debug("REST request to get all Medicaos");
        List<Medicao> medicaos = medicaoRepository.findAll();
        return medicaos;
    }

    /**
     * GET  /medicaos/:id : get the "id" medicao.
     *
     * @param id the id of the medicao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicao, or with status 404 (Not Found)
     */
    @GetMapping("/medicaos/{id}")
    @Timed
    public ResponseEntity<Medicao> getMedicao(@PathVariable Long id) {
        log.debug("REST request to get Medicao : {}", id);
        Medicao medicao = medicaoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medicao));
    }

    /**
     * DELETE  /medicaos/:id : delete the "id" medicao.
     *
     * @param id the id of the medicao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medicaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicao(@PathVariable Long id) {
        log.debug("REST request to delete Medicao : {}", id);
        medicaoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
