package br.com.personal.wishlist.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Details about the product")
public class ProductRequest {
    @Schema(description = "The unique ID of the product")
    private String id;
    @Schema(description = "The name of the product")
    private String name;
}