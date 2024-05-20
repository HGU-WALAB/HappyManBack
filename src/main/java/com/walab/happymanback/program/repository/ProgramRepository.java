package com.walab.happymanback.program.repository;

import com.walab.happymanback.program.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

  @Query("SELECT p FROM Program p JOIN FETCH p.category WHERE p.id = :id")
  Optional<Program> findByIdWithCategory(Long id);

  @Query(
      "SELECT p FROM Program p JOIN FETCH p.participants AS pa JOIN FETCH pa.user WHERE p.id = :id")
  Optional<Program> findByIdWithParticipant(Long id);

  @Modifying
  @Query("DELETE FROM Program p WHERE p.id = :id")
  void deleteById(Long id);

  @Query("SELECT p FROM Program p JOIN FETCH p.category LEFT JOIN FETCH p.files WHERE p.id = :id")
  Optional<Program> findByIdWithAll(Long id);

  @Query("SELECT p FROM Program p JOIN FETCH p.bookmarks b WHERE b.user.uniqueId = :uniqueId")
  List<Program> findAllIsBookmarked(String uniqueId);
}
