package taewan.Smart.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.member.dto.AuthInfoDto;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;
import taewan.Smart.domain.member.service.MemberService;

import javax.validation.Valid;

import static taewan.Smart.global.utils.JwtUtil.createJwt;
import static taewan.Smart.global.utils.JwtUtil.parseJwt;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public MemberInfoDto search(@RequestHeader("Authorization") String token) {
        Long memberId = Long.valueOf((Integer)parseJwt(token).get("memberId"));

        return memberService.findOne(memberId);
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void join(@Valid MemberSaveDto dto) {
        memberService.save(dto);
    }

    @PostMapping("/update")
    public AuthInfoDto modify(@RequestHeader("Authorization") String token, @Valid MemberUpdateDto dto) {
        MemberInfoDto updated = memberService.update(dto);

        return new AuthInfoDto(dto.getNickName(), createJwt(updated.toClaimsMap()), createJwt(updated.toClaimMap()));
    }

    @PostMapping("/delete")
    public void remove(@RequestHeader("Authorization") String token) {
        Long memberId = Long.valueOf((Integer)parseJwt(token).get("memberId"));

        memberService.delete(memberId);
    }

    @PostMapping("/refresh")
    public AuthInfoDto refresh(@RequestHeader("Authorization") String token) {
        Long memberId = Long.valueOf((Integer)parseJwt(token).get("memberId"));
        MemberInfoDto dto = memberService.findOne(memberId);

        return new AuthInfoDto(dto.getNickName(), createJwt(dto.toClaimsMap()), createJwt(dto.toClaimMap()));
    }
}
