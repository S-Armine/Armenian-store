package com.armstore.armeniannativestore.repository;

import com.armstore.armeniannativestore.model.cart.CartItem;
import com.armstore.armeniannativestore.model.compositekey.ItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, ItemKey> {

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.id.collectionId = :cartId AND ci.id.productId = :productId")
    void deleteByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);
}
