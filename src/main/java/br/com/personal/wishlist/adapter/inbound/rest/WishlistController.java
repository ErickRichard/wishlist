package br.com.personal.wishlist.adapter.inbound.rest;

import br.com.personal.wishlist.application.dto.request.WishlistRequest;
import br.com.personal.wishlist.application.dto.response.WishlistResponse;
import br.com.personal.wishlist.application.port.AuthorizationServicePort;
import br.com.personal.wishlist.application.port.WishlistServicePort;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/wishlist")
public class WishlistController {

    @Autowired
    AuthorizationServicePort authorizationServicePort;

    @Autowired
    WishlistServicePort wishlistServicePort;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Results are ok", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WishlistResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Invalid request", content = @Content)})
    @PostMapping("/add")
    public ResponseEntity<WishlistResponse> includeWishlist(@RequestBody WishlistRequest wishlistRequest) {
        var wishlist = wishlistServicePort.addWishlist(wishlistRequest);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(wishlist);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find all wishlists successfully", content = {@Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = WishlistResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Invalid request", content = @Content)})

    @GetMapping("/all")
    public ResponseEntity<List<WishlistResponse>> findAllWishlist() {
        return ResponseEntity.ok().body(wishlistServicePort.findAll());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find wishlist by userId and productId successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = WishlistResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Invalid request", content = @Content)})
    @GetMapping("/product")
    public ResponseEntity<WishlistResponse> findByProductName(@RequestParam(required = false) String wishlistId, @RequestParam(required = false) String userId, @RequestParam(required = false) String productName) {
        return ResponseEntity.ok().body(wishlistServicePort.findByProduct(wishlistId, userId, productName));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Wishlist deleted successfully", content = @Content),
            @ApiResponse(responseCode = "500", description = "Invalid request", content = @Content)})
    @DeleteMapping("/remove")
    public ResponseEntity<Void> remove(@RequestParam(required = false) String wishlistId, @RequestParam(required = false) String userId, @RequestParam(required = false) String productId) {
        wishlistServicePort.removeProduct(wishlistId, userId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}