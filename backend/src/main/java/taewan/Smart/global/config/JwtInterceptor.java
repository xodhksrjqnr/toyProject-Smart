package taewan.Smart.global.config;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import taewan.Smart.domain.member.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static taewan.Smart.global.error.ExceptionStatus.JWT_INVALID;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(request.getMethod().equals("OPTIONS"))) {
            if (!memberRepository.existsById(Long.valueOf(String.valueOf(request.getAttribute("tokenMemberId"))))) {
                throw JWT_INVALID.exception();
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
