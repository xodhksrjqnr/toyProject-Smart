package taewan.Smart.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import taewan.Smart.domain.member.dto.MemberInfoDto;

import java.time.Duration;
import java.util.Date;

public class JwtUtils {

    private static final String SECRET_KEY = "smartSecretKey";

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

    public static Claims parseJwt(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
