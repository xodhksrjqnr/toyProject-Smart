package taewan.Smart.global.utils;

import io.jsonwebtoken.*;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static taewan.Smart.global.error.ExceptionStatus.*;
import static taewan.Smart.global.utils.PropertyUtil.SECRET_KEY;

public class JwtUtil {
    public static String createJwt(Map<String, Object> claims) {
        Date now = new Date();
        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("smart")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()))
                .addClaims(claims)
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
