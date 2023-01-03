package taewan.Smart.item.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSaveDto {

    private String itemImg;
    private String itemName;
    private Integer price;
    private Long category;
    private String itemInformation;
}
