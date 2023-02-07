package taewan.Smart.domain.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.repository.MemberRepository;

import static taewan.Smart.global.error.ExceptionStatus.DUPLICATE_MEMBER_ID;
import static taewan.Smart.global.error.ExceptionStatus.MEMBER_NOT_FOUND;


@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberInfoDto findOne(Long id) {
        return new MemberInfoDto(memberRepository.findById(id)
                .orElseThrow(MEMBER_NOT_FOUND::exception));
    }

    public MemberInfoDto findOne(String memberId, String password) {
        return new MemberInfoDto(memberRepository.findByMemberIdAndPassword(memberId, password)
                .orElseThrow(MEMBER_NOT_FOUND::exception));
    }

    @Transactional
    @Override
    public Long save(MemberSaveDto memberSaveDto) {
        if (memberRepository.findByMemberId(memberSaveDto.getMemberId()).isPresent()) {
            throw DUPLICATE_MEMBER_ID.exception();
        }
        return memberRepository.save(new Member(memberSaveDto)).getId();
    }

    @Transactional
    @Override
    public Long modify(MemberUpdateDto memberUpdateDto, Long id) {
        if (memberRepository.findByMemberId(memberUpdateDto.getMemberId()).isPresent()) {
            throw DUPLICATE_MEMBER_ID.exception();
        }

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
