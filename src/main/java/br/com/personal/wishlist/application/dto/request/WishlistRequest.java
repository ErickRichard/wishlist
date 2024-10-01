package br.com.personal.wishlist.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@Builder
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Details about the wishlist")
public class WishlistRequest {
    private String userId;
    private List<ProductRequest> productRequestList;
}
