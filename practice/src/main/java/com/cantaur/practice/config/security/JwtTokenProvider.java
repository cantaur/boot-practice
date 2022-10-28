package com.cantaur.practice.config.security;

import com.cantaur.practice.config.resources.JwtToken;
import com.cantaur.practice.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class JwtTokenProvider {
    /**
     * JWT를 생성하고 검증하는 컴포넌트. (실제로 이 컴포넌트를 이용하는 것은 인증 작업을 진행하는 Filter 이다.)
     * JWT에는 토큰 만료 시간이나 회원 권한 정보등을 저장할 수 있습니다.
     */

    private String secretKey;
    private int refreshTokenValidTime;
    private long tokenValidTime = 30 * 60 * 1000L;


    private CustomUserDetailsService userDetailsService;


    public JwtTokenProvider(JwtToken jwtToken,
                            CustomUserDetailsService userDetailsService) {
        this.secretKey = jwtToken.getSecretKey();
        this.refreshTokenValidTime = jwtToken.getExpireLength();
        this.userDetailsService = userDetailsService;
    }

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String username, List<String> roles){
        Claims claims = Jwts.claims().setSubject(username); // claims : JWT payload 에 저장되는 정보단위
        claims.put("roles", roles);                         // 정보는 key와 value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)                          // 정보 저장
                .setIssuedAt(now)                           // 토큰 발행시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 폐기시간 정보
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        log.info("userDetails.getPassword() : "+userDetails.getPassword());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUsername(String token) {
        log.info("token - {}", token);
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        String subject = claims.getBody().getSubject();
        Date date = claims.getBody().getExpiration();
        String id = claims.getBody().getId();
        return subject;
    }


    // Request의 Header에서 token 값을 가져옵니다. "Authorization" :
    private final String USER_SECURITY_TOKEN = "user_security_token";
    public String resolveToken(HttpServletRequest req) {


        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7, bearerToken.length());

            try {
                //DB에 저장되어 있는 토큰을 꺼냄
                String email= getUsername(bearerToken);
                log.info("email : " + email );
                String oldAccessToken= userDetailsService.selectMemberAccessToken(email);

                //현재 토큰 정보 !=DB에 저장되어 있는 토큰 정보와 다르다->토큰 보내지 않음
                if(!bearerToken.equals(oldAccessToken)){
                    return null;
                }
            }catch (Exception e){
                log.info("e : ", e.getMessage());
                return e.getMessage();
            }
            return bearerToken;
        }
        return bearerToken;
    }


    public boolean validateToken(String jwtToken) {
        try {
            log.info("token - {}", jwtToken);
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
