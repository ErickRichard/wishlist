package br.com.personal.wishlist.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "product")
public class Product {
    @Id
    private String id;
    @Field(name = "name")
    @Indexed
    private String name;
    @Field(name = "description")
    private String description;
    @Field(name = "price")
    private BigDecimal price;
    @Field(name = "quantity")
    private Integer quantity;
}
