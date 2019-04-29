package club.wustfly.inggua.model.req;


import club.wustfly.inggua.model.BaseModel;

public class ObtainServiceScopeParam extends BaseModel {

    private String location;
    private String diu;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDiu() {
        return diu;
    }

    public void setDiu(String diu) {
        this.diu = diu;
    }
}
