package club.wustfly.inggua.model.bean;

import club.wustfly.inggua.model.BaseModel;

public class BannerItem extends BaseModel {

    /**
     * id : 1
     * image : /static/upload/banner/a73ff4896ee63eafe9384bc59a409327.jpg
     */

    private Integer id;
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
