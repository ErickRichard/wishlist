package br.com.personal.wishlist.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Details about the Token")
public class TokenRequest {
    @Schema(description = "username",example = "teste@luizalabs.com")
    private String email;
    @Schema(description = "password",example = "1234567")
    private String password;
}