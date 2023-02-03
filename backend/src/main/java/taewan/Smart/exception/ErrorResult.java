package taewan.Smart.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {

    private String code;
    private String message;
    private String detail;
}
