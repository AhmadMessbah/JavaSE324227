package mft.library.model.entity.enums;


public enum InsuranceType {
    CAR("بیمه خودرو"),
    LIFE("بیمه عمر"),
    HEALTH("بیمه سلامت"),
    PROPERTY("بیمه اموال");

    private final String description;

    InsuranceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

