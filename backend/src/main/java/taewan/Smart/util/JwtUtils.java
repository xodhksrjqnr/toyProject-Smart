package taewan.Smart.util;

import io.jsonwebtoken.*;
import taewan.Smart.member.dto.MemberInfoDto;

import java.time.Duration;
import java.util.Date;

public class JwtUtils {

    private static final String SECRET_KEY = "smartSecretKey";

    public static String createJwt(MemberInfoDto dto) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("smart")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()))
                .claim("id", dto.getId())
                .claim("memberId", dto.getMemberId())
                .claim("email", dto.getEmail())
                .claim("phoneNumber", dto.getPhoneNumber())
                .claim("birthday", dto.getBirthday())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String createRefreshJwt(Long id) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("smart")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofHours(2).toMillis()))
                .claim("id", id)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims parseJwt(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
