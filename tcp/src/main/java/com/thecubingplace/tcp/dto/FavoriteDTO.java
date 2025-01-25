package com.thecubingplace.tcp.dto;

import com.thecubingplace.tcp.model.Favorite;
import com.thecubingplace.tcp.model.Item;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class FavoriteDTO {
    private Long userId;
    private Long itemId;
    private Item.ItemType itemType;
    private String itemTitle;  // titre du cours ou de la leçon
    private Timestamp createdAt;

    public static FavoriteDTO fromFavorite(Favorite favorite) {
        FavoriteDTO dto = new FavoriteDTO();
        dto.setUserId(favorite.getId().getUserId());
        dto.setItemId(favorite.getId().getItemId());
        dto.setItemType(favorite.getItem().getType());
        dto.setItemTitle(favorite.getItem().getType() == Item.ItemType.COURSE ? 
            favorite.getItem().getCourse().getTitle() : 
            favorite.getItem().getLesson().getTitle());
        dto.setCreatedAt(favorite.getCreatedAt());
        return dto;
    }
} 