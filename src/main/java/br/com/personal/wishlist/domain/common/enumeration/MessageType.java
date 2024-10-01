package br.com.personal.wishlist.domain.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {

    ERROR("ERROR", "1"),
    WARNING("WARNING", "2"),
    SUCCESS("SUCCESS", "3");

    private final String description;
    private final String code;
}
