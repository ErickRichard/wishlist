package br.com.personal.wishlist.domain.repository;

import br.com.personal.wishlist.domain.model.Wishlist;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public class WishlistCustomRepository {

    private final MongoTemplate mongoTemplate;

    public WishlistCustomRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<Wishlist> findWishlistByFilters(String wishlistId, String userId, String productId, String productName) {
        Query query = new Query();

        if (Objects.nonNull(wishlistId)) {
            query.addCriteria(Criteria.where("id").is(wishlistId));
        }

        if (Objects.nonNull(userId)) {
            query.addCriteria(Criteria.where("userId").is(userId));
        }

        if (Objects.nonNull(productId) || Objects.nonNull(productName)) {
            Criteria productCriteria = new Criteria();
            if (Objects.nonNull(productId)) {
                productCriteria = Criteria.where("products.id").is(productId);
            }
            if (Objects.nonNull(productName)) {
                productCriteria = productCriteria.orOperator(Criteria.where("products.name").is(productName));
            }
            query.addCriteria(productCriteria);
        }

        return Optional.ofNullable(mongoTemplate.findOne(query, Wishlist.class));
    }
}
