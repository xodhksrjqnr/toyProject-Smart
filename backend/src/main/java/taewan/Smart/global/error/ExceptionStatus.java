package taewan.Smart.global.error;

import org.springframework.dao.DuplicateKeyException;
import taewan.Smart.domain.member.exception.ExpiredTokenException;

import java.util.NoSuchElementException;

public enum ExceptionStatus {
    MEMBER_NOT_FOUND(new NoSuchElementException("존재하지 않는 회원입니다.")),

    MEMBER_ID_DUPLICATE(new DuplicateKeyException("중복된 회원 아이디입니다.")),

    PRODUCT_NOT_FOUND(new NoSuchElementException("존재하지 않거나 삭제된 제품입니다.")),

    PRODUCT_NAME_DUPLICATE(new DuplicateKeyException("중복된 제품 이름입니다.")),

    PRODUCT_IMAGE_EMPTY(new IllegalArgumentException("등록할 이미지가 필요합니다.")),

    LOGIN_JWT_EXPIRED(new ExpiredTokenException("LoginToken이 만료되었습니다.")),

    REFRESH_JWT_EXPIRED(new ExpiredTokenException("RefreshToken이 만료되었습니다."));

    private final RuntimeException exception;

    ExceptionStatus(RuntimeException exception) {
        this.exception = exception;
    }

    public RuntimeException exception() {
        return exception;
    }
}
