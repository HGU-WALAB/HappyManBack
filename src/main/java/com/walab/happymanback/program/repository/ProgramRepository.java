package com.walab.happymanback.program.repository;

import com.walab.happymanback.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    @Query("SELECT p FROM Program p JOIN FETCH p.category")
    List<Program> findAllWithCategory();

    @Query("SELECT p FROM Program p LEFT JOIN FETCH p.files WHERE p.id = :id")
    Optional<Program> findByIdWithProgramFile(Long id);
}
