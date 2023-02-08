package taewan.Smart.domain.member.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCertificateDto {

    private String email;
    private String message;
    private String text;
}
