package com.growup.ecountry.config;

import com.growup.ecountry.dto.UserDTO;
import com.growup.ecountry.entity.Users;
import com.growup.ecountry.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    // 토큰에서 userId을 추출
    public String extractId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 토큰에서 만료 시간 추출
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 토큰에서 클레임(클레임은 페이로드의 일부) 추출
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 토큰에서 모든 클레임 추출
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // 토큰이 만료되었는지 확인
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 토큰 생성(userId로 만듬)
        public String generateToken(Long id) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, id);
    }

    // 실제 토큰 생성 로직
    private String createToken(Map<String, Object> claims, Long id) {
        System.out.println("토큰 생성 : " + id);
        System.out.println("토큰 생성 : " + String.valueOf(id));
        String token = Jwts.builder().setClaims(claims).setSubject(String.valueOf(id)).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        return token;
    }

    // 토큰 유효성 검사
    public Long validateToken(String token) {
        System.out.println("유효성 검사 : " + token);
        String[] realToken = token.split(" ");
        System.out.println("유효성 검사 : " + realToken[1]);
        Long id = Long.valueOf(extractId(realToken[1]));
        System.out.println(id);
        Optional<Users> userExist = userRepository.findById(id);
        if(userExist.isPresent()){
           return id;
        }
        else{
            return 0L;
        }
    }
}
// return (username.equals(user.getUserId()) && !isTokenExpired(token));