package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DigiT-25 on 26-10-2016.
 */
public class CatagoryDto {

    public boolean isSetCheck() {
        return setCheck;
    }

    public void setSetCheck(boolean setCheck) {
        this.setCheck = setCheck;
    }

    public boolean setCheck;
    public String CatagoryId;

    public String getLike_unlike_status() {
        return like_unlike_status;
    }

    public void setLike_unlike_status(String like_unlike_status) {
        this.like_unlike_status = like_unlike_status;
    }

    public String like_unlike_status;

    public List<CatagoryDto> getCatList() {
        return catList;
    }

    public void setCatList(List<CatagoryDto> catList) {
        this.catList = catList;
    }

    List<CatagoryDto> catList = new ArrayList<>();

    public String getCatagoryId() {
        return CatagoryId;
    }

    public void setCatagoryId(String catagoryId) {
        CatagoryId = catagoryId;
    }

    public String getCatagoryName() {
        return CatagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        CatagoryName = catagoryName;
    }

    public String getCatagoryImg() {
        return CatagoryImg;
    }

    public void setCatagoryImg(String catagoryImg) {
        CatagoryImg = catagoryImg;
    }

    public String CatagoryName;
    public String CatagoryImg;
}
