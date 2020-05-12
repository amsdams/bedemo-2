package com.amsdams.ex.simplerest.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.amsdams.ex.simplerest.service.dto.DeelnemerDTO;
import com.amsdams.ex.simplerest.service.dto.PensioenRegelingDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JaarlijksePremieStortingBerekeningService {
	/*
	 * (Full-time salaris – Franchise) * Parttime percentage * Beschikbare premie
	 * percentage
	 */
	public BigDecimal getJaarlijksePremieStorting(BigDecimal deelnemerVoltijdsalaris, BigDecimal pensioenRegelingVoltijdFranchise, BigDecimal deelnemerDeeltijdPercentage, BigDecimal pensioenRegelingPremiePercentage) {
		/*
		 * return (jaarlijksePremieStortingBerekening.getVoltijdSalaris() -
		 * jaarlijksePremieStortingBerekening.getVoltijdFranchise())
		 * (jaarlijksePremieStortingBerekening.getDeeltijdPercentage() / 100)
		 * (jaarlijksePremieStortingBerekening.getPremiePercentage() / 100);
		 */
		BigDecimal prt1 = deelnemerVoltijdsalaris
				.subtract(pensioenRegelingVoltijdFranchise);

		BigDecimal prt2 = deelnemerDeeltijdPercentage.divide(new BigDecimal(100));

		BigDecimal prt3 = pensioenRegelingPremiePercentage.divide(new BigDecimal(100));

		BigDecimal prt4 = prt2.multiply(prt3);
		BigDecimal prt5 = prt1.multiply(prt4);
		return prt5.setScale(2, RoundingMode.HALF_EVEN);

	}

}
