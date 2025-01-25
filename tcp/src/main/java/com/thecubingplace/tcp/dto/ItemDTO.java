package com.thecubingplace.tcp.dto;

import com.thecubingplace.tcp.model.Item;
import lombok.Data;

@Data
public class ItemDTO {
    private Long id;
    private Item.ItemType type;
    private Long referenceId;  // ID du cours ou de la leçon
    private String title;      // Titre du cours ou de la leçon

    public static ItemDTO fromItem(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setType(item.getType());
        if (item.getType() == Item.ItemType.COURSE) {
            dto.setReferenceId(item.getCourse().getId());
            dto.setTitle(item.getCourse().getTitle());
        } else {
            dto.setReferenceId(item.getLesson().getId());
            dto.setTitle(item.getLesson().getTitle());
        }
        return dto;
    }
} 