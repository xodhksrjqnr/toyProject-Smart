package taewan.Smart.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice("taewan.Smart.product.controller")
public class ProductExController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResult NoSuchElementExHandle(NoSuchElementException e) {
        log.error("[ProductErrorHandle]", e);
        return new ErrorResult("404", "존재하지 않거나 삭제된 제품입니다.", "");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, BindException.class, DuplicateKeyException.class})
    public ErrorResult InputDataExHandle(Exception e) {
        log.error("[ProductErrorHandle]", e);
        String message = e.getMessage().substring(e.getMessage().lastIndexOf("[DetailErrorMessage"));
        return new ErrorResult("400", "입력된 제품의 정보가 잘못되었습니다.", message);
    }
}
