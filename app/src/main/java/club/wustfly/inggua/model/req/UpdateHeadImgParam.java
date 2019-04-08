package club.wustfly.inggua.model.req;

import club.wustfly.inggua.model.BaseModel;

public class UpdateHeadImgParam extends BaseModel {

    private int uid;
    private String file;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
