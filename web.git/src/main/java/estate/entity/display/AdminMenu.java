package estate.entity.display;

/**
 * Created by kangbiao on 15-11-10.
 * 用户组的菜单情况实体类
 */
public class AdminMenu
{
    private String property;
    private String parkLot;
    private String user;
    private String fee;
    private String notice;
    private String complainRepair;
    private String secret;
    private String admin;

    public String getSecret()
    {
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String property)
    {
        this.property = property;
    }

    public String getParkLot()
    {
        return parkLot;
    }

    public void setParkLot(String parkLot)
    {
        this.parkLot = parkLot;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getFee()
    {
        return fee;
    }

    public void setFee(String fee)
    {
        this.fee = fee;
    }

    public String getNotice()
    {
        return notice;
    }

    public void setNotice(String notice)
    {
        this.notice = notice;
    }

    public String getComplainRepair()
    {
        return complainRepair;
    }

    public void setComplainRepair(String complainRepair)
    {
        this.complainRepair = complainRepair;
    }

    public String getAdmin()
    {
        return admin;
    }

    public void setAdmin(String admin)
    {
        this.admin = admin;
    }
}
