package club.wustfly.inggua.model.req;

import club.wustfly.inggua.model.BaseModel;

public class SignForParam extends BaseModel {

    private int id;
    private int status;
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
