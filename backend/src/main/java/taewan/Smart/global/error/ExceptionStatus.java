package taewan.Smart.global.error;

import io.jsonwebtoken.JwtException;
import org.springframework.dao.DuplicateKeyException;
import taewan.Smart.domain.member.exception.ExpiredTokenException;
import taewan.Smart.global.exception.AuthAccessException;
import taewan.Smart.global.exception.ForeignKeyException;
import taewan.Smart.global.exception.PageNotFoundException;

import javax.security.auth.message.AuthException;
import java.util.NoSuchElementException;

public enum ExceptionStatus {

    //Member Domain Status
    MEMBER_NOT_FOUND(new NoSuchElementException("존재하지 않는 회원입니다.")),
    MEMBER_NICKNAME_DUPLICATE(new DuplicateKeyException("중복된 회원 아이디입니다.")),
    MEMBER_EMAIL_NOT_FOUND(new NoSuchElementException("가입되지 않은 이메일입니다.")),
    MEMBER_EMAIL_DUPLICATE(new DuplicateKeyException("이미 가입된 이메일입니다.")),
    MEMBER_PASSWORD_INVALID(new IllegalArgumentException("비밀번호 형식이 유효하지 않습니다.")),

    //Auth Status
    JWT_EXPIRED(new ExpiredTokenException("JWT가 만료되었습니다.")),
    JWT_INVALID(new JwtException("JWT의 내용이 잘못되었습니다.")),
    JWT_ISNULL(new JwtException("JWT가 존재하지 않습니다.")),
    DATA_FALSIFICATION(new AuthAccessException("권한이 없는 데이터를 수정하려고 합니다.")),

    //Category Domain Status
    CATEGORY_NAME_DUPLICATE(new DuplicateKeyException("중복된 카테고리명입니다.")),

    //Product Domain Status
    PRODUCT_NOT_FOUND(new NoSuchElementException("존재하지 않거나 삭제된 제품입니다.")),
    PRODUCT_NAME_DUPLICATE(new DuplicateKeyException("중복된 제품명입니다.")),
    PRODUCT_IMAGE_EMPTY(new IllegalArgumentException("등록할 이미지가 필요합니다.")),
    PRODUCT_REFERRED(new ForeignKeyException("해당 제품과 관련된 주문이 존재합니다.")),

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
