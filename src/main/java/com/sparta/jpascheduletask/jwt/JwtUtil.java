package com.sparta.jpascheduletask.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    // JWT 데이터
    // Header KEY 값 (쿠키의 네임값)
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // Token 식별자(이게 토큰입니다 알려주는 것)
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    //application.properties에서 선언한 키 값을 넣어주기
    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 로그 설정 (시간순으로 가록)
    public static final Logger log = LoggerFactory.getLogger("JWT 관련 로그");

    @PostConstruct  //생성자 호출 뒤에 실행되는 메서드
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes); //변환 후 key에 우리가 사용할 secret key가 담긴다
    }

    // JWT 토큰 생성
    public String createToken(String username) {
        Date date = new Date();
        // 데이터와 함께 암호화 후 토큰으로 만들어지고, 'Bearer '랑 햡쳐지면서 반환
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간 60분
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }


    // 생성된 JWT를 Cookie에 저장
    // JWT Cookie 에 저장
    public void addJwtToCookie(String token, HttpServletResponse res) {
        try { // Cookie Value 에는 공백이 불가능해서 encoding 진행(bearer 뒤의 공백)
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");

            jakarta.servlet.http.Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
            cookie.setPath("/");

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }


    // Cookie에 들어있던 JWT 토큰을 substring
    // JWT 토큰 substring
    public String substringToken(String tokenValue) {
        // 공백과 널이 아니고, 토큰밸류가 Bearer로 시작하면
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            // 'bearer ' 이게 7글자라서 자른다. 그래야 토큰값이 나오니까
            return tokenValue.substring(7);
        }
        log.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }


    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            // 암호화할 때 사용한 키, 받아온 토큰
            // 이 한줄로 토큰에 위변조가 있는지 검증
            Jwts.parserBuilder()
                    .setSigningKey(key) // 비밀 키 설정
                    .build() // 파서 빌더 빌드
                    .parseClaimsJws(token); // 토큰 파싱 및 검증
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            // 토큰 서명이 잘못되었거나, 잘못된 형식의 JWT가 전달된 경우
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Expired JWT token, 만료된 JWT token 입니다.", e);
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 JWT 형식이 전달된 경우
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
        } catch (IllegalArgumentException e) {
            // JWT 클레임이 비어 있거나 잘못된 형식일 경우
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JWT claims is empty, 잘못된 JWT 토큰 입니다.", e);
        }

    }


    // 문제 없으면 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        // 겟바디로 바디부분에 들어있는 claims 집합을 반환 그 안에 들어있는 사용자 정보 꺼냄
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // HttpServletRequest 에서 Cookie Value : JWT 가져오기
    public String getTokenFromRequest(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8"); // Encode 되어 넘어간 Value 다시 Decode
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

}

