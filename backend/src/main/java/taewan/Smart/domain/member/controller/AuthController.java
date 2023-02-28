package taewan.Smart.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.member.dto.AuthInfoDto;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.service.MemberCertificationService;
import taewan.Smart.domain.member.service.MemberService;
import taewan.Smart.infra.Mail;

import static taewan.Smart.global.util.JwtUtils.createJwt;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class AuthController {

    private final MemberCertificationService memberCertificationService;
    private final MemberService memberService;
    private final Mail mail;

    @PostMapping("/login")
    public AuthInfoDto login(@RequestParam String nickName, @RequestParam String password) {
        MemberInfoDto dto = memberService.findOne(nickName, password);

        return new AuthInfoDto(dto.getNickName(), createJwt(dto.toClaimsMap()), createJwt(dto.toClaimMap()));
    }

    @PostMapping("/logout")
    public void logout() {
    }

    @PostMapping("/certificate/email")
    public void certificateEmail(@ModelAttribute("email") String email) {
        mail.sendMail(memberCertificationService.findEmail(email));
    }

    @PostMapping("/certificate/nickname")
    public void certificateMemberId(@ModelAttribute("email") String email) {
        mail.sendMail(memberCertificationService.findMember(email));
    }

    @PostMapping("/certificate/password")
    public void certificatePassword(@ModelAttribute("email") String email, @ModelAttribute("nickName") String nickName) {
        mail.sendMail(memberCertificationService.findMember(email, nickName));
    }
}
