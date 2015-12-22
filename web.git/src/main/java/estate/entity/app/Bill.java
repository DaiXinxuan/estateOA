package estate.entity.app;

import estate.entity.json.Select2;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-11-4.
 *
 */
public class Bill
{
    private Integer id;
    private String total;
    private ArrayList<Select2> items;
    private byte status;
    private String billTime;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getBillTime()
    {
        return billTime;
    }

    public void setBillTime(String billTime)
    {
        this.billTime = billTime;
    }

    public byte getStatus()
    {
        return status;
    }

    public void setStatus(byte status)
    {
        this.status = status;
    }

    public ArrayList<Select2> getItems()
    {
        return items;
    }

    public void setItems(ArrayList<Select2> items)
    {
        this.items = items;
    }

    public String getTotal()
    {
        return total;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }
}
