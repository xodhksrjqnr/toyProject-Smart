package taewan.Smart.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

    public static Claims parseJwt(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
