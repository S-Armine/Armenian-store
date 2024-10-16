package com.armstore.armeniannativestore.model.compositekey;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemKey implements Serializable {

    private Long collectionId;
    private Long productId;

    public ItemKey() {
    }

    public ItemKey(Long collectionId, Long productId) {
        this.collectionId = collectionId;
        this.productId = productId;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemKey that = (ItemKey) o;
        return Objects.equals(collectionId, that.collectionId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionId, productId);
    }
}
