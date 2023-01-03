package taewan.Smart.member.service;

import taewan.Smart.member.dto.MemberInfoDto;
import taewan.Smart.member.dto.MemberSaveDto;
import taewan.Smart.member.dto.MemberUpdateDto;

import java.util.List;

public interface MemberService {

    MemberInfoDto findOne(Long memberId);
    List<MemberInfoDto> findAll();
    Long save(MemberSaveDto memberSaveDto);
    Long modify(Long memberId, MemberUpdateDto memberUpdateDto);
    void delete(Long memberId);
}
