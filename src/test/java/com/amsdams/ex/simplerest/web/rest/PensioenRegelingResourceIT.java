package com.amsdams.ex.simplerest.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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
import com.amsdams.ex.simplerest.domain.PensioenRegeling;
import com.amsdams.ex.simplerest.repository.PensioenRegelingRepository;
import com.amsdams.ex.simplerest.service.PensioenRegelingService;
import com.amsdams.ex.simplerest.service.dto.PensioenRegelingDTO;
import com.amsdams.ex.simplerest.service.mapper.PensioenRegelingMapper;
import com.amsdams.ex.simplerest.web.rest.errors.ExceptionTranslator;

/**
 * Integration tests for the {@link PensioenRegelingResource} REST controller.
 */
@SpringBootTest(classes = Application.class)
public class PensioenRegelingResourceIT {

    private static final String DEFAULT_NAAM = "AAAAAAAAAA";
    private static final String UPDATED_NAAM = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VOLTIJD_FRANCHISE = new BigDecimal(0);
    private static final BigDecimal UPDATED_VOLTIJD_FRANCHISE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_PREMIE_PERCENTAGE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PREMIE_PERCENTAGE = new BigDecimal(1);

    //private static final BigDecimal DEFAULT_DEELTIJD_PERCENTAGE = new BigDecimal(0);
    //private static final BigDecimal UPDATED_DEELTIJD_PERCENTAGE = new BigDecimal(1);

    private static final BigDecimal DEFAULT_JAARLIJKS_RENDEMENT_BELEGGINGEN = new BigDecimal(0);
    private static final BigDecimal UPDATED_JAARLIJKS_RENDEMENT_BELEGGINGEN = new BigDecimal(1);

    @Autowired
    private PensioenRegelingRepository pensioenRegelingRepository;

    @Autowired
    private PensioenRegelingMapper pensioenRegelingMapper;

    @Autowired
    private PensioenRegelingService pensioenRegelingService;

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

    private MockMvc restPensioenRegelingMockMvc;

