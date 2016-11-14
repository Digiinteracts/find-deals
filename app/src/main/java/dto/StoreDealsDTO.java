package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Digi-T25 on 11/11/2016.
 */
public class StoreDealsDTO {
    String id;
    String name;
    String deal_price;
    String discounted_price;
    String percentage_discount;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    String img;
    List<StoreDealsDTO> dealsDTOList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeal_price() {
        return deal_price;
    }

    public void setDeal_price(String deal_price) {
        this.deal_price = deal_price;
    }

    public String getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(String discounted_price) {
        this.discounted_price = discounted_price;
    }

    public String getPercentage_discount() {
        return percentage_discount;
    }

    public void setPercentage_discount(String percentage_discount) {
        this.percentage_discount = percentage_discount;
    }

    public List<StoreDealsDTO> getDealsDTOList() {
        return dealsDTOList;
    }

    public void setDealsDTOList(List<StoreDealsDTO> dealsDTOList) {
        this.dealsDTOList = dealsDTOList;
    }
}
