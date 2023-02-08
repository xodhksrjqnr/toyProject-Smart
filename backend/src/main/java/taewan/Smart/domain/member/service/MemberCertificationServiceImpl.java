package taewan.Smart.domain.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.member.dto.MemberCertificateDto;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.repository.MemberRepository;

import java.util.UUID;

import static taewan.Smart.global.error.ExceptionStatus.MAIL_INVALID;
import static taewan.Smart.global.error.ExceptionStatus.MEMBER_NOT_FOUND;

@Service
public class MemberCertificationServiceImpl implements MemberCertificationService {

    @Value("${client.address}")
    private String clientAddress;

    private final MemberRepository memberRepository;

    @Autowired
    public MemberCertificationServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberCertificateDto findEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MAIL_INVALID::exception);
        String message = "[Smart] 회원가입 이메일 인증 안내 메일입니다.";
        String text = clientAddress + "signup";

        return new MemberCertificateDto(email, message, text);
    }

    @Override
    public MemberCertificateDto findMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MAIL_INVALID::exception);
        String message = "[Smart] 회원 아이디 찾기 안내 메일입니다.";
        String text = "회원님의 아이디는 \"" + member.getMemberId() + "\"입니다.";

        return new MemberCertificateDto(email, message, text);
    }

    @Transactional
    @Override
    public MemberCertificateDto findMember(String email, String memberId) {
        Member member = memberRepository.findByMemberIdAndEmail(memberId, email)
                .orElseThrow(MEMBER_NOT_FOUND::exception);

        member.updateMemberPassword(UUID.randomUUID().toString().substring(0, 8));

        String message = "[Smart] 회원 비밀번호 찾기 안내 메일입니다.";
        String text = "회원님의 임시 비밀번호는 \"" + member.getPassword()
                + "\"입니다. 개인정보 보호를 위해 로그인 후 변경 부탁드립니다.";

        return new MemberCertificateDto(email, message, text);
    }
}
