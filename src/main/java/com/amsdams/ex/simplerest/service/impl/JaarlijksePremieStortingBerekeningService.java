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
	public BigDecimal getJaarlijksePremieStorting(DeelnemerDTO deelnemerDTO, PensioenRegelingDTO pensioenRegelingDTO) {
		/*
		 * return (jaarlijksePremieStortingBerekening.getVoltijdSalaris() -
		 * jaarlijksePremieStortingBerekening.getVoltijdFranchise())
		 * (jaarlijksePremieStortingBerekening.getDeeltijdPercentage() / 100)
		 * (jaarlijksePremieStortingBerekening.getPremiePercentage() / 100);
		 */
		BigDecimal prt1 = deelnemerDTO.getVoltijdsalaris()
				.subtract(pensioenRegelingDTO.getVoltijdFranchise());

		BigDecimal prt2 = deelnemerDTO.getDeeltijdPercentage().divide(new BigDecimal(100));

		BigDecimal prt3 = pensioenRegelingDTO.getPremiePercentage().divide(new BigDecimal(100));

		BigDecimal prt4 = prt2.multiply(prt3);
		BigDecimal prt5 = prt1.multiply(prt4);
		return prt5.setScale(2, RoundingMode.HALF_UP);

	}

}
