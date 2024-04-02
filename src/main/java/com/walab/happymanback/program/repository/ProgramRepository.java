package com.walab.happymanback.program.repository;

import com.walab.happymanback.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    @Query("SELECT p FROM Program p JOIN FETCH p.category")
    List<Program> findAllWithCategory();
}
