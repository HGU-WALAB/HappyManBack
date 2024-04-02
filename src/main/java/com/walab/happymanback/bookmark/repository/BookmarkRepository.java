package com.walab.happymanback.bookmark.repository;

import com.walab.happymanback.bookmark.entity.Bookmark;
import com.walab.happymanback.program.entity.Program;
import com.walab.happymanback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByUserAndProgram(User user, Program program);

    void deleteByUserAndProgram(User user, Program program);
}
