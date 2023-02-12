package taewan.Smart.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import taewan.Smart.domain.member.dto.MemberCertificateDto;

import java.time.LocalDateTime;

import static taewan.Smart.global.error.ExceptionStatus.MAIL_INVALID;

@Slf4j
@Component
public class Mail {

    private final JavaMailSender javaMailSender;

    @Autowired
    public Mail(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(MemberCertificateDto dto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        try {
            simpleMailMessage.setTo(dto.getEmail());
            simpleMailMessage.setSubject(dto.getMessage());
            simpleMailMessage.setText(dto.getText());
            javaMailSender.send(simpleMailMessage);
        } catch (MailAuthenticationException | MailSendException e) {
            log.info("[회원 가입 인증 메일 전송 실패] : {}", dto.getEmail() + " " + LocalDateTime.now());
            throw MAIL_INVALID.exception();
        }
        log.info("[회원 가입 인증 메일 전송 성공] : {}", dto.getEmail() + " " + LocalDateTime.now());
    }
}
