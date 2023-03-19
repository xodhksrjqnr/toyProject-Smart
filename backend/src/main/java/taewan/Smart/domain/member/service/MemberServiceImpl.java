package taewan.Smart.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.repository.MemberRepository;

import static taewan.Smart.global.error.ExceptionStatus.*;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberInfoDto findOne(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(MEMBER_NOT_FOUND::exception)
                .toInfoDto();
    }

    public MemberInfoDto findOne(String email) {
        return memberRepository
                .findByEmail(email)
                .orElseThrow(MEMBER_EMAIL_NOT_FOUND::exception)
                .toInfoDto();
    }

    public MemberInfoDto findOne(String nickName, String password) {
        return memberRepository
                .findByNickNameAndPassword(nickName, password)
                .orElseThrow(MEMBER_NOT_FOUND::exception)
                .toInfoDto();
    }

    @Transactional
    @Override
    public Long save(MemberSaveDto dto) {
        memberRepository.findByNickName(dto.getNickName())
                .ifPresent(m -> {throw MEMBER_NICKNAME_DUPLICATE.exception();});
        return memberRepository.save(dto.toEntity()).getMemberId();
    }

    @Transactional
    @Override
    public MemberInfoDto update(MemberUpdateDto dto) {
        Member found = memberRepository.findById(dto.getMemberId())
                .orElseThrow(MEMBER_NOT_FOUND::exception);

        memberRepository.findByNickName(dto.getNickName())
                .ifPresent(m -> {throw MEMBER_NICKNAME_DUPLICATE.exception();});
        found.updateMember(dto);
        return found.toInfoDto();
    }

    @Transactional
    @Override
    public void delete(Long memberId) {
        try {
            memberRepository.deleteById(memberId);
        } catch (EmptyResultDataAccessException ex) {
            throw MEMBER_NOT_FOUND.exception();
        }
    }
}
