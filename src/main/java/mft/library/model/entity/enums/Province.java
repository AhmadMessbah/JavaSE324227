package mft.library.model.entity.enums;

import lombok.Getter;

@Getter
public enum Province {
    TEHRAN("تهران"),
    FARS("فارس"),
    ISFAHAN("اصفهان"),
    KHORASAN_RAZAVI("خراسان رضوی"),
    KERMAN("کرمان"),
    KURDISTAN("کردستان"),
    GUILAN("گیلان"),
    MAZANDARAN("مازندران"),
    ALBORZ("البرز"),
    EAST_AZERBAIJAN("آذربایجان شرقی"),
    WEST_AZERBAIJAN("آذربایجان غربی"),
    KHORASAN_SHOMALI("خراسان شمالی"),
    KHORASAN_JONUBI("خراسان جنوبی"),
    MARKAZI("مرکزی"),
    ZANJAN("زنجان"),
    CHAHARMAHAL_BAKHTIARI("چهارمحال بختیاری"),
    YAZD("یزد"),
    KERMANSHAH("کرمانشاه"),
    HORMOZGAN("هرمزگان"),
    ILAM("ایلام"),
    LORESTAN("لرستان"),
    GOLESTAN("گلستان"),
    QOM("قم"),
    QAZVIN("قزوین"),
    SEMNAN("سمنان"),
    KOHGILOUYEH_BOYER_AHMAD("کهگیلویه و بویراحمد"),
    BUSHEHR("بوشهر"),
    SISTAN_BALUCHISTAN("سیستان و بلوچستان");

    private final String name;

    Province(String name) {
        this.name = name;
    }
}
