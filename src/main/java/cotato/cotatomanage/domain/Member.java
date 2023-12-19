package cotato.cotatomanage.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Arrays;

@Getter
@Slf4j
public class Member {
    private static final int MIN_AGE = 22;
    private static final int MAX_AGE = 30;
    private static final int CURRENT_PERIOD = 9;

    private final String name;
    private final String period;
    private final int age;
    private final Part part;
    private final int ability;

    @Builder
    public Member(String name, String period, int age, String part){
        validation(name,part,age);
        this.name = name;
        this.period = period;
        this.age = age;
        this.part = Part.valueOf(part);
        this.ability = setAbility(period, age,part);
    }

    private int setAbility(String period, int age, String part) {
        int intPeriod = Integer.parseInt(period.split("기")[0]);
        int periodAbility = (CURRENT_PERIOD - intPeriod)*2;
        int partAbility = 0;

        if (age < 27) {
            partAbility = getBuffPart(part);
        }

        return age+periodAbility+partAbility;
    }

    private int getBuffPart(String part) {
        int month = LocalDate.now().getMonthValue();
        String buffPart;
        switch (month) {
            case 1, 5, 9 -> buffPart = "기획";
            case 2, 6, 10 -> buffPart = "디자이너";
            case 3, 7, 11 -> buffPart = "프론트엔드";
            default -> buffPart = "백엔드";
        }
        if (buffPart.equals(part)) return 10;
        else return 0;
    }

    void validation(String name, String part, int age){
        if (name.isBlank()||name.length() < 3 || name.length() > 10) {
            throw new IllegalArgumentException("이름은 3자에서 10자 사이여야 합니다.");
        }
        if (age > MAX_AGE || age < MIN_AGE) {
            throw new IllegalArgumentException("이름은 3자에서 10자 사이여야 합니다.");
        }
        if (Arrays.stream(Part.values()).noneMatch(v -> v.name().equals(part))) {
            throw new IllegalArgumentException("유효하지 않은 파트입니다.");
        }
    }
}
