package estate.entity.database;

import java.math.BigDecimal;

/**
 * Created by kangbiao on 15-11-30.
 *
 */
public class UserBillEntity
{
    private int id;
    private String billSeris;
    private String phone;
    private String propertyBill;
    private String serviceBill;
    private String parklotBill;
    private Long updateTime;
    private Byte payStatus;
    private Byte payType;
    private Long payTime;
    private BigDecimal overdueFee;


    public String getBillSeris()
    {
        return billSeris;
    }

    public void setBillSeris(String billSeris)
    {
        this.billSeris = billSeris;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPropertyBill()
    {
        return propertyBill;
    }

    public void setPropertyBill(String propertyBill)
    {
        this.propertyBill = propertyBill;
    }

    public String getServiceBill()
    {
        return serviceBill;
    }

    public void setServiceBill(String serviceBill)
    {
        this.serviceBill = serviceBill;
    }

    public String getParklotBill()
    {
        return parklotBill;
    }

    public void setParklotBill(String parklotBill)
    {
        this.parklotBill = parklotBill;
    }

    public Long getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime)
    {
        this.updateTime = updateTime;
    }

    public Byte getPayStatus()
    {
        return payStatus;
    }

    public void setPayStatus(Byte payStatus)
    {
        this.payStatus = payStatus;
    }

    public Byte getPayType()
    {
        return payType;
    }

    public void setPayType(Byte payType)
    {
        this.payType = payType;
    }

    public Long getPayTime()
    {
        return payTime;
    }

    public void setPayTime(Long payTime)
    {
        this.payTime = payTime;
    }

    public BigDecimal getOverdueFee()
    {
        return overdueFee;
    }

    public void setOverdueFee(BigDecimal overdueFee)
    {
        this.overdueFee = overdueFee;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserBillEntity that = (UserBillEntity) o;

        if (id != that.id) return false;
        if (billSeris != null ? !billSeris.equals(that.billSeris) : that.billSeris != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (propertyBill != null ? !propertyBill.equals(that.propertyBill) : that.propertyBill != null) return false;
        if (serviceBill != null ? !serviceBill.equals(that.serviceBill) : that.serviceBill != null) return false;
        if (parklotBill != null ? !parklotBill.equals(that.parklotBill) : that.parklotBill != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (payStatus != null ? !payStatus.equals(that.payStatus) : that.payStatus != null) return false;
        if (payType != null ? !payType.equals(that.payType) : that.payType != null) return false;
        if (payTime != null ? !payTime.equals(that.payTime) : that.payTime != null) return false;
        if (overdueFee != null ? !overdueFee.equals(that.overdueFee) : that.overdueFee != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + (billSeris != null ? billSeris.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (propertyBill != null ? propertyBill.hashCode() : 0);
        result = 31 * result + (serviceBill != null ? serviceBill.hashCode() : 0);
        result = 31 * result + (parklotBill != null ? parklotBill.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (payStatus != null ? payStatus.hashCode() : 0);
        result = 31 * result + (payType != null ? payType.hashCode() : 0);
        result = 31 * result + (payTime != null ? payTime.hashCode() : 0);
        result = 31 * result + (overdueFee != null ? overdueFee.hashCode() : 0);
        return result;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
