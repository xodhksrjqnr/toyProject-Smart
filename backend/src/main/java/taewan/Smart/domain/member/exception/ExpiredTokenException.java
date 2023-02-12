package taewan.Smart.domain.member.exception;

import io.jsonwebtoken.JwtException;

public class ExpiredTokenException extends JwtException {
    public ExpiredTokenException(String message) {
        super(message);
    }

    public ExpiredTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
