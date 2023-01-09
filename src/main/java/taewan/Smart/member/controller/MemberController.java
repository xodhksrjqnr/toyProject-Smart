package taewan.Smart.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String searchOne(@PathVariable Long memberId, Model model) {
        model.addAttribute("member", memberService.findOne(memberId));
        return "member/view";
    }

    @GetMapping
    public String searchAll(Model model) {
        model.addAttribute("memberList", memberService.findAll());
        return "member/list_view";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("member", new MemberSaveDto());
        return "member/write";
    }

    @PostMapping
    public String upload(MemberSaveDto dto) {
        Long memberId = memberService.save(dto);
        return "redirect:/member/" + memberId;
    }

    @GetMapping("/update/{memberId}")
    public String updateForm(@PathVariable Long memberId, Model model) {
        model.addAttribute("member", memberService.findOne(memberId));
        return "member/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberUpdateDto dto) {
        Long memberId = memberService.modify(dto);
        return "redirect:/member/" + memberId;
    }

    @PostMapping("/delete/{memberId}")
    public String delete(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return "redirect:/member";
    }
}
