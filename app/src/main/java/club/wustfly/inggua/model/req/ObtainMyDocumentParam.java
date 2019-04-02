package club.wustfly.inggua.model.req;

import club.wustfly.inggua.model.BaseModel;

public class ObtainMyDocumentParam extends BaseModel {

    private String uid;
    private int type;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
