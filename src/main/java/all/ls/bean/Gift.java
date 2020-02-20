package all.ls.bean;

/**
 * Demo class
 *
 * @author ls
 * @date 20-1-9
 */
public class Gift {
    /**
     * giftname 礼物名称
     * giftcost 所需礼物卡
     *giftdesc 礼物描述
     *giftcapacity 库存
     *giftimgurl 图片url
     * */
    private String giftid;
    private String giftname;
    private int giftcost;
    private String giftdesc;
    private int giftcapacity;
    private String giftimgurl;

    public String getGiftid() {
        return giftid;
    }

    public void setGiftid(String giftid) {
        this.giftid = giftid;
    }

    public String getGiftname() {
        return giftname;
    }

    public void setGiftname(String giftname) {
        this.giftname = giftname;
    }

    public int getGiftcost() {
        return giftcost;
    }

    public void setGiftcost(int giftcost) {
        this.giftcost = giftcost;
    }

    public String getGiftdesc() {
        return giftdesc;
    }

    public void setGiftdesc(String giftdesc) {
        this.giftdesc = giftdesc;
    }

    public int getGiftcapacity() {
        return giftcapacity;
    }

    public void setGiftcapacity(int giftcapacity) {
        this.giftcapacity = giftcapacity;
    }

    public String getGiftimgurl() {
        return giftimgurl;
    }

    public void setGiftimgurl(String giftimgurl) {
        this.giftimgurl = giftimgurl;
    }
}
