package club.wustfly.inggua.model.req;

import club.wustfly.inggua.model.BaseModel;

public class DeleteOrderParam extends BaseModel {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
