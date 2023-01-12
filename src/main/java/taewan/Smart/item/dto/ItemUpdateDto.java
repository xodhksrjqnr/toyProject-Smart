package taewan.Smart.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemUpdateDto {

    private Long itemId;
    private String itemImg;
    private String itemName;
    private Integer price;
    private Long category;
    private String itemInformation;
}
