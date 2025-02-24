package mft.library.model.entity.enums;


public enum InsuranceStatus {
    ACTIVE("فعال"),
    EXPIRED("منقضی"),
    CANCELLED("لغو شده");

    private final String description;

    InsuranceStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

