package taewan.Smart.item.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemImg;
    private String itemName;
    private Integer price;
    private Long category;
    private String itemInformation;

    public Item(ItemSaveDto dto) {
        this.itemImg = dto.getItemImg();
        this.itemName = dto.getItemName();
        this.price = dto.getPrice();
        this.category = dto.getCategory();
        this.itemInformation = dto.getItemInformation();
    }

    public void updateItem(ItemUpdateDto dto) {
        this.itemId = dto.getItemId();
        this.itemImg = dto.getItemImg();
        this.itemName = dto.getItemName();
        this.price = dto.getPrice();
        this.category = dto.getCategory();
        this.itemInformation = dto.getItemInformation();
    }
}
