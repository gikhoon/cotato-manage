package cotato.cotatomanage.domain.dto;

import lombok.Getter;

@Getter
public class AddMemberRequest {
    private String name;
    private String period;
    private int age;
    private String part;
}
