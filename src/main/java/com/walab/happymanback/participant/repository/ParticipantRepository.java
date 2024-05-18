package com.walab.happymanback.participant.repository;

import com.walab.happymanback.participant.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query("SELECT COUNT(p) > 0 FROM Participant p WHERE p.program.id = :programId AND p.user.uniqueId = :userId")
    Boolean existsByProgramIdAndUserId(Long programId, String userId);

    @Modifying
    @Query("DELETE FROM Participant p WHERE p.program.id = :programId")
    void deleteByProgramId(Long programId);
}
