package com.amsdams.ex.simplerest.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Service;

import com.amsdams.ex.simplerest.service.dto.DeelnemerDTO;
import com.amsdams.ex.simplerest.service.dto.PensioenRegelingDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VerwachteWaardePensioenBerekeningService {

	public BigDecimal getVerwachteWaardePensioen(LocalDate deelnemerStartdatumDienst,LocalDate deelnemerEinddatumDienst, BigDecimal deelnemerHuidigeWaardeBeleggingen, BigDecimal deelnemerJaarlijksePremieStortingResultaat, BigDecimal pensioenRegelingJaarlijksRendementBeleggingen) {

		Period period = Period.between(deelnemerStartdatumDienst, deelnemerEinddatumDienst);

		Integer jarenTotPensioen = period.getYears();

		log.info("jarenTotPensioen : {} ", jarenTotPensioen);
		BigDecimal nieuweWaardeBeleggingen = deelnemerHuidigeWaardeBeleggingen;

		log.info("nieuweWaardeBeleggingen : {} ", nieuweWaardeBeleggingen);

		for (int i = 0; i < jarenTotPensioen; i++) {
			nieuweWaardeBeleggingen = getHuidgeWaarde(deelnemerJaarlijksePremieStortingResultaat,pensioenRegelingJaarlijksRendementBeleggingen, nieuweWaardeBeleggingen);
			log.info("huidigeWaardeBeleggingen: {} {}", i, nieuweWaardeBeleggingen);
		}
		return nieuweWaardeBeleggingen;

	}
	/*
	 * Huidige waarde + Jaarlijkse premiestorting + (Huidige waarde + Jaarlijkse
	 * premiestorting/2) * rendement
	 */

	private BigDecimal getHuidgeWaarde(BigDecimal deelnemerJaarlijksePremieStortingResultaat, BigDecimal pensioenRegelingJaarlijksRendementBeleggingen, BigDecimal nieuweWaardeBeleggingen) {
		/*
		 * return nieuweWaardeBeleggingen +
		 * verwachteWaardePensioenBerekening.getJaarlijksePremieStorting() +
		 * (nieuweWaardeBeleggingen +
		 * verwachteWaardePensioenBerekening.getJaarlijksePremieStorting() / 2)
		 * ((verwachteWaardePensioenBerekening.getJaarlijksRendementBeleggingen() /
		 * 100));
		 */

		BigDecimal sum = nieuweWaardeBeleggingen.add(deelnemerJaarlijksePremieStortingResultaat);
		log.info("sum" + sum);

		BigDecimal sum2 = nieuweWaardeBeleggingen
				.add(deelnemerJaarlijksePremieStortingResultaat.divide(BigDecimal.valueOf(2)));
		log.info("sum2" + sum2);

		BigDecimal sum3 = sum2
				.multiply(pensioenRegelingJaarlijksRendementBeleggingen.divide(BigDecimal.valueOf(100)));

		return sum3.add(sum).setScale(2, RoundingMode.HALF_EVEN);

	}
}
