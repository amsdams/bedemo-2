package com.amsdams.ex.simplerest.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * A DTO for the {@link com.amsdams.befrank.domain.PensioenRegeling} entity.
 */
@Data
public class PensioenRegelingDTO implements Serializable {

    private Long id;

    @NotNull
    private String naam;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal voltijdFranchise;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal premiePercentage;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal jaarlijksRendementBeleggingen;


    
}
