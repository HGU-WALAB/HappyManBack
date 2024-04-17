package com.walab.happymanback.participant.repository;

import com.walab.happymanback.participant.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Participant p WHERE p.program.id = :programId AND p.user.uniqueId = :userId")
    Boolean existsByProgramIdAndUserId(Long programId, String userId);
}
