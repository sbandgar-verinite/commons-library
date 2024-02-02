package com.verinite.commons.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.verinite.commons.model.LookupColumns;

@Repository
public interface LookupColumnRepository extends JpaRepository<LookupColumns, Long> {

}