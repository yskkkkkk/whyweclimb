package com.whyweclimb.backend.domain.user.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
	private String secret = "thisisfakesecretcodeforinitprocessitdidntusedduringrealprocess";

	private Key SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());

	// 객체 초기화, secretKey를 Base64로 인코딩한다.
	@PostConstruct
	protected void init() {
		this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}

	// 토큰 유효시간 12시간
	private long tokenValidTime = 12 * 60 * 60 * 1000L;
	private final UserDetailsService userDetailsService;

	// JWT 토큰 생성
	public String createToken(String userPk, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userPk);
		// JWT payload 에 저장되는 정보단위
		claims.put("roles", roles);
		// 정보는 key / value 쌍으로 저장된다.
		Date now = new Date();
		return Jwts.builder().setClaims(claims) // 정보 저장
				.setIssuedAt(now) // 토큰 발행 시간 정보
				.setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
				.signWith(SECRET_KEY)// signature 에 들어갈 secret값 세팅
				.compact();
	}

	// JWT 토큰에서 인증 정보 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
		ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new UsernamePasswordAuthenticationToken(userDetails, "", grantedAuthorities);
	} // 토큰에서 회원 정보 추출

	public String getUserPk(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
	}

	// Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}

	// 토큰의 유효성 + 만료일자 확인
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}
}
