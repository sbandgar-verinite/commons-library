package com.verinite.commons.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.verinite.commons.model.LookupTables;

@Repository
public interface LookupTableRepository extends JpaRepository<LookupTables, Long> {

	Optional<LookupTables> findByName(String name);

}