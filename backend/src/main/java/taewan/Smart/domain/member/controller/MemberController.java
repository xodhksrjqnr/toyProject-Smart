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

import static taewan.Smart.global.error.ExceptionStatus.DATA_FALSIFICATION;
import static taewan.Smart.global.error.ExceptionStatus.JWT_INVALID;
import static taewan.Smart.global.util.JwtUtils.createJwt;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public MemberInfoDto search(@RequestAttribute Long tokenMemberId) {
        return memberService.findOne(tokenMemberId);
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void join(@Valid @ModelAttribute MemberSaveDto dto) {
        memberService.save(dto);
    }

    @PostMapping("/update")
    public AuthInfoDto modify(@RequestAttribute Long tokenMemberId, @Valid @ModelAttribute MemberUpdateDto dto) {
        if (!dto.getMemberId().equals(tokenMemberId)) {
            throw DATA_FALSIFICATION.exception();
        }

        MemberInfoDto updated = memberService.update(dto);

        return new AuthInfoDto(dto.getNickName(), createJwt(updated.toClaimsMap()), createJwt(updated.toClaimMap()));
    }

    @PostMapping("/delete")
    public void remove(@RequestAttribute Long tokenMemberId) {
        memberService.delete(tokenMemberId);
    }

    @PostMapping("/refresh")
    public AuthInfoDto refresh(@RequestAttribute Long tokenMemberId, @RequestAttribute String tokenType) {
        if (!tokenType.equals("refresh")) {
            throw JWT_INVALID.exception();
        }

        MemberInfoDto dto = memberService.findOne(tokenMemberId);

        return new AuthInfoDto(dto.getNickName(), createJwt(dto.toClaimsMap()), createJwt(dto.toClaimMap()));
    }
}
