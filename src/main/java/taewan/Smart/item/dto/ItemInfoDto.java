package taewan.Smart.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import taewan.Smart.item.entity.Item;

@Getter
@ToString
public class ItemInfoDto {

    private Long itemId;
    private String itemImg;
    private String itemName;
    private Integer price;
    private Long category;
    private String itemInformation;

    public ItemInfoDto(Item item) {
        this.itemId = item.getItemId();
        this.itemImg = item.getItemImg();
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.category = item.getCategory();
        this.itemInformation = item.getItemInformation();
    }
}
