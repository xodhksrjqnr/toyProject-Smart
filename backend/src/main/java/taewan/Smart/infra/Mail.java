package taewan.Smart.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class Mail {

    private final JavaMailSender javaMailSender;

    @Autowired
    public Mail(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        try{
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("[Smart] 회원가입 이메일 인증 안내 메일입니다.");
            simpleMailMessage.setText("http://121.180.239.248:3000/signup");
            javaMailSender.send(simpleMailMessage);
        } catch(Exception e){
            log.info("[회원 가입 이메일 인증 메일 전송] : {}", email + " " + LocalDateTime.now());
        }
    }
}
