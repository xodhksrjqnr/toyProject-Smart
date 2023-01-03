package taewan.Smart.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taewan.Smart.member.dto.MemberInfoDto;
import taewan.Smart.member.dto.MemberSaveDto;
import taewan.Smart.member.dto.MemberUpdateDto;
import taewan.Smart.member.entity.Member;
import taewan.Smart.member.repository.MemberRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberInfoDto findOne(Long memberId) {
        return new MemberInfoDto(memberRepository.findById(memberId).orElseThrow());
    }

    @Override
    public List<MemberInfoDto> findAll() {
        return Arrays.asList(memberRepository.findAll()
                .stream().map(MemberInfoDto::new)
                .toArray(MemberInfoDto[]::new));
    }

    @Override
    public Long save(MemberSaveDto memberSaveDto) {
        return memberRepository.save(new Member(memberSaveDto)).getMemberId();
    }

    @Override
    public Long modify(Long memberId, MemberUpdateDto memberUpdateDto) {
        Member found = memberRepository.findById(memberId).orElseThrow();

        found.updateMember(memberUpdateDto);
        return memberId;
    }

    @Override
    public void delete(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
