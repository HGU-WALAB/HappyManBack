package com.walab.happymanback.bookmark.repository;

import com.walab.happymanback.bookmark.entity.Bookmark;
import com.walab.happymanback.program.entity.Program;
import com.walab.happymanback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByUserAndProgram(User user, Program program);

    void deleteByUserAndProgram(User user, Program program);

    List<Bookmark> findAllByUser(User user);

    @Modifying
    @Query("delete from Bookmark b where b.program.id in :ids")
    void deleteByProgramIds(List<Long> ids);
}
