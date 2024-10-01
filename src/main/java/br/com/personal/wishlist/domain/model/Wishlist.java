package br.com.personal.wishlist.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "wishlists")
public class Wishlist {

    @Id
    private String id;
    @Field(name = "user_id")
    private String userId;
    @Field(name = "products")
    private List<Product> products;

    public Wishlist() {
        this.products = new ArrayList<>();
    }
}