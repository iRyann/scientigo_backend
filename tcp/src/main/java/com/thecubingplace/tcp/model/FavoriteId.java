package com.thecubingplace.tcp.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.util.Objects;

@Data
@Embeddable
public class FavoriteId implements Serializable {
    private Long userId;
    private Long itemId;

    public FavoriteId() {
    }
    public FavoriteId(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, itemId);
    }

}
