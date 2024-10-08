package br.com.personal.wishlist.tests.adapter.rest;

import br.com.personal.wishlist.adapter.inbound.rest.AuthenticationController;
import br.com.personal.wishlist.application.dto.request.TokenRequest;
import br.com.personal.wishlist.application.dto.response.TokenResponse;
import br.com.personal.wishlist.application.port.AuthorizationServicePort;
import br.com.personal.wishlist.tests.mock.Mocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthorizationServicePort authorizationServicePort;

    private TokenRequest tokenRequest;
    private TokenResponse tokenResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
        tokenRequest = Mocks.createTokenRequest();
        tokenResponse = Mocks.createTokenResponse();
    }

    @Test
    void shouldAuthenticateUserSuccessfully() throws Exception {
        Mockito.when(authorizationServicePort.authenticateUser(Mockito.any())).thenReturn(tokenResponse);
        mockMvc.perform(post("/auth/v1/token-jwt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(tokenRequest)))
                .andExpect(status().isOk());
    }
}
