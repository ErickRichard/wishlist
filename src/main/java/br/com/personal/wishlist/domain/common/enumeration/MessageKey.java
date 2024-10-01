package br.com.personal.wishlist.domain.common.enumeration;

import br.com.personal.wishlist.domain.common.utils.ValidateMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum MessageKey implements ValidateMessage {

    PRODUCT_NOT_FOUND("product-not-found", "Product not found."),
    PRODUCT_WISHLIST_NOT_FOUND("product-wishlist-not-found", "Product wishlist not found."),
    PRODUCT_ALREADY_REGISTERED("product-already-registered", "Product already registered."),
    USER_NOT_FOUND("user-not-found", "User not found."),
    LIMIT_EXCEEDED("limit-exceeded", "Limit exceeded for the wishlist."),
    WISHLIST_NOT_FOUND("wishlist-not-found", "Wishlist not found."),
    TOKEN_REQUIRED("token-required", "Token is required."),
    INVALID_PASSWORD("invalid-password", "Password is invalid."),
    ERROR_DEFAULT("error-default", "Error default."),
    FAIL_TO_GENERATE_TOKEN("fail-to-generate-token", "Fail to generate token."),
    INVALID_TOKEN_EXPIRED("invalid-token-expired", "Invalid token or Expired.");

    private final String code;
    private final String message;

    public static MessageKey findByCode(String code) {
        return Arrays.stream(MessageKey.values())
                .filter(keyEnum -> Objects.equals(keyEnum.getCode(), code))
                .findFirst()
                .orElse(ERROR_DEFAULT);
    }
}
