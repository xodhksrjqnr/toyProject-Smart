package taewan.Smart.global.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import taewan.Smart.domain.member.dto.MemberInfoDto;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static taewan.Smart.global.error.ExceptionStatus.*;

@Component
public class JwtUtils {

    private static String SECRET_KEY;

    @Value("${jwt.secret.key}")
    public void setSecretKey(String key) {
        SECRET_KEY = key;
    }

    public static String createJwt(MemberInfoDto dto) {
        Date now = new Date();
        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("smart")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()))
                .claim("memberId", Long.toString(dto.getMemberId()))
                .claim("nickName", dto.getMemberId())
                .claim("email", dto.getEmail())
                .claim("phoneNumber", (dto.getPhoneNumber() == null ? "" : dto.getPhoneNumber()))
                .claim("birthday", dto.getBirthday() == null ? "" : dto.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return jwt;
    }

    public static String createRefreshJwt(MemberInfoDto dto) {
        Date now = new Date();
        String id = Long.toString(dto.getMemberId());
        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("smart")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofHours(2).toMillis()))
                .claim("memberId", id)
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
        } catch (JwtException | IllegalArgumentException e2) {
            throw JWT_INVALID.exception();
        } catch (NullPointerException e3) {
            throw JWT_ISNULL.exception();
        }
    }
}
