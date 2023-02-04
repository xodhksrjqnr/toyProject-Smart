package taewan.Smart.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import taewan.Smart.member.dto.MemberSimpleDto;
import taewan.Smart.member.entity.Member;
import taewan.Smart.member.repository.MemberRepository;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@RestController
public class LoginController {

    private final MemberRepository memberRepository;

    @Autowired
    public LoginController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam String memberId, @RequestParam String password,
                                HttpServletRequest request) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NoSuchElementException("[DetailErrorMessage:아이디가 존재하지 않습니다.]"));

        if (member.getPassword().equals(password))
            throw new NoSuchElementException("[DetailErrorMessage:비밀번호가 틀렸습니다.]");

        HttpSession session = request.getSession();
        session.setAttribute("memberSession", new MemberSimpleDto(member.getId(), memberId));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@SessionAttribute(name = "memberSession", required = false) MemberSimpleDto memberSimpleDto,
                                 HttpServletRequest request) throws AuthException {
        if (memberSimpleDto == null)
            throw new AuthException("[DetailErrorMessage:세션이 만료되었습니다.]");
        HttpSession session = request.getSession();
        session.removeAttribute("memberSession");
        session.invalidate();
        return new ResponseEntity(HttpStatus.OK);
    }
}
