package taewan.Smart.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.message.AuthException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice("taewan.Smart.login.controller")
public class LoginExController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResult NoSuchElementExHandle(NoSuchElementException e) {
        log.error("[LoginErrorHandle]", e);
        return new ErrorResult("404", "아이디 또는 비밀번호가 틀렸습니다.", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthException.class, ExpiredJwtException.class})
    public ErrorResult AuthExHandle(Exception e) {
        log.error("[LoginErrorHandle]", e);
        return new ErrorResult("401", "자동 로그아웃되었습니다.", "[DetailErrorMessage:loginToken이 만료되었습니다.]");
    }
}
