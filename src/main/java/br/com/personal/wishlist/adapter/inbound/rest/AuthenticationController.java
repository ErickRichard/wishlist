package br.com.personal.wishlist.adapter.inbound.rest;

import br.com.personal.wishlist.application.dto.request.TokenRequest;
import br.com.personal.wishlist.application.dto.response.TokenResponse;
import br.com.personal.wishlist.application.port.AuthorizationServicePort;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/v1")
public class AuthenticationController {

    @Autowired
    private AuthorizationServicePort authorizationServicePort;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Results are ok", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Invalid request", content = @Content)})
    @PostMapping("/token-jwt")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody TokenRequest tokenRequest) {
        var token = authorizationServicePort.authenticateUser(tokenRequest);
        return ResponseEntity.ok().body(token);
    }
}
