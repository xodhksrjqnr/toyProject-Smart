package taewan.Smart.global.error;

import io.jsonwebtoken.JwtException;
import org.springframework.dao.DuplicateKeyException;
import taewan.Smart.domain.member.exception.ExpiredTokenException;
import taewan.Smart.global.exception.PageNotFoundException;

import java.util.NoSuchElementException;

public enum ExceptionStatus {

    //Member Domain Status
    MEMBER_NOT_FOUND(new NoSuchElementException("존재하지 않는 회원입니다.")),
    MEMBER_ID_DUPLICATE(new DuplicateKeyException("중복된 회원 아이디입니다.")),
    MEMBER_EMAIL_NOT_FOUND(new NoSuchElementException("가입되지 않은 이메일입니다.")),
    MEMBER_EMAIL_DUPLICATE(new DuplicateKeyException("이미 가입된 이메일입니다.")),

    //JWT
    JWT_EXPIRED(new ExpiredTokenException("JWT가 만료되었습니다.")),
    JWT_INVALID(new JwtException("JWT의 내용이 잘못되었습니다.")),
    JWT_ISNULL(new JwtException("JWT가 존재하지 않습니다.")),

    //Product Domain Status
    PRODUCT_NOT_FOUND(new NoSuchElementException("존재하지 않거나 삭제된 제품입니다.")),
    PRODUCT_NAME_DUPLICATE(new DuplicateKeyException("중복된 제품 이름입니다.")),
    PRODUCT_IMAGE_EMPTY(new IllegalArgumentException("등록할 이미지가 필요합니다.")),
    PRODUCT_IMG_NOT_FOUND(new NoSuchElementException("존재하지 않거나 삭제된 이미지입니다.")),

    //Order Domain Status
    ORDER_NOT_FOUND(new NoSuchElementException("존재하지 않는 주문입니다.")),
    ORDER_ITEM_NOT_FOUND(new NoSuchElementException("존재하지 않는 주문 아이템입니다.")),

    //Global Status
    MAIL_INVALID(new NoSuchElementException("유효하지 않은 이메일입니다.")),
    PAGE_NOT_FOUND(new PageNotFoundException("유효하지 않은 api 호출입니다."));

    private final RuntimeException exception;

    ExceptionStatus(RuntimeException exception) {
        this.exception = exception;
    }

    public RuntimeException exception() {
        return exception;
    }
}
