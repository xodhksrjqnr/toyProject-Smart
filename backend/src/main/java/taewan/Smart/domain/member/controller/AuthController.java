package taewan.Smart.domain.member.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.member.dto.AuthInfoDto;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.service.MemberCertificationService;
import taewan.Smart.domain.member.service.MemberService;
import taewan.Smart.infra.Mail;

import static taewan.Smart.global.util.JwtUtils.createJwt;
import static taewan.Smart.global.util.JwtUtils.createRefreshJwt;

@Slf4j
@RestController
@RequestMapping("members")
public class AuthController {

    private final MemberCertificationService memberCertificationService;
    private final MemberService memberService;
    private final Mail mail;

    @Autowired
    public AuthController(MemberCertificationService memberCertificationService, MemberService memberService, Mail mail) {
        this.memberCertificationService = memberCertificationService;
        this.memberService = memberService;
        this.mail = mail;
    }

    @PostMapping("/login")
    public AuthInfoDto login(@RequestParam String memberId, @RequestParam String password) {
        MemberInfoDto dto = memberService.findOne(memberId, password);
        return new AuthInfoDto(dto.getMemberId(), createJwt(dto), createRefreshJwt(dto));
    }

    @PostMapping("/logout")
    public void logout() {
    }

    @PostMapping("/certificate/email")
    public void certificateEmail(@ModelAttribute("email") String email) {
        mail.sendMail(memberCertificationService.findEmail(email));
    }

    @PostMapping("/certificate/memberId")
    public void certificateMemberId(@ModelAttribute("email") String email) {
        mail.sendMail(memberCertificationService.findMember(email));
    }

    @PostMapping("/certificate/password")
    public void certificatePassword(@ModelAttribute("email") String email, @ModelAttribute("memberId") String memberId) {
        mail.sendMail(memberCertificationService.findMember(email, memberId));
    }
}
