package com.amsdams.ex.simplerest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amsdams.ex.simplerest.domain.PensioenRegeling;


/**
 * Spring Data  repository for the PensioenRegeling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PensioenRegelingRepository extends JpaRepository<PensioenRegeling, Long> {

}
