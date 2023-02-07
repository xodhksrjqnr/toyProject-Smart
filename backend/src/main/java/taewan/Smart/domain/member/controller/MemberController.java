package taewan.Smart.domain.member.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;
import taewan.Smart.domain.member.service.MemberService;
import taewan.Smart.domain.member.dto.AuthInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        Long id = (Long)parseJwt(getJwt(request, "loginToken")).get("id");
        return memberService.findOne(id);
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void join(@Valid MemberSaveDto memberSaveDto) {
        memberService.save(memberSaveDto);
    }

    @PostMapping("/update")
    public AuthInfoDto update(HttpServletRequest request, HttpServletResponse response,
                              @Valid MemberUpdateDto memberUpdateDto) {
        Claims memberInfo = parseJwt(getJwt(request, "loginToken"));
        Long id = (Long)memberInfo.get("id");
        memberService.modify(memberUpdateDto, id);
        return new AuthInfoDto((String)memberInfo.get("memberId"), createJwt(memberService.findOne(id)),
                createRefreshJwt(id));
    }

    @PostMapping("/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(HttpServletRequest request) {
        Long id = (Long)parseJwt(getJwt(request, "loginToken")).get("id");
        memberService.delete(id);
    }

    @PostMapping("/refresh")
    public AuthInfoDto refresh(HttpServletRequest request, HttpServletResponse response) {
        Long id = (Long)parseJwt(getJwt(request, "refreshToken")).get("id");
        MemberInfoDto dto = memberService.findOne(id);
        return new AuthInfoDto(dto.getId().toString(), createJwt(dto), createRefreshJwt(id));
    }
}
