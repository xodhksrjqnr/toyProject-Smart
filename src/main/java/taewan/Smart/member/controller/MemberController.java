package taewan.Smart.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.member.dto.MemberInfoDto;
import taewan.Smart.member.dto.MemberSaveDto;
import taewan.Smart.member.dto.MemberUpdateDto;
import taewan.Smart.member.service.MemberService;

import java.util.List;

@Controller
@RequestMapping("member")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{memberId}")
    public String theMemberPage(@PathVariable Long memberId) {
        MemberInfoDto member = memberService.findOne(memberId);
        return "";
    }

    @GetMapping
    public String membersPage() {
        List<MemberInfoDto> members = memberService.findAll();
        return "";
    }

    @PostMapping
    public String memberUpload(MemberSaveDto dto) {
        Long memberId = memberService.save(dto);
        return "";
    }

    @PostMapping("/{memberId}")
    public String memberUpdate(@PathVariable Long memberId, MemberUpdateDto dto) {
        memberService.modify(memberId, dto);
        return "";
    }

    @DeleteMapping("/{memberId}")
    public String memberDelete(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return "";
    }
}
