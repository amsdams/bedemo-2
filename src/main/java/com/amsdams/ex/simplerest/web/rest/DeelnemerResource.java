package com.amsdams.ex.simplerest.web.rest;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
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

import com.amsdams.ex.simplerest.service.DeelnemerService;
import com.amsdams.ex.simplerest.service.PensioenRegelingService;
import com.amsdams.ex.simplerest.service.dto.DeelnemerDTO;
import com.amsdams.ex.simplerest.service.dto.PensioenRegelingDTO;
import com.amsdams.ex.simplerest.service.impl.JaarlijksePremieStortingBerekeningService;
import com.amsdams.ex.simplerest.service.impl.VerwachteWaardePensioenBerekeningService;
import com.amsdams.ex.simplerest.web.rest.errors.BadRequestAlertException;
import com.amsdams.ex.simplerest.web.rest.util.HeaderUtil;
import com.amsdams.ex.simplerest.web.rest.util.ResponseUtil;

/**
 * REST controller for managing {@link com.amsdams.befrank.domain.Deelnemer}.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class DeelnemerResource {

	private final Logger log = LoggerFactory.getLogger(DeelnemerResource.class);

	private static final String ENTITY_NAME = "deelnemer";

	@Value("${my.clientApp.name:default}")
	private String applicationName;

	private final DeelnemerService deelnemerService;

	private final PensioenRegelingService pensioenRegelingService;

	private final JaarlijksePremieStortingBerekeningService jaarlijksePremieStortingBerekeningService;

	private final VerwachteWaardePensioenBerekeningService verwachteWaardePensioenBerekeningService;

	public DeelnemerResource(DeelnemerService deelnemerService,
			JaarlijksePremieStortingBerekeningService jaarlijksePremieStortingBerekeningService,
			VerwachteWaardePensioenBerekeningService verwachteWaardePensioenBerekeningService,
			PensioenRegelingService pensioenRegelingService) {
		this.deelnemerService = deelnemerService;
		this.jaarlijksePremieStortingBerekeningService = jaarlijksePremieStortingBerekeningService;
		this.verwachteWaardePensioenBerekeningService = verwachteWaardePensioenBerekeningService;
		this.pensioenRegelingService = pensioenRegelingService;
	}

	/**
	 * {@code POST  /deelnemers} : Create a new deelnemer.
	 *
	 * @param deelnemerDTO the deelnemerDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new deelnemerDTO, or with status {@code 400 (Bad Request)}
	 *         if the deelnemer has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	/*@PostMapping("/deelnemers")
	public ResponseEntity<DeelnemerDTO> createDeelnemer(@Valid @RequestBody DeelnemerDTO deelnemerDTO)
			throws URISyntaxException {
		log.debug("REST request to save Deelnemer : {}", deelnemerDTO);
		if (deelnemerDTO.getId() != null) {
			throw new BadRequestAlertException("A new deelnemer cannot already have an ID", ENTITY_NAME, "idexists");
		}

		if (deelnemerDTO.getPensioenRegelingId() != null) {

			Optional<PensioenRegelingDTO> optionalPensioenRegelingDTO = pensioenRegelingService
					.findOne(deelnemerDTO.getPensioenRegelingId());
			if (optionalPensioenRegelingDTO.isPresent()) {

				BigDecimal jaarlijksePremieStortingResultaat = jaarlijksePremieStortingBerekeningService
						.getJaarlijksePremieStorting(deelnemerDTO, optionalPensioenRegelingDTO.get());
				deelnemerDTO.setJaarlijksePremieStortingResultaat(jaarlijksePremieStortingResultaat);

				BigDecimal verwachteWaardePensioenResultaat = verwachteWaardePensioenBerekeningService
						.getVerwachteWaardePensioen(deelnemerDTO, optionalPensioenRegelingDTO.get());
				deelnemerDTO.setVerwachteWaardePensioenResultaat(verwachteWaardePensioenResultaat);
			}
		}

		DeelnemerDTO result = deelnemerService.save(deelnemerDTO);
		return ResponseEntity.created(new URI("/api/deelnemers/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}
*/
	/**
	 * {@code PUT  /deelnemers} : Updates an existing deelnemer.
	 *
	 * @param deelnemerDTO the deelnemerDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated deelnemerDTO, or with status {@code 400 (Bad Request)} if
	 *         the deelnemerDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the deelnemerDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/deelnemers")
	public ResponseEntity<DeelnemerDTO> updateDeelnemer(@Valid @RequestBody DeelnemerDTO deelnemerDTO)
			throws URISyntaxException {
		log.debug("REST request to update Deelnemer : {}", deelnemerDTO);
		if (deelnemerDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

		if (deelnemerDTO.getPensioenRegelingId() != null) {

			Optional<PensioenRegelingDTO> optionalPensioenRegelingDTO = pensioenRegelingService
					.findOne(deelnemerDTO.getPensioenRegelingId());
			if (optionalPensioenRegelingDTO.isPresent()) {

		
				
				BigDecimal deelnemerVoltijdsalaris = deelnemerDTO.getVoltijdsalaris();
				BigDecimal pensioenRegelingVoltijdFranchise = optionalPensioenRegelingDTO.get().getVoltijdFranchise();
				BigDecimal deelnemerDeeltijdPercentage = deelnemerDTO.getDeeltijdPercentage();
				BigDecimal pensioenRegelingPremiePercentage = optionalPensioenRegelingDTO.get().getPremiePercentage();
				
				BigDecimal jaarlijksePremieStortingResultaat = jaarlijksePremieStortingBerekeningService.getJaarlijksePremieStorting(deelnemerVoltijdsalaris, pensioenRegelingVoltijdFranchise, deelnemerDeeltijdPercentage, pensioenRegelingPremiePercentage);
				deelnemerDTO.setJaarlijksePremieStortingResultaat(jaarlijksePremieStortingResultaat);

				LocalDate deelnemerStartdatumDienst = deelnemerDTO.getStartdatumDienst();
				LocalDate deelnemerEinddatumDienst = deelnemerDTO.getEinddatumDienst();
				BigDecimal deelnemerHuidigeWaardeBeleggingen = deelnemerDTO.getHuidigeWaardeBeleggingen();
				BigDecimal deelnemerJaarlijksePremieStortingResultaat  = deelnemerDTO.getJaarlijksePremieStortingResultaat();
				BigDecimal pensioenRegelingJaarlijksRendementBeleggingen = optionalPensioenRegelingDTO.get().getJaarlijksRendementBeleggingen();
				
				BigDecimal verwachteWaardePensioenResultaat = verwachteWaardePensioenBerekeningService.getVerwachteWaardePensioen(deelnemerStartdatumDienst, deelnemerEinddatumDienst, deelnemerHuidigeWaardeBeleggingen, deelnemerJaarlijksePremieStortingResultaat, pensioenRegelingJaarlijksRendementBeleggingen);

				deelnemerDTO.setVerwachteWaardePensioenResultaat(verwachteWaardePensioenResultaat);
			}
		}

		DeelnemerDTO result = deelnemerService.save(deelnemerDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, ENTITY_NAME, deelnemerDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /deelnemers} : get all the deelnemers.
	 *
	 * 
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of deelnemers in body.
	 */
	@GetMapping("/deelnemers")
	public List<DeelnemerDTO> getAllDeelnemers() {
		log.debug("REST request to get all Deelnemers");
		return deelnemerService.findAll();
	}

	/**
	 * {@code GET  /deelnemers/:id} : get the "id" deelnemer.
	 *
	 * @param id the id of the deelnemerDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the deelnemerDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/deelnemers/{id}")
	public ResponseEntity<DeelnemerDTO> getDeelnemer(@PathVariable Long id) {
		log.debug("REST request to get Deelnemer : {}", id);
		Optional<DeelnemerDTO> deelnemerDTO = deelnemerService.findOne(id);
		return ResponseUtil.wrapOrNotFound(deelnemerDTO);
	}

	/**
	 * {@code DELETE  /deelnemers/:id} : delete the "id" deelnemer.
	 *
	 * @param id the id of the deelnemerDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	/*@DeleteMapping("/deelnemers/{id}")
	public ResponseEntity<Void> deleteDeelnemer(@PathVariable Long id) {
		log.debug("REST request to delete Deelnemer : {}", id);
		deelnemerService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, ENTITY_NAME, id.toString())).build();
	}
	*/
}
