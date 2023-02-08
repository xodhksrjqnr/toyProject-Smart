package taewan.Smart.domain.member.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.member.dto.AuthInfoDto;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;
import taewan.Smart.domain.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static taewan.Smart.global.util.JwtUtils.*;

@RestController
@RequestMapping("members")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public MemberInfoDto search(HttpServletRequest request) {
        Claims loginToken = parseJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
        Long id = Long.parseLong((String)loginToken.get("id"));
        return memberService.findOne(id);
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void join(@Valid MemberSaveDto memberSaveDto) {
        memberService.save(memberSaveDto);
    }

    @PostMapping("/update")
    public AuthInfoDto modify(HttpServletRequest request, @Valid MemberUpdateDto memberUpdateDto) {
        Claims loginToken = parseJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
        Long id = Long.parseLong((String)loginToken.get("id"));
        memberService.update(memberUpdateDto);

        return new AuthInfoDto((String)loginToken.get("memberId"), createJwt(memberService.findOne(id)),
                createRefreshJwt(new MemberInfoDto(memberUpdateDto)));
    }

    @PostMapping("/delete")
    public void remove(HttpServletRequest request) {
        Claims loginToken = parseJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
        Long id = Long.parseLong((String)loginToken.get("id"));
        memberService.delete(id);
    }

    @PostMapping("/refresh")
    public AuthInfoDto refresh(HttpServletRequest request) {
        Claims refreshToken = parseJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
        Long id = Long.parseLong((String)refreshToken.get("id"));
        MemberInfoDto dto = memberService.findOne(id);

        return new AuthInfoDto(dto.getMemberId(), createJwt(dto), createRefreshJwt(dto));
    }
}
