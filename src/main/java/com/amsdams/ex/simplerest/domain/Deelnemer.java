package com.amsdams.ex.simplerest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Deelnemer.
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Deelnemer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "naam", nullable = false)
	private String naam;

	@NotNull
	@Column(name = "adres", nullable = false)
	private String adres;

	@NotNull
	@Column(name = "woonplaats", nullable = false)
	private String woonplaats;

	@NotNull
	@Column(name = "email", nullable = false)
	private String email;

	@NotNull
	@Column(name = "geboortedatum", nullable = false)
	private LocalDate geboortedatum;

	@NotNull
	@Column(name = "einddatum_dienst", nullable = false)
	private LocalDate einddatumDienst;

	@NotNull
	@Column(name = "startdatum_dienst", nullable = false)
	private LocalDate startdatumDienst;

	@NotNull
	@DecimalMin(value = "0")
	@Column(name = "voltijdsalaris", precision = 21, scale = 2, nullable = false)
	private BigDecimal voltijdsalaris;

	@NotNull
	@DecimalMin(value = "0")
	@DecimalMax(value = "100")
	@Column(name = "deeltijd_percentage", precision = 21, scale = 2, nullable = false)
	private BigDecimal deeltijdPercentage;

	@NotNull
	@DecimalMin(value = "0")
	@Column(name = "huidige_waarde_beleggingen", precision = 21, scale = 2, nullable = false)
	private BigDecimal huidigeWaardeBeleggingen;

	@NotNull
	@Column(name = "rekening", nullable = false)
	private String rekening;

	@Column(name = "jaarlijkse_premie_storting_resultaat", precision = 21, scale = 2)
	private BigDecimal jaarlijksePremieStortingResultaat;

	@Column(name = "verwachte_waarde_pensioen_resultaat", precision = 21, scale = 2)
	private BigDecimal verwachteWaardePensioenResultaat;

	@ManyToOne
	@JsonIgnoreProperties("deelnemers")
	private PensioenRegeling pensioenRegeling;

	@Override
	public String toString() {
		return "Deelnemer{" + "id=" + getId() + ", naam='" + getNaam() + "'" + ", adres='" + getAdres() + "'"
				+ ", woonplaats='" + getWoonplaats() + "'" + ", email='" + getEmail() + "'" + ", geboortedatum='"
				+ getGeboortedatum() + "'" + ", einddatumDienst='" + getEinddatumDienst() + "'" + ", startdatumDienst='"
				+ getStartdatumDienst() + "'" + ", voltijdsalaris=" + getVoltijdsalaris()
				+ ", huidigeWaardeBeleggingen=" + getHuidigeWaardeBeleggingen() + ", rekening='" + getRekening() + "'"
				+ "}";
	}
}
