package taewan.Smart.global.config;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import taewan.Smart.domain.member.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static taewan.Smart.global.error.ExceptionStatus.JWT_INVALID;
import static taewan.Smart.global.util.JwtUtils.parseJwt;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        Claims claims = parseJwt(token);
        Long memberId = Long.valueOf(String.valueOf(claims.get("memberId")));
        String type = claims.get("type").toString();

        if (!memberRepository.existsById(memberId)) {
            throw JWT_INVALID.exception();
        }
        request.setAttribute("tokenMemberId", memberId);
        request.setAttribute("tokenType", type);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
