package br.com.personal.wishlist.application.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@Generated
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponse {
    private String id;
    private List<ProductResponse> productResponse;
}
