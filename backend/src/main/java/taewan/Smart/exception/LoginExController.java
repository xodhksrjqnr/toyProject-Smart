package taewan.Smart.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
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
        return new ErrorResult("404", "입력된 정보가 잘못되었습니다.", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    public ErrorResult AuthExHandle(AuthException e) {
        log.error("[LoginErrorHandle]", e);
        return new ErrorResult("401", "세션이 만료되었습니다.", "");
    }
}
