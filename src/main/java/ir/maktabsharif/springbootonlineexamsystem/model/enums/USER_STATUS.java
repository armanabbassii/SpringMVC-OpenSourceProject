package ir.maktabsharif.springbootonlineexamsystem.model.enums;

import lombok.Getter;

@Getter
public enum USER_STATUS {
    PENDING("در انتظار تایید"),
    APPROVED("تایید شده"),
    BLOCKED("مسدود شده");

    private final String title;
//create new method -> pending enter
    USER_STATUS(String title) {
        this.title = title;
    }
}