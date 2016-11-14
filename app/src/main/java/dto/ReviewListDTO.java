package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Digi-T25 on 11/10/2016.
 */
public class ReviewListDTO {
    String name;
    String id;
    String view;
    String img;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time;
    List<ReviewListDTO> riviewsList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<ReviewListDTO> getRiviewsList() {
        return riviewsList;
    }

    public void setRiviewsList(List<ReviewListDTO> riviewsList) {
        this.riviewsList = riviewsList;
    }
}
