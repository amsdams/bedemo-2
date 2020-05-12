package com.amsdams.ex.simplerest.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * A DTO for the {@link com.amsdams.ex.simplerest.domain.Deelnemer} entity.
 */
@Data
public class DeelnemerDTO implements Serializable {

    private Long id;

    @NotNull
    private String naam;

    @NotNull
    private String adres;

    @NotNull
    private String woonplaats;

    @NotNull
    private String email;

    @NotNull
    private LocalDate geboortedatum;

    @NotNull
    private LocalDate einddatumDienst;

    @NotNull
    private LocalDate startdatumDienst;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal voltijdsalaris;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal deeltijdPercentage;
    
    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal huidigeWaardeBeleggingen;

    @NotNull
    private String rekening;


    private Long pensioenRegelingId;

    private String pensioenRegelingNaam;

    private BigDecimal jaarlijksePremieStortingResultaat;
    
    private BigDecimal verwachteWaardePensioenResultaat;
}
