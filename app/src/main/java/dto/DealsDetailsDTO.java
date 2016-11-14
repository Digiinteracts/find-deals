package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DigiT-25 on 08-11-2016.
 */
public class DealsDetailsDTO {

    String Deals_Id;
    String name;
    String Img;

    public List<DealsDetailsDTO> getDealsList() {
        return dealsList;
    }

    public void setDealsList(List<DealsDetailsDTO> dealsList) {
        this.dealsList = dealsList;
    }

    List<DealsDetailsDTO> dealsList = new ArrayList<>();

    public String getDeals_Id() {
        return Deals_Id;
    }

    public void setDeals_Id(String deals_Id) {
        Deals_Id = deals_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }
}
