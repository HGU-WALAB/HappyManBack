package com.walab.happymanback.bookmark.service;

import com.walab.happymanback.base.exception.DoNotExistException;
import com.walab.happymanback.bookmark.dto.BookmarkDto;
import com.walab.happymanback.bookmark.repository.BookmarkRepository;
import com.walab.happymanback.program.entity.Program;
import com.walab.happymanback.program.repository.ProgramRepository;
import com.walab.happymanback.user.entity.User;
import com.walab.happymanback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.walab.happymanback.bookmark.entity.Bookmark;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
  private final BookmarkRepository bookmarkRepository;
  private final UserRepository userRepository;
  private final ProgramRepository programRepository;

  public void createBookmark(String uniqueId, Long programId) {
    User user =
        userRepository
            .findById(uniqueId)
            .orElseThrow(() -> new DoNotExistException("해당 유저가 없습니다."));

    Program program =
        programRepository
            .findById(programId)
            .orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다."));
    if (bookmarkRepository.existsByUserAndProgram(user, program)) {
      return;
    }
    bookmarkRepository.save(Bookmark.builder().user(user).program(program).build());
  }

  public void deleteBookmark(String uniqueId, Long programId) {
    User user =
        userRepository
            .findById(uniqueId)
            .orElseThrow(() -> new DoNotExistException("해당 유저가 없습니다."));

    Program program =
        programRepository
            .findById(programId)
            .orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다."));
    bookmarkRepository.deleteByUserAndProgram(user, program);
  }

  public List<BookmarkDto> getBookmarks(String uniqueId) {
    User user =
        userRepository
            .findById(uniqueId)
            .orElseThrow(() -> new DoNotExistException("해당 유저가 없습니다."));
    return bookmarkRepository.findAllByUser(user).stream()
        .map(BookmarkDto::from)
        .collect(Collectors.toList());
  }

    public boolean isBookmarked(String uniqueId, Long programId) {
        User user =
            userRepository
                .findById(uniqueId)
                .orElseThrow(() -> new DoNotExistException("해당 유저가 없습니다."));

        Program program =
            programRepository
                .findById(programId)
                .orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다."));
        return bookmarkRepository.existsByUserAndProgram(user, program);
    }
}
