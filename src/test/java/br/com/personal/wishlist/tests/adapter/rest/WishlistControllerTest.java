package br.com.personal.wishlist.tests.adapter.rest;

import br.com.personal.wishlist.adapter.inbound.rest.WishlistController;
import br.com.personal.wishlist.application.dto.request.WishlistRequest;
import br.com.personal.wishlist.application.dto.response.WishlistResponse;
import br.com.personal.wishlist.application.port.AuthorizationServicePort;
import br.com.personal.wishlist.application.port.WishlistServicePort;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class WishlistControllerTest {

    public static final String WISHLIST_ID = "wishlist123";
    public static final String USER_ID = "user123";
    public static final String PRODUCT_NAME = "Product Name";

    private MockMvc mockMvc;

    @InjectMocks
    private WishlistController wishlistController;

    @Mock
    private AuthorizationServicePort authorizationServicePort;

    @Mock
    private WishlistServicePort wishlistServicePort;

    private WishlistRequest wishlistRequest;
    private WishlistResponse wishlistResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(wishlistController).build();
        wishlistRequest = Mocks.createWishlistRequest();
        wishlistResponse = Mocks.createWishlistResponse();
    }

    @Test
    void shouldAddWishlistSuccessfully() throws Exception {
        Mockito.when(wishlistServicePort.addWishlist(Mockito.any(WishlistRequest.class))).thenReturn(wishlistResponse);
        mockMvc.perform(post("/v1/wishlist/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(wishlistRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldFindAllWishlistsSuccessfully() throws Exception {
        List<WishlistResponse> wishlists = new ArrayList<>();
        wishlists.add(wishlistResponse);
        Mockito.when(wishlistServicePort.findAll()).thenReturn(wishlists);
        mockMvc.perform(get("/v1/wishlist/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindWishlistByProductSuccessfully() throws Exception {
        Mockito.when(wishlistServicePort.findByProduct(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(wishlistResponse);
        mockMvc.perform(get("/v1/wishlist/product")
                        .param("wishlistId", WISHLIST_ID)
                        .param("userId", USER_ID)
                        .param("productName", PRODUCT_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRemoveWishlistSuccessfully() throws Exception {
        Mockito.doNothing().when(wishlistServicePort).removeProduct(WISHLIST_ID, USER_ID, PRODUCT_NAME);
        mockMvc.perform(delete("/v1/wishlist/remove")
                        .param("wishlistId", WISHLIST_ID)
                        .param("userId", USER_ID)
                        .param("productId", PRODUCT_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}