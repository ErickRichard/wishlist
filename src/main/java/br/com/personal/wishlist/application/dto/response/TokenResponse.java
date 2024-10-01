package br.com.personal.wishlist.application.dto.response;

import lombok.*;

@Data
@Builder
@Generated
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private Long expire;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", expire=" + expire +
                '}';
    }
}
