package taewan.Smart.member.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.login.dto.LoginInfoDto;
import taewan.Smart.member.dto.MemberInfoDto;
import taewan.Smart.member.dto.MemberSaveDto;
import taewan.Smart.member.dto.MemberUpdateDto;
import taewan.Smart.member.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static taewan.Smart.util.CookieUtils.expireCookie;
import static taewan.Smart.util.JwtUtils.createJwt;
import static taewan.Smart.util.JwtUtils.parseJwt;

@RestController
@RequestMapping("members")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public MemberInfoDto search(@CookieValue String loginToken) {
        Long id = (Long)parseJwt(loginToken).get("id");
        return memberService.findOne(id);
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void join(@Valid MemberSaveDto memberSaveDto) {
        memberService.save(memberSaveDto);
    }

    @PostMapping("/update")
    @ResponseStatus(value = HttpStatus.OK)
    public LoginInfoDto update(@CookieValue String loginToken, @Valid MemberUpdateDto memberUpdateDto,
                               HttpServletRequest request, HttpServletResponse response) {
        Claims memberInfo = parseJwt(loginToken);
        Long id = (Long)memberInfo.get("id");
        memberService.modify(memberUpdateDto, id);
        expireCookie(request, response, "loginToken");
        return new LoginInfoDto((String)memberInfo.get("memberId"), createJwt(memberService.findOne(id)));
    }

    @PostMapping("/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@CookieValue String loginToken) {
        Long id = (Long)parseJwt(loginToken).get("id");
        memberService.delete(id);
    }
}
