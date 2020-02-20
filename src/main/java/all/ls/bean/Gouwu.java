package all.ls.bean;

/**
 * Demo class
 *
 * @author ls
 * @date 20-2-1
 */
public class Gouwu {
    private int _id;
    private String goodsname;
    private String goodsprice;
    private String goodsimgurl;
    private int goodsnumber;
    private String goodstime;
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getGoodstime() {
        return goodstime;
    }

    public void setGoodstime(String goodstime) {
        this.goodstime = goodstime;
    }

    public int getGoodsid() {
        return _id;
    }

    public void setGoodsid(int goodsid) {
        this._id = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(String goodsprice) {
        this.goodsprice = goodsprice;
    }

    public String getGoodsimgurl() {
        return goodsimgurl;
    }

    public void setGoodsimgurl(String goodsimgurl) {
        this.goodsimgurl = goodsimgurl;
    }

    public int getGoodsnumber() {
        return goodsnumber;
    }

    public void setGoodsnumber(int goodsnumber) {
        this.goodsnumber = goodsnumber;
    }
}
