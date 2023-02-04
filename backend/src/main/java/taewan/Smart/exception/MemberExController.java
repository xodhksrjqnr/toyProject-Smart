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
@RestControllerAdvice("taewan.Smart.member.controller")
public class MemberExController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResult NoSuchElementExHandle(NoSuchElementException e) {
        log.error("[MemberErrorHandle]", e);
        return new ErrorResult("404", "존재하지 않는 회원입니다.", "");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, BindException.class, DuplicateKeyException.class})
    public ErrorResult InputDataExHandle(Exception e) {
        log.error("[MemberErrorHandle]", e);
        String message = e.getMessage().substring(e.getMessage().lastIndexOf("[DetailErrorMessage"));
        return new ErrorResult("400", "입력된 회원 정보가 잘못되었습니다.", message);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    public ErrorResult AuthExHandle(AuthException e) {
        log.error("[MemberErrorHandle]", e);
        return new ErrorResult("401", "세션이 만료되었습니다.", "");
    }
}
