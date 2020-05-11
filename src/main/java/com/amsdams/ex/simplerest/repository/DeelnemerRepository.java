package com.amsdams.ex.simplerest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amsdams.ex.simplerest.domain.Deelnemer;


/**
 * Spring Data  repository for the Deelnemer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeelnemerRepository extends JpaRepository<Deelnemer, Long> {

}
