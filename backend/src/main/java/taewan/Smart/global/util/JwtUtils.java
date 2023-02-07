package taewan.Smart.global.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import taewan.Smart.domain.member.dto.MemberInfoDto;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;

@Slf4j
public class JwtUtils {

    private static final String SECRET_KEY = "smartSecretKey";

    public static String createJwt(MemberInfoDto dto) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("smart")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()))
                .claim("id", Long.toString(dto.getId()))
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
                .claim("id", Long.toString(id))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims parseJwt(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getJwt(HttpServletRequest request, String tokenName) {
        String[] tokens = request.getHeader(HttpHeaders.AUTHORIZATION).split(";");

        for (String token : tokens) {
            if (token.contains(tokenName)) {
                return token.split("=")[1];
            }
        }
        return null;
    }
}
