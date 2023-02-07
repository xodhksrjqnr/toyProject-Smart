package taewan.Smart.domain.member.service;

import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;

public interface MemberService {

    MemberInfoDto findOne(Long id);
    MemberInfoDto findOne(String memberId, String password);
    Long save(MemberSaveDto memberSaveDto);
    Long modify(MemberUpdateDto memberUpdateDto, Long id);
    void delete(Long id);
}
