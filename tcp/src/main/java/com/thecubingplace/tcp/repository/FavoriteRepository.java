package com.thecubingplace.tcp.repository;

import org.springframework.data.repository.CrudRepository;

import com.thecubingplace.tcp.model.Favorite;
import com.thecubingplace.tcp.model.FavoriteId;
import com.thecubingplace.tcp.model.Item;

import lombok.NonNull;

public interface FavoriteRepository extends CrudRepository<Favorite, FavoriteId> {
    Iterable<Favorite> findByIdUserId(Long userId);
    Iterable<Favorite> findByIdUserIdAndItemType(Long userId, Item.ItemType type);

    void deleteByIdUserId(Long userId);
    void deleteByIdItemId(Long itemId);
    void deleteByIdUserIdAndItemType(Long userId, Item.ItemType type);
    void deleteById(@NonNull FavoriteId id);

}
