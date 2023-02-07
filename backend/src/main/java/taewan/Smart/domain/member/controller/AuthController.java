package taewan.Smart.domain.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.service.MemberService;
import taewan.Smart.domain.member.dto.AuthInfoDto;
import taewan.Smart.infra.Mail;

import javax.validation.constraints.Email;

import static taewan.Smart.global.util.JwtUtils.createJwt;
import static taewan.Smart.global.util.JwtUtils.createRefreshJwt;

@RestController
@RequestMapping("members")
public class AuthController {

    private final MemberService memberService;
    private final Mail mail;

    @Autowired
    public AuthController(MemberService memberService, Mail mail) {
        this.memberService = memberService;
        this.mail = mail;
    }

    @PostMapping("/login")
    public AuthInfoDto login(@RequestParam String memberId, @RequestParam String password) {
        MemberInfoDto dto = memberService.findOne(memberId, password);
        return new AuthInfoDto(dto.getMemberId(), createJwt(dto), createRefreshJwt(dto.getId()));
    }

    @PostMapping("/logout")
    public void logout() {
    }

    @PostMapping("/certification")
    public void certificate(@Email @RequestParam String email) {
        mail.sendMail(email);
    }
}
