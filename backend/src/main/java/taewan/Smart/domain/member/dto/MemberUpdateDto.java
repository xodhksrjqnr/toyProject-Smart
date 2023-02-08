package taewan.Smart.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberUpdateDto extends MemberSaveDto {

    private Long id;
}