    private PensioenRegeling pensioenRegeling;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PensioenRegelingResource pensioenRegelingResource = new PensioenRegelingResource(pensioenRegelingService);
        this.restPensioenRegelingMockMvc = MockMvcBuilders.standaloneSetup(pensioenRegelingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            //.setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PensioenRegeling createEntity(EntityManager em) {
        PensioenRegeling pensioenRegeling = PensioenRegeling.builder()
            .naam(DEFAULT_NAAM)
            .voltijdFranchise(DEFAULT_VOLTIJD_FRANCHISE)
            .premiePercentage(DEFAULT_PREMIE_PERCENTAGE)
            .jaarlijksRendementBeleggingen(DEFAULT_JAARLIJKS_RENDEMENT_BELEGGINGEN).build();
        return pensioenRegeling;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    /*public static PensioenRegeling createUpdatedEntity(EntityManager em) {
        PensioenRegeling pensioenRegeling = PensioenRegeling.builder()
            .naam(UPDATED_NAAM)
            .voltijdFranchise(UPDATED_VOLTIJD_FRANCHISE)
            .premiePercentage(UPDATED_PREMIE_PERCENTAGE)
            .jaarlijksRendementBeleggingen(UPDATED_JAARLIJKS_RENDEMENT_BELEGGINGEN).build();
        return pensioenRegeling;
    }
*/
    @BeforeEach
    public void initTest() {
        pensioenRegeling = createEntity(em);
    }
/*
    @Test
    @Transactional
    public void createPensioenRegeling() throws Exception {
        int databaseSizeBeforeCreate = pensioenRegelingRepository.findAll().size();

        // Create the PensioenRegeling
        PensioenRegelingDTO pensioenRegelingDTO = pensioenRegelingMapper.toDto(pensioenRegeling);
        restPensioenRegelingMockMvc.perform(post("/api/pensioen-regelings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensioenRegelingDTO)))
            .andExpect(status().isCreated());

        // Validate the PensioenRegeling in the database
        List<PensioenRegeling> pensioenRegelingList = pensioenRegelingRepository.findAll();
        assertThat(pensioenRegelingList).hasSize(databaseSizeBeforeCreate + 1);
        PensioenRegeling testPensioenRegeling = pensioenRegelingList.get(pensioenRegelingList.size() - 1);
        assertThat(testPensioenRegeling.getNaam()).isEqualTo(DEFAULT_NAAM);
        assertThat(testPensioenRegeling.getVoltijdFranchise()).isEqualTo(DEFAULT_VOLTIJD_FRANCHISE);
        assertThat(testPensioenRegeling.getPremiePercentage()).isEqualTo(DEFAULT_PREMIE_PERCENTAGE);
        assertThat(testPensioenRegeling.getDeeltijdPercentage()).isEqualTo(DEFAULT_DEELTIJD_PERCENTAGE);
        assertThat(testPensioenRegeling.getJaarlijksRendementBeleggingen()).isEqualTo(DEFAULT_JAARLIJKS_RENDEMENT_BELEGGINGEN);
    }
*/
    
    /*
    @Test
    @Transactional
    public void createPensioenRegelingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pensioenRegelingRepository.findAll().size();

        // Create the PensioenRegeling with an existing ID
        pensioenRegeling.setId(1L);
        PensioenRegelingDTO pensioenRegelingDTO = pensioenRegelingMapper.toDto(pensioenRegeling);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPensioenRegelingMockMvc.perform(put("/api/pensioen-regelings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensioenRegelingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PensioenRegeling in the database
        List<PensioenRegeling> pensioenRegelingList = pensioenRegelingRepository.findAll();
        assertThat(pensioenRegelingList).hasSize(databaseSizeBeforeCreate);
    }
*/

    @Test
    @Transactional
    public void checkNaamIsRequired() throws Exception {
        int databaseSizeBeforeTest = pensioenRegelingRepository.findAll().size();
        // set the field null
        pensioenRegeling.setNaam(null);

        // Create the PensioenRegeling, which fails.
        PensioenRegelingDTO pensioenRegelingDTO = pensioenRegelingMapper.toDto(pensioenRegeling);

        restPensioenRegelingMockMvc.perform(put("/api/pensioen-regelings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensioenRegelingDTO)))
            .andExpect(status().isBadRequest());

        List<PensioenRegeling> pensioenRegelingList = pensioenRegelingRepository.findAll();
        assertThat(pensioenRegelingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVoltijdFranchiseIsRequired() throws Exception {
        int databaseSizeBeforeTest = pensioenRegelingRepository.findAll().size();
        // set the field null
        pensioenRegeling.setVoltijdFranchise(null);

        // Create the PensioenRegeling, which fails.
        PensioenRegelingDTO pensioenRegelingDTO = pensioenRegelingMapper.toDto(pensioenRegeling);

        restPensioenRegelingMockMvc.perform(put("/api/pensioen-regelings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensioenRegelingDTO)))
            .andExpect(status().isBadRequest());

        List<PensioenRegeling> pensioenRegelingList = pensioenRegelingRepository.findAll();
        assertThat(pensioenRegelingList).hasSize(databaseSizeBeforeTest);
    }

    
 
    @Test
    @Transactional
    public void checkPremiePercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = pensioenRegelingRepository.findAll().size();
        // set the field null
        pensioenRegeling.setPremiePercentage(null);

        // Create the PensioenRegeling, which fails.
        PensioenRegelingDTO pensioenRegelingDTO = pensioenRegelingMapper.toDto(pensioenRegeling);

        restPensioenRegelingMockMvc.perform(put("/api/pensioen-regelings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensioenRegelingDTO)))
            .andExpect(status().isBadRequest());

        List<PensioenRegeling> pensioenRegelingList = pensioenRegelingRepository.findAll();
        assertThat(pensioenRegelingList).hasSize(databaseSizeBeforeTest);
    }

    

    @Test
    @Transactional
    public void checkJaarlijksRendementBeleggingenIsRequired() throws Exception {
        int databaseSizeBeforeTest = pensioenRegelingRepository.findAll().size();
        // set the field null
        pensioenRegeling.setJaarlijksRendementBeleggingen(null);

        // Create the PensioenRegeling, which fails.
        PensioenRegelingDTO pensioenRegelingDTO = pensioenRegelingMapper.toDto(pensioenRegeling);

        restPensioenRegelingMockMvc.perform(put("/api/pensioen-regelings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensioenRegelingDTO)))
            .andExpect(status().isBadRequest());

        List<PensioenRegeling> pensioenRegelingList = pensioenRegelingRepository.findAll();
        assertThat(pensioenRegelingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPensioenRegelings() throws Exception {
        // Initialize the database
        pensioenRegelingRepository.saveAndFlush(pensioenRegeling);

        // Get all the pensioenRegelingList
        restPensioenRegelingMockMvc.perform(get("/api/pensioen-regelings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pensioenRegeling.getId().intValue())))
            .andExpect(jsonPath("$.[*].naam").value(hasItem(DEFAULT_NAAM)))
            .andExpect(jsonPath("$.[*].voltijdFranchise").value(hasItem(DEFAULT_VOLTIJD_FRANCHISE.intValue())))
            .andExpect(jsonPath("$.[*].premiePercentage").value(hasItem(DEFAULT_PREMIE_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].jaarlijksRendementBeleggingen").value(hasItem(DEFAULT_JAARLIJKS_RENDEMENT_BELEGGINGEN.intValue())));
    }
    
    @Test
    @Transactional
    public void getPensioenRegeling() throws Exception {
        // Initialize the database
        pensioenRegelingRepository.saveAndFlush(pensioenRegeling);

        // Get the pensioenRegeling
        restPensioenRegelingMockMvc.perform(get("/api/pensioen-regelings/{id}", pensioenRegeling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pensioenRegeling.getId().intValue()))
            .andExpect(jsonPath("$.naam").value(DEFAULT_NAAM))
            .andExpect(jsonPath("$.voltijdFranchise").value(DEFAULT_VOLTIJD_FRANCHISE.intValue()))
            .andExpect(jsonPath("$.premiePercentage").value(DEFAULT_PREMIE_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.jaarlijksRendementBeleggingen").value(DEFAULT_JAARLIJKS_RENDEMENT_BELEGGINGEN.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPensioenRegeling() throws Exception {
        // Get the pensioenRegeling
        restPensioenRegelingMockMvc.perform(get("/api/pensioen-regelings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePensioenRegeling() throws Exception {
        // Initialize the database
        pensioenRegelingRepository.saveAndFlush(pensioenRegeling);

        int databaseSizeBeforeUpdate = pensioenRegelingRepository.findAll().size();

        // Update the pensioenRegeling
        PensioenRegeling updatedPensioenRegeling = pensioenRegelingRepository.findById(pensioenRegeling.getId()).get();
        // Disconnect from session so that the updates on updatedPensioenRegeling are not directly saved in db
        em.detach(updatedPensioenRegeling);
        updatedPensioenRegeling = PensioenRegeling.builder()
            .naam(UPDATED_NAAM)
            .voltijdFranchise(UPDATED_VOLTIJD_FRANCHISE)
            .premiePercentage(UPDATED_PREMIE_PERCENTAGE)
            .jaarlijksRendementBeleggingen(UPDATED_JAARLIJKS_RENDEMENT_BELEGGINGEN).id(updatedPensioenRegeling.getId()).build();
        PensioenRegelingDTO pensioenRegelingDTO = pensioenRegelingMapper.toDto(updatedPensioenRegeling);

        restPensioenRegelingMockMvc.perform(put("/api/pensioen-regelings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensioenRegelingDTO)))
            .andExpect(status().isOk());

        // Validate the PensioenRegeling in the database
        List<PensioenRegeling> pensioenRegelingList = pensioenRegelingRepository.findAll();
        assertThat(pensioenRegelingList).hasSize(databaseSizeBeforeUpdate);
        PensioenRegeling testPensioenRegeling = pensioenRegelingList.get(pensioenRegelingList.size() - 1);
        assertThat(testPensioenRegeling.getNaam()).isEqualTo(UPDATED_NAAM);
        assertThat(testPensioenRegeling.getVoltijdFranchise()).isEqualTo(UPDATED_VOLTIJD_FRANCHISE);
        assertThat(testPensioenRegeling.getPremiePercentage()).isEqualTo(UPDATED_PREMIE_PERCENTAGE);
        assertThat(testPensioenRegeling.getJaarlijksRendementBeleggingen()).isEqualTo(UPDATED_JAARLIJKS_RENDEMENT_BELEGGINGEN);
    }

    
  
    @Test
    @Transactional
    public void updateNonExistingPensioenRegeling() throws Exception {
        int databaseSizeBeforeUpdate = pensioenRegelingRepository.findAll().size();

        // Create the PensioenRegeling
        PensioenRegelingDTO pensioenRegelingDTO = pensioenRegelingMapper.toDto(pensioenRegeling);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPensioenRegelingMockMvc.perform(put("/api/pensioen-regelings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensioenRegelingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PensioenRegeling in the database
        List<PensioenRegeling> pensioenRegelingList = pensioenRegelingRepository.findAll();
        assertThat(pensioenRegelingList).hasSize(databaseSizeBeforeUpdate);
    }

    
    /*
    @Test
    @Transactional
    public void deletePensioenRegeling() throws Exception {
        // Initialize the database
        pensioenRegelingRepository.saveAndFlush(pensioenRegeling);

        int databaseSizeBeforeDelete = pensioenRegelingRepository.findAll().size();

        // Delete the pensioenRegeling
        restPensioenRegelingMockMvc.perform(delete("/api/pensioen-regelings/{id}", pensioenRegeling.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PensioenRegeling> pensioenRegelingList = pensioenRegelingRepository.findAll();
        assertThat(pensioenRegelingList).hasSize(databaseSizeBeforeDelete - 1);
    }
    */
    
    @Test
    @Transactional
    public void updatePensioenRegelingScenario1() throws Exception {
        // Initialize the database
        pensioenRegelingRepository.saveAndFlush(pensioenRegeling);

        int databaseSizeBeforeUpdate = pensioenRegelingRepository.findAll().size();

        // Update the pensioenRegeling
        PensioenRegeling updatedPensioenRegeling = pensioenRegelingRepository.findById(pensioenRegeling.getId()).get();
        // Disconnect from session so that the updates on updatedPensioenRegeling are not directly saved in db
        em.detach(updatedPensioenRegeling);
        updatedPensioenRegeling = PensioenRegeling.builder()
            .naam(UPDATED_NAAM)
            .voltijdFranchise(new BigDecimal("13785"))
            .premiePercentage(new BigDecimal("1.657"))
            .jaarlijksRendementBeleggingen(new BigDecimal("3"))
            .id(updatedPensioenRegeling.getId()).build();
        PensioenRegelingDTO pensioenRegelingDTO = pensioenRegelingMapper.toDto(updatedPensioenRegeling);

        restPensioenRegelingMockMvc.perform(put("/api/pensioen-regelings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensioenRegelingDTO)))
            .andExpect(status().isOk());

        // Validate the PensioenRegeling in the database
        List<PensioenRegeling> pensioenRegelingList = pensioenRegelingRepository.findAll();
        assertThat(pensioenRegelingList).hasSize(databaseSizeBeforeUpdate);
        PensioenRegeling testPensioenRegeling = pensioenRegelingList.get(pensioenRegelingList.size() - 1);
        assertThat(testPensioenRegeling.getNaam()).isEqualTo(UPDATED_NAAM);
        assertThat(testPensioenRegeling.getVoltijdFranchise()).isEqualTo(new BigDecimal("13785"));
        assertThat(testPensioenRegeling.getPremiePercentage()).isEqualTo(new BigDecimal("1.657"));
        assertThat(testPensioenRegeling.getJaarlijksRendementBeleggingen()).isEqualTo(new BigDecimal("3"));
    }
    
    @Test
    @Transactional
    public void updatePensioenRegelingScenario2() throws Exception {
        // Initialize the database
        pensioenRegelingRepository.saveAndFlush(pensioenRegeling);

        int databaseSizeBeforeUpdate = pensioenRegelingRepository.findAll().size();

        // Update the pensioenRegeling
        PensioenRegeling updatedPensioenRegeling = pensioenRegelingRepository.findById(pensioenRegeling.getId()).get();
        // Disconnect from session so that the updates on updatedPensioenRegeling are not directly saved in db
        em.detach(updatedPensioenRegeling);
        updatedPensioenRegeling = PensioenRegeling.builder()
            .naam(UPDATED_NAAM)
            .voltijdFranchise(new BigDecimal("15599"))
            .premiePercentage(new BigDecimal("5"))
            .jaarlijksRendementBeleggingen(new BigDecimal("3"))
            .id(updatedPensioenRegeling.getId()).build();
        PensioenRegelingDTO pensioenRegelingDTO = pensioenRegelingMapper.toDto(updatedPensioenRegeling);

        restPensioenRegelingMockMvc.perform(put("/api/pensioen-regelings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pensioenRegelingDTO)))
            .andExpect(status().isOk());

        // Validate the PensioenRegeling in the database
        List<PensioenRegeling> pensioenRegelingList = pensioenRegelingRepository.findAll();
        assertThat(pensioenRegelingList).hasSize(databaseSizeBeforeUpdate);
        PensioenRegeling testPensioenRegeling = pensioenRegelingList.get(pensioenRegelingList.size() - 1);
        assertThat(testPensioenRegeling.getNaam()).isEqualTo(UPDATED_NAAM);
        assertThat(testPensioenRegeling.getVoltijdFranchise()).isEqualTo(new BigDecimal("15599"));
        assertThat(testPensioenRegeling.getPremiePercentage()).isEqualTo(new BigDecimal("5"));
        assertThat(testPensioenRegeling.getJaarlijksRendementBeleggingen()).isEqualTo(new BigDecimal("3"));
    }
}
