package taewan.Smart.global.error;

import org.springframework.dao.DuplicateKeyException;
import taewan.Smart.domain.member.exception.ExpiredTokenException;

import java.util.NoSuchElementException;

public enum ExceptionStatus {
    MEMBER_NOT_FOUND(new NoSuchElementException("존재하지 않는 회원입니다.")),

    DUPLICATE_MEMBER_ID(new DuplicateKeyException("중복된 회원 아이디입니다.")),

    PRODUCT_NOT_FOUND(new NoSuchElementException("존재하지 않거나 삭제된 제품입니다.")),

    DUPLICATE_PRODUCT_NAME(new DuplicateKeyException("중복된 제품 이름입니다.")),

    EXPIRED_LOGIN_JWT(new ExpiredTokenException("LoginToken이 만료되었습니다.")),

    EXPIRED_REFRESH_JWT(new ExpiredTokenException("RefreshToken이 만료되었습니다."));

    private final RuntimeException exception;

    ExceptionStatus(RuntimeException exception) {
        this.exception = exception;
    }

    public RuntimeException exception() {
        return exception;
    }
}
