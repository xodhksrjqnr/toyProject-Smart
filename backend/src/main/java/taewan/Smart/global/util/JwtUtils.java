package taewan.Smart.global.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import taewan.Smart.domain.member.dto.MemberInfoDto;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;

import static taewan.Smart.global.error.ExceptionStatus.JWT_EXPIRED;
import static taewan.Smart.global.error.ExceptionStatus.JWT_INVALID;

public class JwtUtils {

    private static String SECRET_KEY = "smartSecretKey";

    public static String createJwt(MemberInfoDto dto) {
        Date now = new Date();
        String jwt = Jwts.builder()
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

        return jwt;
    }

    public static String createRefreshJwt(MemberInfoDto dto) {
        Date now = new Date();
        String id = Long.toString(dto.getId());
        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("smart")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofHours(2).toMillis()))
                .claim("id", id)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return jwt;
    }

    public static Claims parseJwt(HttpServletRequest request) {
        try {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION)
                    .replace("Bearer ", "");

            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e1) {
            throw JWT_EXPIRED.exception();
        } catch (JwtException | IllegalArgumentException | NullPointerException e2) {
            throw JWT_INVALID.exception();
        }
    }
}
