package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DigiT-25 on 02-11-2016.
 */
public class StoreDTO {

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getTiming() {
        return Timing;
    }

    public void setTiming(String timing) {
        Timing = timing;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    String StoreId;
    String Name;
    String Address;
    String City;
    String State;
    String Country;
    String Timing;
    String CategoryId;
    String Category;
    String Img;

    public List<StoreDTO> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<StoreDTO> storeList) {
        this.storeList = storeList;
    }

    List<StoreDTO> storeList = new ArrayList<>();

}
