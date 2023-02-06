package taewan.Smart.member.service;

import taewan.Smart.member.dto.MemberInfoDto;
import taewan.Smart.member.dto.MemberSaveDto;
import taewan.Smart.member.dto.MemberUpdateDto;

import java.util.List;

public interface MemberService {

    MemberInfoDto findOne(Long id);
    MemberInfoDto findOne(String memberId, String password);
    Long save(MemberSaveDto memberSaveDto);
    Long modify(MemberUpdateDto memberUpdateDto, Long id);
    void delete(Long id);
}
