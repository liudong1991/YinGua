package club.wustfly.inggua.model.req;

import club.wustfly.inggua.model.BaseModel;

public class ObtainEditParam extends BaseModel {

    private Integer uid;
    private String fid;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
