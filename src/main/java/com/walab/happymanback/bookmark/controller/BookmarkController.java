package com.walab.happymanback.bookmark.controller;

import com.walab.happymanback.bookmark.controller.request.BookmarkRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.walab.happymanback.bookmark.service.BookmarkService;

@RestController
@RequiredArgsConstructor
public class BookmarkController {
  private final BookmarkService bookmarkService;

  @PostMapping("/api/happyman/bookmarks")
  public void createBookmark(
      @RequestBody BookmarkRequest request, @AuthenticationPrincipal String uniqueId) {
    bookmarkService.createBookmark(uniqueId, request.getProgramId());
  }

  @DeleteMapping("/api/happyman/bookmarks")
  public void deleteBookmark(
      @RequestBody BookmarkRequest request, @AuthenticationPrincipal String uniqueId) {
    bookmarkService.deleteBookmark(uniqueId, request.getProgramId());
  }
}
