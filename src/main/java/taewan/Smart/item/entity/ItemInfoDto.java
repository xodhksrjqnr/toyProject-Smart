package taewan.Smart.item.entity;

import lombok.Getter;

@Getter
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
