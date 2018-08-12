package comeon.com.assignment.model;

/**
 * Created by amir on 8/12/18.
 */

public class User{
    String Uid;
    public String locale;
    public String useMobile;
    public String lastProduct;
    public String mobileSeriaNo;

    public void setMobileSeriaNo(String mobileSeriaNo) {
        this.mobileSeriaNo = mobileSeriaNo;
    }



    public String getUid() {
        return Uid;
    }




    public void setUid(String uid) {
        Uid = uid;
    }




    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setLastProduct(String lastProduct) {
        this.lastProduct = lastProduct;
    }



    public void setUseMobile(String useMobile) {
        this.useMobile = useMobile;
    }



}
