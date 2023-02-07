package taewan.Smart.domain.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.service.MemberService;
import taewan.Smart.domain.member.dto.AuthInfoDto;

import static taewan.Smart.global.util.JwtUtils.createJwt;
import static taewan.Smart.global.util.JwtUtils.createRefreshJwt;

@RestController
@RequestMapping("members")
public class AuthController {

    private final MemberService memberService;

    @Autowired
    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public AuthInfoDto login(@RequestParam String memberId, @RequestParam String password) {
        MemberInfoDto dto = memberService.findOne(memberId, password);
        return new AuthInfoDto(dto.getMemberId(), createJwt(dto), createRefreshJwt(dto.getId()));
    }

    @PostMapping("/logout")
    public void logout() {
    }
}
