package taewan.Smart.domain.member.service;

import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;

public interface MemberService {

    MemberInfoDto findOne(Long id);
    MemberInfoDto findOne(String email);
    MemberInfoDto findOne(String memberId, String password);
    Long save(MemberSaveDto memberSaveDto);
    Long update(MemberUpdateDto memberUpdateDto);
    void delete(Long id);
}
