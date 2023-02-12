package taewan.Smart.domain.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.repository.MemberRepository;

import static taewan.Smart.global.error.ExceptionStatus.*;


@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberInfoDto findOne(Long memberId) {
        return new MemberInfoDto(memberRepository.findById(memberId)
                .orElseThrow(MEMBER_NOT_FOUND::exception));
    }

    public MemberInfoDto findOne(String email) {
        return new MemberInfoDto(memberRepository.findByEmail(email)
                .orElseThrow(MEMBER_EMAIL_NOT_FOUND::exception));
    }

    public MemberInfoDto findOne(String nickName, String password) {
        return new MemberInfoDto(memberRepository.findByNickNameAndPassword(nickName, password)
                .orElseThrow(MEMBER_NOT_FOUND::exception));
    }

    @Transactional
    @Override
    public Long save(MemberSaveDto memberSaveDto) {
        memberRepository.findByNickName(memberSaveDto.getNickName())
                .ifPresent(m -> {throw MEMBER_ID_DUPLICATE.exception();});
        return memberRepository.save(new Member(memberSaveDto)).getMemberId();
    }

    @Transactional
    @Override
    public Long update(MemberUpdateDto memberUpdateDto) {
        memberRepository.findByNickName(memberUpdateDto.getNickName())
                .ifPresent(m -> {throw MEMBER_ID_DUPLICATE.exception();});

        Member found = memberRepository.findById(memberUpdateDto.getMemberId()).orElseThrow();

        if (memberUpdateDto.getPassword().equals(found.getPassword()))
            memberUpdateDto.setPassword(found.getPassword());
        found.updateMember(memberUpdateDto);
        return found.getMemberId();
    }

    @Transactional
    @Override
    public void delete(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
