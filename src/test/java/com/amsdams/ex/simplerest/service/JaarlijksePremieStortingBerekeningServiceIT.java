package com.amsdams.ex.simplerest.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.amsdams.ex.simplerest.Application;
import com.amsdams.ex.simplerest.service.impl.JaarlijksePremieStortingBerekeningService;

@SpringBootTest(classes = Application.class)
@Transactional
public class JaarlijksePremieStortingBerekeningServiceIT {

	@Autowired
	private JaarlijksePremieStortingBerekeningService jaarlijksePremieStortingBerekeningService;

	@Test
	void getJaarlijksePremieStortingScenario1() {
		BigDecimal deelnemerVoltijdsalaris = new BigDecimal("40000");
		BigDecimal pensioenRegelingVoltijdFranchise = new BigDecimal("13785");
		BigDecimal deelnemerDeeltijdPercentage = new BigDecimal("100");
		BigDecimal pensioenRegelingPremiePercentage = new BigDecimal("1.657");

		BigDecimal jaarlijksePremieStorting = jaarlijksePremieStortingBerekeningService.getJaarlijksePremieStorting(deelnemerVoltijdsalaris,
				pensioenRegelingVoltijdFranchise, deelnemerDeeltijdPercentage, pensioenRegelingPremiePercentage);
		
		assertThat(jaarlijksePremieStorting).isEqualTo(new BigDecimal("434.38"));


	}
	
	@Test
	void getJaarlijksePremieStortingScenario2() {
		BigDecimal deelnemerVoltijdsalaris = new BigDecimal("40000");
		BigDecimal pensioenRegelingVoltijdFranchise = new BigDecimal("15599");
		BigDecimal deelnemerDeeltijdPercentage = new BigDecimal("100");
		BigDecimal pensioenRegelingPremiePercentage = new BigDecimal("5");

		BigDecimal jaarlijksePremieStorting = jaarlijksePremieStortingBerekeningService.getJaarlijksePremieStorting(deelnemerVoltijdsalaris,
				pensioenRegelingVoltijdFranchise, deelnemerDeeltijdPercentage, pensioenRegelingPremiePercentage);
		
		assertThat(jaarlijksePremieStorting).isEqualTo(new BigDecimal("1220.05"));


	}
}
