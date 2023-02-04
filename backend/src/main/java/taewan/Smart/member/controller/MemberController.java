package taewan.Smart.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.member.dto.MemberInfoDto;
import taewan.Smart.member.dto.MemberSaveDto;
import taewan.Smart.member.dto.MemberSimpleDto;
import taewan.Smart.member.dto.MemberUpdateDto;
import taewan.Smart.member.service.MemberService;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@RestController
@RequestMapping("members")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public MemberInfoDto search(@SessionAttribute("memberSession") MemberSimpleDto memberSimpleDto) throws AuthException {
        if (memberSimpleDto == null)
            throw new AuthException("[DetailErrorMessage:세션이 만료되었습니다.]");
        return memberService.findOne(memberSimpleDto.getId());
    }

    @PostMapping
    public ResponseEntity join(@Valid MemberSaveDto memberSaveDto) {
        memberService.save(memberSaveDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity update(@Valid MemberUpdateDto memberUpdateDto,
                         @SessionAttribute("memberSession") MemberSimpleDto memberSimpleDto) throws AuthException {
        if (memberSimpleDto == null)
            throw new AuthException("[DetailErrorMessage:세션이 만료되었습니다.]");
        memberService.modify(memberUpdateDto, memberSimpleDto.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@SessionAttribute("memberSession") MemberSimpleDto memberSimpleDto) throws AuthException {
        if (memberSimpleDto == null)
            throw new AuthException("[DetailErrorMessage:세션이 만료되었습니다.]");
        memberService.delete(memberSimpleDto.getId());
        return new ResponseEntity(HttpStatus.OK);
    }
}
