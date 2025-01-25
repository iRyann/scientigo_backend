package com.thecubingplace.tcp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.thecubingplace.tcp.dto.FavoriteDTO;
import com.thecubingplace.tcp.model.Favorite;
import com.thecubingplace.tcp.model.FavoriteId;
import com.thecubingplace.tcp.service.FavoriteService;
import com.thecubingplace.tcp.model.Item;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("authentication.principal.id == #userId or hasRole('ADMIN')")
    public ResponseEntity<List<FavoriteDTO>> getFavorites(
            @PathVariable Long userId,
            @RequestParam(required = false) Item.ItemType type) {
        if (type != null) {
            return ResponseEntity.ok(
                StreamSupport.stream(favoriteService.getFavoritesByUserIdAndItemType(userId, type).spliterator(), false)
                    .map(FavoriteDTO::fromFavorite)
                    .collect(Collectors.toList())
            );
        }
        return ResponseEntity.ok(
            StreamSupport.stream(favoriteService.getFavoritesByUserId(userId).spliterator(), false)
                .map(FavoriteDTO::fromFavorite)
                .collect(Collectors.toList())
        );
    }

    @PostMapping
    @PreAuthorize("authentication.principal.id == #favorite.userId or hasRole('ADMIN')")
    public ResponseEntity<FavoriteDTO> createFavorite(@RequestBody Favorite favorite) {
        return ResponseEntity.status(HttpStatus.CREATED)
                           .body(FavoriteDTO.fromFavorite(favoriteService.createFavorite(favorite)));
    }

    @DeleteMapping
    @PreAuthorize("authentication.principal.id == #userId or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFavorite(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long itemId) {
        if (userId != null && itemId != null) {
            favoriteService.deleteFavoriteById(new FavoriteId(userId, itemId));
        } else if (userId != null) {
            favoriteService.deleteFavoritesByUserId(userId);
        } else if (itemId != null) {
            favoriteService.deleteFavoritesByItemId(itemId);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}


