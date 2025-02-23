package mft.library.model.entity.enums;

import lombok.Getter;

@Getter
public enum MilitaryType {
    COMPLETED_SERVICE("completed"),         // پایان خدمت
    MEDICAL_EXEMPTION("medical"),           // معافیت پزشکی
    KEFALAT_EXEMPTION("kefalat"),           // معافیت کفالت
    EDUCATIONAL_EXEMPTION("educational"),   // معافیت تحصیلی
    SACRIFICE_EXEMPTION("sacrifice"),       // معافیت ایثارگری
    AGE_PARDON_EXEMPTION("age_pardon"),     // معافیت عفو رهبری
    THREE_BROTHERS_EXEMPTION("three_brothers"),  // معافیت سه‌برادری
    ELITE_EXEMPTION("elite");               // معافیت نخبگان

    private final String value;

    MilitaryType(String value) {
        this.value = value;
    }

}
