package com.walab.happymanback.auth.filter;

import com.walab.happymanback.auth.exception.WrongTokenException;
import com.walab.happymanback.auth.service.AuthService;
import com.walab.happymanback.auth.util.JwtUtil;
import com.walab.happymanback.user.entity.User;
import com.walab.happymanback.auth.exception.DoNotLoginException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  private final AuthService authService;

  private final String SECRET_KEY;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    String pattern1 = ".*/error.*";
    String pattern2 = ".*/api/happyman/auth/.*";
    String pattern3 = ".*/api/happyman/all/.*";
    String pattern4 = ".*/file/.*";

    // 정규 표현식 패턴을 컴파일하여 패턴 객체 생성
    Pattern regex1 = Pattern.compile(pattern1);
    Pattern regex2 = Pattern.compile(pattern2);
    if (regex1.matcher(request.getRequestURI()).matches()
        || regex2.matcher(request.getRequestURI()).matches()
        || request.getRequestURI().matches(pattern3)
        || request.getRequestURI().matches(pattern4)) {
      filterChain.doFilter(request, response);
      return;
    }
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    // Header의 Authorization 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
    if (authorizationHeader == null) throw new DoNotLoginException();

    // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
    if (!authorizationHeader.startsWith("Bearer "))
      throw new WrongTokenException("Bearer 로 시작하지 않는 토큰입니다.");

    // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
    String token = authorizationHeader.split(" ")[1];

    User loginUser = authService.getLoginUser(JwtUtil.getUserId(token, SECRET_KEY));

    // loginUser 정보로 UsernamePasswordAuthenticationToken 발급
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(
            loginUser.getUniqueId(),
            null,
            List.of(new SimpleGrantedAuthority(loginUser.getStatus().name())));
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    // 권한 부여
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    filterChain.doFilter(request, response);
  }
}
