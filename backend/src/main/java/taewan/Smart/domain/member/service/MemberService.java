package taewan.Smart.domain.member.service;

import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;

public interface MemberService {

    MemberInfoDto findOne(Long memberId);
    MemberInfoDto findOne(String email);
    MemberInfoDto findOne(String nickName, String password);
    Long save(MemberSaveDto memberSaveDto);
    MemberInfoDto update(MemberUpdateDto memberUpdateDto);
    void delete(Long memberId);
}
