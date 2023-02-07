package taewan.Smart.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.login.dto.LoginInfoDto;
import taewan.Smart.member.dto.MemberInfoDto;
import taewan.Smart.member.service.MemberService;

import static taewan.Smart.util.JwtUtils.createJwt;
import static taewan.Smart.util.JwtUtils.createRefreshJwt;

@RestController
public class LoginController {

    private final MemberService memberService;

    @Autowired
    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public LoginInfoDto login(@RequestParam String memberId, @RequestParam String password) {
        MemberInfoDto dto = memberService.findOne(memberId, password);
        return new LoginInfoDto(dto.getMemberId(), createJwt(dto), createRefreshJwt(dto.getId()));
    }

    @PostMapping("/logout")
    @ResponseStatus(value = HttpStatus.OK)
    public void logout() {
    }
}
