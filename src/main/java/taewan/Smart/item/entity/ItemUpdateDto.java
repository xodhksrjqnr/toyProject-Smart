package taewan.Smart.item.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemUpdateDto {

    private Long itemId;
    private String itemImg;
    private String itemName;
    private Integer price;
    private Long category;
    private String itemInformation;
}
