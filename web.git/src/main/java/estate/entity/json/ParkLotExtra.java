package estate.entity.json;

/**
 * Created by kangbiao on 15-10-16.
 * 存储车位费的额外配置
 */
public class ParkLotExtra
{
    private String monthPrice;
    private String perTimePrice;
    private String managePrice;

    public String getMonthPrice()
    {
        return monthPrice;
    }

    public void setMonthPrice(String monthPrice)
    {
        this.monthPrice = monthPrice;
    }

    public String getPerTimePrice()
    {
        return perTimePrice;
    }

    public void setPerTimePrice(String perTimePrice)
    {
        this.perTimePrice = perTimePrice;
    }

    public String getManagePrice()
    {
        return managePrice;
    }

    public void setManagePrice(String managePrice)
    {
        this.managePrice = managePrice;
    }
}
