package taewan.Smart.domain.member.service;

import taewan.Smart.domain.member.dto.MemberCertificateDto;

public interface MemberCertificationService {

    MemberCertificateDto findEmail(String email);
    MemberCertificateDto findMember(String email);
    MemberCertificateDto findMember(String email, String nickName);
}
