package club.wustfly.inggua.model.bean;

import club.wustfly.inggua.model.BaseModel;

public class CouponItem extends BaseModel {


    /**
     * id : 3
     * money : 30
     * condition : 300
     */

    private Integer id;
    private String money;
    private String condition;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
