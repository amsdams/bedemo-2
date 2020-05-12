package com.amsdams.ex.simplerest.domain;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A PensioenRegeling.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PensioenRegeling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "naam", nullable = false)
    private String naam;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "voltijd_franchise", precision = 21, scale = 2, nullable = false)
    private BigDecimal voltijdFranchise;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "premie_percentage", precision = 21, scale = 2, nullable = false)
    private BigDecimal premiePercentage;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "jaarlijks_rendement_beleggingen", precision = 21, scale = 2, nullable = false)
    private BigDecimal jaarlijksRendementBeleggingen;

    @OneToMany(mappedBy = "pensioenRegeling")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Deelnemer> deelnemers = new HashSet<>();

    public PensioenRegeling addDeelnemer(Deelnemer deelnemer) {
        this.deelnemers.add(deelnemer);
        deelnemer.setPensioenRegeling(this);
        return this;
    }

    public PensioenRegeling removeDeelnemer(Deelnemer deelnemer) {
        this.deelnemers.remove(deelnemer);
        deelnemer.setPensioenRegeling(null);
        return this;
    }

    @Override
    public String toString() {
        return "PensioenRegeling{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", voltijdFranchise=" + getVoltijdFranchise() +
            ", premiePercentage=" + getPremiePercentage() +
            ", jaarlijksRendementBeleggingen=" + getJaarlijksRendementBeleggingen() +
            "}";
    }
}
