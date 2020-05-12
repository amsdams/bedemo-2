package com.amsdams.ex.simplerest.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.amsdams.ex.simplerest.Application;
import com.amsdams.ex.simplerest.service.impl.JaarlijksePremieStortingBerekeningService;
import com.amsdams.ex.simplerest.service.impl.VerwachteWaardePensioenBerekeningService;

@SpringBootTest(classes = Application.class)
@Transactional
public class VerwachteWaardePensioenBerekeningServiceIT {

	@Autowired
	private VerwachteWaardePensioenBerekeningService verwachteWaardePensioenBerekeningService;

	@Test
	void getVerwachteWaardePensioenScenario1() {
		LocalDate deelnemerStartdatumDienst = LocalDate.parse("2000-01-01");
		LocalDate deelnemerEinddatumDienst = LocalDate.parse("2033-01-01");
		BigDecimal deelnemerHuidigeWaardeBeleggingen = new BigDecimal("100");
		BigDecimal deelnemerJaarlijksePremieStortingResultaat  = new BigDecimal("434.38");
		
		BigDecimal pensioenRegelingJaarlijksRendementBeleggingen = new BigDecimal("3");

		BigDecimal jaarlijksePremieStorting = verwachteWaardePensioenBerekeningService.getVerwachteWaardePensioen(deelnemerStartdatumDienst, deelnemerEinddatumDienst, deelnemerHuidigeWaardeBeleggingen, deelnemerJaarlijksePremieStortingResultaat, pensioenRegelingJaarlijksRendementBeleggingen);

		
		assertThat(jaarlijksePremieStorting).isEqualTo(new BigDecimal("24548.84"));


	}
	
	@Test
	void getVerwachteWaardePensioenScenario2() {
		LocalDate deelnemerStartdatumDienst = LocalDate.parse("2000-01-01");
		LocalDate deelnemerEinddatumDienst = LocalDate.parse("2033-01-01");
		BigDecimal deelnemerHuidigeWaardeBeleggingen = new BigDecimal("100");
		BigDecimal deelnemerJaarlijksePremieStortingResultaat  = new BigDecimal("1220.05");
		
		BigDecimal pensioenRegelingJaarlijksRendementBeleggingen = new BigDecimal("3");
		

		BigDecimal jaarlijksePremieStorting = verwachteWaardePensioenBerekeningService.getVerwachteWaardePensioen(deelnemerStartdatumDienst, deelnemerEinddatumDienst, deelnemerHuidigeWaardeBeleggingen, deelnemerJaarlijksePremieStortingResultaat, pensioenRegelingJaarlijksRendementBeleggingen);
		
		assertThat(jaarlijksePremieStorting).isEqualTo(new BigDecimal("68470.93"));


	}
}
