package com.thecubingplace.tcp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.thecubingplace.tcp.model.FavoriteId;
import com.thecubingplace.tcp.model.Item;
import com.thecubingplace.tcp.model.Favorite;
import com.thecubingplace.tcp.repository.FavoriteRepository;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    public Iterable<Favorite> getFavoritesByUserId(Long userId) {
        return favoriteRepository.findByIdUserId(userId);
    }

    public void deleteFavoritesByUserId(Long userId) {
        favoriteRepository.deleteByIdUserId(userId);
    }

    public void deleteFavoritesByItemId(Long itemId) {
        favoriteRepository.deleteByIdItemId(itemId);
    }

    public Iterable<Favorite> getFavoritesByUserIdAndItemType(Long userId, Item.ItemType itemType) {
        return favoriteRepository.findByIdUserIdAndItemType(userId, itemType);
    }

    public Favorite createFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }

    public void deleteFavoriteById(FavoriteId favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }

    public void deleteFavoritesById(FavoriteId favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }
}
