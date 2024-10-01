package br.com.personal.wishlist.application.port;

import br.com.personal.wishlist.application.dto.request.TokenRequest;
import br.com.personal.wishlist.application.dto.response.TokenResponse;

public interface AuthorizationServicePort {
    TokenResponse authenticateUser(TokenRequest tokenRequest);
}
