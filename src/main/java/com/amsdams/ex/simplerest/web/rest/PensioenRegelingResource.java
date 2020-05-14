package com.amsdams.ex.simplerest.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amsdams.ex.simplerest.service.PensioenRegelingService;
import com.amsdams.ex.simplerest.service.dto.PensioenRegelingDTO;
import com.amsdams.ex.simplerest.web.rest.errors.BadRequestAlertException;
import com.amsdams.ex.simplerest.web.rest.util.HeaderUtil;
import com.amsdams.ex.simplerest.web.rest.util.ResponseUtil;

/**
 * REST controller for managing {@link com.amsdams.ex.simplerest.domain.PensioenRegeling}.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class PensioenRegelingResource {

    private final Logger log = LoggerFactory.getLogger(PensioenRegelingResource.class);

    private static final String ENTITY_NAME = "pensioenRegeling";

    @Value("${my.clientApp.name:default}")
    private String applicationName;

    private final PensioenRegelingService pensioenRegelingService;

    public PensioenRegelingResource(PensioenRegelingService pensioenRegelingService) {
        this.pensioenRegelingService = pensioenRegelingService;
    }


    /**
     * {@code PUT  /pensioen-regelings} : Updates an existing pensioenRegeling.
     *
     * @param pensioenRegelingDTO the pensioenRegelingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pensioenRegelingDTO,
     * or with status {@code 400 (Bad Request)} if the pensioenRegelingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pensioenRegelingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pensioen-regelings")
    public ResponseEntity<PensioenRegelingDTO> updatePensioenRegeling(@Valid @RequestBody PensioenRegelingDTO pensioenRegelingDTO) throws URISyntaxException {
        log.debug("REST request to update PensioenRegeling : {}", pensioenRegelingDTO);
        if (pensioenRegelingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PensioenRegelingDTO result = pensioenRegelingService.save(pensioenRegelingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, ENTITY_NAME, pensioenRegelingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pensioen-regelings} : get all the pensioenRegelings.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pensioenRegelings in body.
     */
    @GetMapping("/pensioen-regelings")
    public List<PensioenRegelingDTO> getAllPensioenRegelings() {
        log.debug("REST request to get all PensioenRegelings");
        return pensioenRegelingService.findAll();
    }

    /**
     * {@code GET  /pensioen-regelings/:id} : get the "id" pensioenRegeling.
     *
     * @param id the id of the pensioenRegelingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pensioenRegelingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pensioen-regelings/{id}")
    public ResponseEntity<PensioenRegelingDTO> getPensioenRegeling(@PathVariable Long id) {
        log.debug("REST request to get PensioenRegeling : {}", id);
        Optional<PensioenRegelingDTO> pensioenRegelingDTO = pensioenRegelingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pensioenRegelingDTO);
    }

    
}
