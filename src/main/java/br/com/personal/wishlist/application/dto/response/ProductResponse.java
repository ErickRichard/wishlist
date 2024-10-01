package br.com.personal.wishlist.application.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
}
