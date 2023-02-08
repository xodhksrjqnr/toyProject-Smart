package taewan.Smart.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static taewan.Smart.global.error.ExceptionStatus.MAIL_INVALID;

@Slf4j
@Component
public class Mail {

    @Value("${client.address}")
    private String clientAddress;

    private final JavaMailSender javaMailSender;

    @Autowired
    public Mail(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("[Smart] 회원가입 이메일 인증 안내 메일입니다.");
        simpleMailMessage.setText(clientAddress + "/signup");
        javaMailSender.send(simpleMailMessage);
        log.info("[회원 가입 인증 메일 전송 성공] : {}", email + " " + LocalDateTime.now());
    }
}
