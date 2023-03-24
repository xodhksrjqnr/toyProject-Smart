package taewan.Smart.fixture;

import io.jsonwebtoken.JwtException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.validation.BindException;
import taewan.Smart.global.exception.AuthAccessException;
import taewan.Smart.global.exception.ForeignKeyException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionTestFixture {
    public static ResultMatcher isBindException() {
        return result -> assertEquals(
                result.getResolvedException().getClass(),
                BindException.class
        );
    }

    public static ResultMatcher isDuplicateKeyException() {
        return result -> assertEquals(
                result.getResolvedException().getClass(),
                DuplicateKeyException.class
        );
    }

    public static ResultMatcher isNoSuchElementException() {
        return result -> assertEquals(
                result.getResolvedException().getClass(),
                NoSuchElementException.class
        );
    }

    public static ResultMatcher isJwtException() {
        return result -> assertEquals(
                result.getResolvedException().getClass(),
                JwtException.class
        );
    }

    public static ResultMatcher isAuthAccessException() {
        return result -> assertEquals(
                result.getResolvedException().getClass(),
                AuthAccessException.class
        );
    }

    public static ResultMatcher isIllegalArgumentException() {
        return result -> assertEquals(
                result.getResolvedException().getClass(),
                IllegalArgumentException.class
        );
    }

    public static ResultMatcher isForeignKeyException() {
        return result -> assertEquals(
                result.getResolvedException().getClass(),
                ForeignKeyException.class
        );
    }
}
