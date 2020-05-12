package com.amsdams.ex.simplerest.web.rest;

//import static com.amsdams.ex.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import com.amsdams.ex.simplerest.Application;
import com.amsdams.ex.simplerest.domain.Deelnemer;
import com.amsdams.ex.simplerest.domain.PensioenRegeling;
import com.amsdams.ex.simplerest.repository.DeelnemerRepository;
import com.amsdams.ex.simplerest.repository.PensioenRegelingRepository;
import com.amsdams.ex.simplerest.service.DeelnemerService;
import com.amsdams.ex.simplerest.service.PensioenRegelingService;
import com.amsdams.ex.simplerest.service.dto.DeelnemerDTO;
import com.amsdams.ex.simplerest.service.impl.JaarlijksePremieStortingBerekeningService;
import com.amsdams.ex.simplerest.service.impl.VerwachteWaardePensioenBerekeningService;
import com.amsdams.ex.simplerest.service.mapper.DeelnemerMapper;
import com.amsdams.ex.simplerest.web.rest.errors.ExceptionTranslator;

/**
 * Integration tests for the {@link DeelnemerResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class DeelnemerResourceIT {

	private static final String DEFAULT_NAAM = "AAAAAAAAAA";
	private static final String UPDATED_NAAM = "BBBBBBBBBB";

	private static final String DEFAULT_ADRES = "AAAAAAAAAA";
	private static final String UPDATED_ADRES = "BBBBBBBBBB";

	private static final String DEFAULT_WOONPLAATS = "AAAAAAAAAA";
	private static final String UPDATED_WOONPLAATS = "BBBBBBBBBB";

	private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
	private static final String UPDATED_EMAIL = "BBBBBBBBBB";

	private static final LocalDate DEFAULT_GEBOORTEDATUM = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_GEBOORTEDATUM = LocalDate.now(ZoneId.systemDefault());

	private static final LocalDate DEFAULT_EINDDATUM_DIENST = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_EINDDATUM_DIENST = LocalDate.now(ZoneId.systemDefault());

	private static final LocalDate DEFAULT_STARTDATUM_DIENST = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_STARTDATUM_DIENST = LocalDate.now(ZoneId.systemDefault());

	private static final BigDecimal DEFAULT_VOLTIJDSALARIS = new BigDecimal(0);
	private static final BigDecimal UPDATED_VOLTIJDSALARIS = new BigDecimal(1);

	private static final BigDecimal DEFAULT_HUIDIGE_WAARDE_BELEGGINGEN = new BigDecimal(0);
	private static final BigDecimal UPDATED_HUIDIGE_WAARDE_BELEGGINGEN = new BigDecimal(1);

	private static final String DEFAULT_REKENING = "AAAAAAAAAA";
	private static final String UPDATED_REKENING = "BBBBBBBBBB";
	private static final BigDecimal DEFAULT_DEELTIJDPERCENTAGE = new BigDecimal(0);
	private static final BigDecimal UPDATED_DEELTIJDPERCENTAGE = new BigDecimal(1);
	@Autowired
	private DeelnemerRepository deelnemerRepository;

	@Autowired
	private PensioenRegelingRepository pensioenRegelingRepository;

	@Autowired
	private DeelnemerMapper deelnemerMapper;

	@Autowired
	private DeelnemerService deelnemerService;

	@Autowired
	private PensioenRegelingService pensioenRegelingService;

	@Autowired
	private JaarlijksePremieStortingBerekeningService jaarlijksePremieStortingBerekeningService;

	@Autowired
	private VerwachteWaardePensioenBerekeningService verwachteWaardePensioenBerekeningService;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private EntityManager em;

	@Autowired
	private Validator validator;

	private MockMvc restDeelnemerMockMvc;

	private Deelnemer deelnemer;
	private Deelnemer deelnemerScenario1;

	private PensioenRegeling pensioenRegelingScenario1;

	
	private Deelnemer deelnemerScenario2;

	private PensioenRegeling pensioenRegelingScenario2;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		final DeelnemerResource deelnemerResource = new DeelnemerResource(deelnemerService,
				jaarlijksePremieStortingBerekeningService, verwachteWaardePensioenBerekeningService,
				pensioenRegelingService);
		this.restDeelnemerMockMvc = MockMvcBuilders.standaloneSetup(deelnemerResource)
				.setCustomArgumentResolvers(pageableArgumentResolver)
				.setControllerAdvice(exceptionTranslator)
				// .setConversionService(createFormattingConversionService())
				.setMessageConverters(jacksonMessageConverter)
				.setValidator(validator).build();
	}

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Deelnemer createEntity(EntityManager em) {
		Deelnemer deelnemer = Deelnemer.builder()
				.naam(DEFAULT_NAAM)
				.adres(DEFAULT_ADRES)
				.woonplaats(DEFAULT_WOONPLAATS)
				.email(DEFAULT_EMAIL)
				.geboortedatum(DEFAULT_GEBOORTEDATUM)
				.einddatumDienst(DEFAULT_EINDDATUM_DIENST)
				.startdatumDienst(DEFAULT_STARTDATUM_DIENST)
				.voltijdsalaris(DEFAULT_VOLTIJDSALARIS)
				.huidigeWaardeBeleggingen(DEFAULT_HUIDIGE_WAARDE_BELEGGINGEN)
				.rekening(DEFAULT_REKENING)
				.deeltijdPercentage(DEFAULT_DEELTIJDPERCENTAGE).build();
		return deelnemer;
	}

	
	@BeforeEach
	public void initTest() {
		deelnemer = createEntity(em);
		deelnemerScenario1 = createDeelnemerScenario1(em);
		pensioenRegelingScenario1 = createPensioenRegelingScenario1(em);
		deelnemerScenario2 = createDeelnemerScenario2(em);
		pensioenRegelingScenario2 = createPensioenRegelingScenario2(em);
	}

	private PensioenRegeling createPensioenRegelingScenario1(EntityManager em2) {
		PensioenRegeling pensioenRegeling = PensioenRegeling.builder()
				.jaarlijksRendementBeleggingen(new BigDecimal("3"))
				.naam("naam")
				.premiePercentage(new BigDecimal("1.657"))
				.voltijdFranchise(new BigDecimal("13785")).build();
		return pensioenRegeling;
	}

	private Deelnemer createDeelnemerScenario1(EntityManager em2) {
		// @formatter:off
		Deelnemer deelnemer = Deelnemer.builder()
				.naam(UPDATED_NAAM)
				.adres(UPDATED_ADRES)
				.woonplaats(UPDATED_WOONPLAATS)
				.email(UPDATED_EMAIL)
				.geboortedatum(LocalDate.parse("1980-01-01"))
				.einddatumDienst(LocalDate.parse("2048-01-01"))
				.startdatumDienst(LocalDate.parse("2000-01-01"))
				.voltijdsalaris(new BigDecimal("40000"))
				.huidigeWaardeBeleggingen(new BigDecimal("100"))
				.rekening(UPDATED_REKENING)
				.deeltijdPercentage(new BigDecimal("100")).build();
		// @formatter:on
		return deelnemer;
	}

	private PensioenRegeling createPensioenRegelingScenario2(EntityManager em2) {
		PensioenRegeling pensioenRegeling = PensioenRegeling.builder()
				.jaarlijksRendementBeleggingen(new BigDecimal("3"))
				.naam("naam")
				.premiePercentage(new BigDecimal("5"))
				.voltijdFranchise(new BigDecimal("15599")).build();
 


		return pensioenRegeling;
	}

	private Deelnemer createDeelnemerScenario2(EntityManager em2) {

		Deelnemer deelnemer = Deelnemer.builder()
				.naam(UPDATED_NAAM)
				.adres(UPDATED_ADRES)
				.woonplaats(UPDATED_WOONPLAATS)
				.email(UPDATED_EMAIL)
				.geboortedatum(LocalDate.parse("1980-01-01"))
				.einddatumDienst(LocalDate.parse("2048-01-01"))
				.startdatumDienst(LocalDate.parse("2000-01-01"))
				.voltijdsalaris(new BigDecimal("40000"))
				.huidigeWaardeBeleggingen(new BigDecimal("100"))
				.rekening(UPDATED_REKENING)
				.deeltijdPercentage(new BigDecimal("100")).build();

		return deelnemer;
	}
	
	@Test
	@Transactional
	public void checkNaamIsRequired() throws Exception {
		int databaseSizeBeforeTest = deelnemerRepository.findAll().size();
		// set the field null
		deelnemer.setNaam(null);

		// Create the Deelnemer, which fails.
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(deelnemer);

		restDeelnemerMockMvc.perform(put("/api/deelnemers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO)))
				.andExpect(status().isBadRequest());

		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkAdresIsRequired() throws Exception {
		int databaseSizeBeforeTest = deelnemerRepository.findAll().size();
		// set the field null
		deelnemer.setAdres(null);

		// Create the Deelnemer, which fails.
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(deelnemer);

		restDeelnemerMockMvc.perform(put("/api/deelnemers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO)))
				.andExpect(status().isBadRequest());

		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkWoonplaatsIsRequired() throws Exception {
		int databaseSizeBeforeTest = deelnemerRepository.findAll().size();
		// set the field null
		deelnemer.setWoonplaats(null);

		// Create the Deelnemer, which fails.
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(deelnemer);

		restDeelnemerMockMvc.perform(put("/api/deelnemers").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO))).andExpect(status().isBadRequest());

		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkEmailIsRequired() throws Exception {
		int databaseSizeBeforeTest = deelnemerRepository.findAll().size();
		// set the field null
		deelnemer.setEmail(null);

		// Create the Deelnemer, which fails.
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(deelnemer);

		restDeelnemerMockMvc.perform(put("/api/deelnemers").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO))).andExpect(status().isBadRequest());

		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkGeboortedatumIsRequired() throws Exception {
		int databaseSizeBeforeTest = deelnemerRepository.findAll().size();
		// set the field null
		deelnemer.setGeboortedatum(null);

		// Create the Deelnemer, which fails.
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(deelnemer);

		restDeelnemerMockMvc.perform(put("/api/deelnemers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO)))
				.andExpect(status().isBadRequest());

		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkEinddatumDienstIsRequired() throws Exception {
		int databaseSizeBeforeTest = deelnemerRepository.findAll().size();
		// set the field null
		deelnemer.setEinddatumDienst(null);

		// Create the Deelnemer, which fails.
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(deelnemer);

		restDeelnemerMockMvc.perform(put("/api/deelnemers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO)))
				.andExpect(status().isBadRequest());

		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkStartdatumDienstIsRequired() throws Exception {
		int databaseSizeBeforeTest = deelnemerRepository.findAll().size();
		// set the field null
		deelnemer.setStartdatumDienst(null);

		// Create the Deelnemer, which fails.
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(deelnemer);

		restDeelnemerMockMvc.perform(put("/api/deelnemers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO)))
				.andExpect(status().isBadRequest());

		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkVoltijdsalarisIsRequired() throws Exception {
		int databaseSizeBeforeTest = deelnemerRepository.findAll().size();
		// set the field null
		deelnemer.setVoltijdsalaris(null);

		// Create the Deelnemer, which fails.
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(deelnemer);

		restDeelnemerMockMvc.perform(put("/api/deelnemers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO)))
				.andExpect(status().isBadRequest());

		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkHuidigeWaardeBeleggingenIsRequired() throws Exception {
		int databaseSizeBeforeTest = deelnemerRepository.findAll().size();
		// set the field null
		deelnemer.setHuidigeWaardeBeleggingen(null);

		// Create the Deelnemer, which fails.
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(deelnemer);

		restDeelnemerMockMvc.perform(put("/api/deelnemers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO)))
				.andExpect(status().isBadRequest());

		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkRekeningIsRequired() throws Exception {
		int databaseSizeBeforeTest = deelnemerRepository.findAll().size();
		// set the field null
		deelnemer.setRekening(null);

		// Create the Deelnemer, which fails.
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(deelnemer);

		restDeelnemerMockMvc.perform(put("/api/deelnemers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO)))
		.andExpect(status().isBadRequest());

		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllDeelnemers() throws Exception {
		// Initialize the database
		deelnemerRepository.saveAndFlush(deelnemer);

		// Get all the deelnemerList
		restDeelnemerMockMvc.perform(get("/api/deelnemers?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[*].id").value(hasItem(deelnemer.getId().intValue())))
				.andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
				.andExpect(jsonPath("$.[*].adres").value(hasItem(DEFAULT_ADRES)))
				.andExpect(jsonPath("$.[*].woonplaats").value(hasItem(DEFAULT_WOONPLAATS)))
				.andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
				.andExpect(jsonPath("$.[*].geboortedatum").value(hasItem(DEFAULT_GEBOORTEDATUM.toString())))
				.andExpect(jsonPath("$.[*].einddatumDienst").value(hasItem(DEFAULT_EINDDATUM_DIENST.toString())))
				.andExpect(jsonPath("$.[*].startdatumDienst").value(hasItem(DEFAULT_STARTDATUM_DIENST.toString())))
				.andExpect(jsonPath("$.[*].voltijdsalaris").value(hasItem(DEFAULT_VOLTIJDSALARIS.intValue())))
				.andExpect(jsonPath("$.[*].huidigeWaardeBeleggingen").value(hasItem(DEFAULT_HUIDIGE_WAARDE_BELEGGINGEN.intValue())))
				.andExpect(jsonPath("$.[*].rekening").value(hasItem(DEFAULT_REKENING)))
				.andExpect(jsonPath("$.[*].deeltijdPercentage").value(hasItem(DEFAULT_DEELTIJDPERCENTAGE.intValue())));
	}

	@Test
	@Transactional
	public void getDeelnemer() throws Exception {
		// Initialize the database
		deelnemerRepository.saveAndFlush(deelnemer);

		// Get the deelnemer
		restDeelnemerMockMvc.perform(get("/api/deelnemers/{id}", deelnemer.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(deelnemer.getId().intValue()))
				.andExpect(jsonPath("$.naam").value(DEFAULT_NAAM)).andExpect(jsonPath("$.adres").value(DEFAULT_ADRES))
				.andExpect(jsonPath("$.woonplaats").value(DEFAULT_WOONPLAATS))
				.andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
				.andExpect(jsonPath("$.geboortedatum").value(DEFAULT_GEBOORTEDATUM.toString()))
				.andExpect(jsonPath("$.einddatumDienst").value(DEFAULT_EINDDATUM_DIENST.toString()))
				.andExpect(jsonPath("$.startdatumDienst").value(DEFAULT_STARTDATUM_DIENST.toString()))
				.andExpect(jsonPath("$.voltijdsalaris").value(DEFAULT_VOLTIJDSALARIS.intValue()))
				.andExpect(jsonPath("$.huidigeWaardeBeleggingen").value(DEFAULT_HUIDIGE_WAARDE_BELEGGINGEN.intValue()))
				.andExpect(jsonPath("$.rekening").value(DEFAULT_REKENING))
				.andExpect(jsonPath("$.deeltijdPercentage").value(DEFAULT_DEELTIJDPERCENTAGE));

	}

	@Test
	@Transactional
	public void getNonExistingDeelnemer() throws Exception {
		// Get the deelnemer
		restDeelnemerMockMvc.perform(get("/api/deelnemers/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateDeelnemer() throws Exception {
		// Initialize the database
		deelnemerRepository.saveAndFlush(deelnemer);

		int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();

		// Update the deelnemer
		Deelnemer updatedDeelnemer = deelnemerRepository.findById(deelnemer.getId()).get();
		// Disconnect from session so that the updates on updatedDeelnemer are not
		// directly saved in db
		em.detach(updatedDeelnemer);
		updatedDeelnemer = Deelnemer.builder().naam(UPDATED_NAAM).adres(UPDATED_ADRES).woonplaats(UPDATED_WOONPLAATS)
				.email(UPDATED_EMAIL).geboortedatum(UPDATED_GEBOORTEDATUM).einddatumDienst(UPDATED_EINDDATUM_DIENST)
				.startdatumDienst(UPDATED_STARTDATUM_DIENST).voltijdsalaris(UPDATED_VOLTIJDSALARIS)
				.huidigeWaardeBeleggingen(UPDATED_HUIDIGE_WAARDE_BELEGGINGEN).rekening(UPDATED_REKENING)
				.deeltijdPercentage(UPDATED_DEELTIJDPERCENTAGE).id(updatedDeelnemer.getId()).build();
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(updatedDeelnemer);

		restDeelnemerMockMvc.perform(put("/api/deelnemers").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO))).andExpect(status().isOk());

		// Validate the Deelnemer in the database
		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
		Deelnemer testDeelnemer = deelnemerList.get(deelnemerList.size() - 1);
		assertThat(testDeelnemer.getNaam()).isEqualTo(UPDATED_NAAM);
		assertThat(testDeelnemer.getAdres()).isEqualTo(UPDATED_ADRES);
		assertThat(testDeelnemer.getWoonplaats()).isEqualTo(UPDATED_WOONPLAATS);
		assertThat(testDeelnemer.getEmail()).isEqualTo(UPDATED_EMAIL);
		assertThat(testDeelnemer.getGeboortedatum()).isEqualTo(UPDATED_GEBOORTEDATUM);
		assertThat(testDeelnemer.getEinddatumDienst()).isEqualTo(UPDATED_EINDDATUM_DIENST);
		assertThat(testDeelnemer.getStartdatumDienst()).isEqualTo(UPDATED_STARTDATUM_DIENST);
		assertThat(testDeelnemer.getVoltijdsalaris()).isEqualTo(UPDATED_VOLTIJDSALARIS);
		assertThat(testDeelnemer.getHuidigeWaardeBeleggingen()).isEqualTo(UPDATED_HUIDIGE_WAARDE_BELEGGINGEN);
		assertThat(testDeelnemer.getRekening()).isEqualTo(UPDATED_REKENING);
		assertThat(testDeelnemer.getDeeltijdPercentage()).isEqualTo(UPDATED_DEELTIJDPERCENTAGE);
	}

	@Test
	@Transactional
	public void updateNonExistingDeelnemer() throws Exception {
		int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();

		// Create the Deelnemer
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(deelnemer);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restDeelnemerMockMvc.perform(put("/api/deelnemers").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO))).andExpect(status().isBadRequest());

		// Validate the Deelnemer in the database
		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void updateDeelnemerScenario1GeenPensioenRegeling() throws Exception {
		// Initialize the database
		deelnemerRepository.saveAndFlush(deelnemerScenario1);

		int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();

		// Update the deelnemer
		Deelnemer updatedDeelnemer = deelnemerRepository.findById(deelnemerScenario1.getId()).get();
		// Disconnect from session so that the updates on updatedDeelnemer are not
		// directly saved in db
		em.detach(updatedDeelnemer);
		updatedDeelnemer = Deelnemer.builder()
				.naam(UPDATED_NAAM)
				.adres(UPDATED_ADRES)
				.woonplaats(UPDATED_WOONPLAATS)
				.email(UPDATED_EMAIL)
				.geboortedatum(LocalDate.parse("1980-01-01"))
				.einddatumDienst(LocalDate.parse("2048-01-01"))
				.startdatumDienst(LocalDate.parse("2000-01-01"))
				.voltijdsalaris(new BigDecimal("40000"))
				.huidigeWaardeBeleggingen(new BigDecimal("100"))
				.rekening(UPDATED_REKENING)
				.deeltijdPercentage(new BigDecimal("100"))
				.id(updatedDeelnemer.getId())
				.build();
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(updatedDeelnemer);

		restDeelnemerMockMvc.perform(put("/api/deelnemers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO)))
				.andDo(print())
				.andExpect(status().isOk());

		// Validate the Deelnemer in the database
		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
		Deelnemer testDeelnemer = deelnemerList.get(deelnemerList.size() - 1);

		assertThat(testDeelnemer.getVerwachteWaardePensioenResultaat()).isNull();
		assertThat(testDeelnemer.getJaarlijksePremieStortingResultaat()).isNull();
	}

	@Test
	@Transactional
	public void updateDeelnemerScenario1WelPensioenRegeling() throws Exception {
		// Initialize the database

		pensioenRegelingRepository.saveAndFlush(pensioenRegelingScenario1);
		PensioenRegeling updatedPensioenRegeling = pensioenRegelingRepository
				.findById(pensioenRegelingScenario1.getId()).get();

		deelnemerScenario1.setPensioenRegeling(updatedPensioenRegeling);

		deelnemerRepository.saveAndFlush(deelnemerScenario1);

		int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();

		// Update the deelnemer
		Deelnemer updatedDeelnemer = deelnemerRepository.findById(deelnemerScenario1.getId()).get();
		// Disconnect from session so that the updates on updatedDeelnemer are not
		// directly saved in db
		em.detach(updatedDeelnemer);
		updatedDeelnemer = Deelnemer.builder()
				.naam(UPDATED_NAAM)
				.adres(UPDATED_ADRES)
				.woonplaats(UPDATED_WOONPLAATS)
				.email(UPDATED_EMAIL)
				.geboortedatum(LocalDate.parse("1980-01-01"))
				.einddatumDienst(LocalDate.parse("2033-01-01"))
				.startdatumDienst(LocalDate.parse("2000-01-01"))
				.voltijdsalaris(new BigDecimal("40000"))
				.huidigeWaardeBeleggingen(new BigDecimal("100"))
				.rekening(UPDATED_REKENING)
				.deeltijdPercentage(new BigDecimal("100"))
				.id(updatedDeelnemer.getId())
				.pensioenRegeling(updatedPensioenRegeling).build();
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(updatedDeelnemer);

		restDeelnemerMockMvc
				.perform(put("/api/deelnemers").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO)))
				.andDo(print()).andExpect(status().isOk());

		// Validate the Deelnemer in the database
		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
		Deelnemer testDeelnemer = deelnemerList.get(deelnemerList.size() - 1);

		assertThat(testDeelnemer.getVerwachteWaardePensioenResultaat()).isEqualTo(new BigDecimal("24548.84"));
		assertThat(testDeelnemer.getJaarlijksePremieStortingResultaat()).isEqualByComparingTo("434.38");
	}

	@Test
	@Transactional
	public void updateDeelnemerScenario2WelPensioenRegeling() throws Exception {
		// Initialize the database

		pensioenRegelingRepository.saveAndFlush(pensioenRegelingScenario2);
		PensioenRegeling updatedPensioenRegeling = pensioenRegelingRepository
				.findById(pensioenRegelingScenario2.getId()).get();

		deelnemerScenario2.setPensioenRegeling(updatedPensioenRegeling);

		deelnemerRepository.saveAndFlush(deelnemerScenario2);

		int databaseSizeBeforeUpdate = deelnemerRepository.findAll().size();

		// Update the deelnemer
		Deelnemer updatedDeelnemer = deelnemerRepository.findById(deelnemerScenario2.getId()).get();
		// Disconnect from session so that the updates on updatedDeelnemer are not
		// directly saved in db
		em.detach(updatedDeelnemer);
		updatedDeelnemer = Deelnemer.builder()
				.naam(UPDATED_NAAM)
				.adres(UPDATED_ADRES)
				.woonplaats(UPDATED_WOONPLAATS)
				.email(UPDATED_EMAIL)
				.geboortedatum(LocalDate.parse("1980-01-01"))
				.einddatumDienst(LocalDate.parse("2033-01-01"))
				.startdatumDienst(LocalDate.parse("2000-01-01"))
				.voltijdsalaris(new BigDecimal("40000"))
				.huidigeWaardeBeleggingen(new BigDecimal("100"))
				.rekening(UPDATED_REKENING)
				.deeltijdPercentage(new BigDecimal("100"))
				.id(updatedDeelnemer.getId())
				.pensioenRegeling(updatedPensioenRegeling).build();
		DeelnemerDTO deelnemerDTO = deelnemerMapper.toDto(updatedDeelnemer);

		restDeelnemerMockMvc.perform(put("/api/deelnemers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(deelnemerDTO)))
				.andDo(print())
				.andExpect(status().isOk());

		// Validate the Deelnemer in the database
		List<Deelnemer> deelnemerList = deelnemerRepository.findAll();
		assertThat(deelnemerList).hasSize(databaseSizeBeforeUpdate);
		Deelnemer testDeelnemer = deelnemerList.get(deelnemerList.size() - 1);

		assertThat(testDeelnemer.getVerwachteWaardePensioenResultaat()).isEqualTo(new BigDecimal("68470.93"));
		assertThat(testDeelnemer.getJaarlijksePremieStortingResultaat()).isEqualByComparingTo("1220.05");
	}
}
