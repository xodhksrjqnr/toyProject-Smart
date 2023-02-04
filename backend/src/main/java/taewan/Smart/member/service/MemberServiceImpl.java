package taewan.Smart.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.member.dto.MemberInfoDto;
import taewan.Smart.member.dto.MemberSaveDto;
import taewan.Smart.member.dto.MemberUpdateDto;
import taewan.Smart.member.entity.Member;
import taewan.Smart.member.repository.MemberRepository;


@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberInfoDto findOne(Long id) {
        return new MemberInfoDto(memberRepository.findById(id).orElseThrow());
    }

    @Transactional
    @Override
    public Long save(MemberSaveDto memberSaveDto) {
        if (memberRepository.findByMemberId(memberSaveDto.getMemberId()).isPresent())
            throw new DuplicateKeyException("[DetailErrorMessage:중복된 회원 아이디입니다.]");
        return memberRepository.save(new Member(memberSaveDto)).getId();
    }

    @Transactional
    @Override
    public Long modify(MemberUpdateDto memberUpdateDto, Long id) {
        if (memberRepository.findByMemberId(memberUpdateDto.getMemberId()).isPresent())
            throw new DuplicateKeyException("[DetailErrorMessage:중복된 회원 아이디입니다.]");

        Member found = memberRepository.findById(id).orElseThrow();

        if (memberUpdateDto.getPassword().equals(found.getPassword()))
            memberUpdateDto.setPassword(found.getPassword());
        found.updateMember(memberUpdateDto);
        return found.getId();
    }

    @Override
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
