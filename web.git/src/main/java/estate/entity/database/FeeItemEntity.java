package estate.entity.database;

import java.math.BigDecimal;

/**
 * Created by kangbiao on 15-11-1.
 *
 */
public class FeeItemEntity
{
    private int id;
    private String name;
    private String decription;
    private Byte feeType;
    private String unit;
    private BigDecimal unitPrice;
    private BigDecimal overdueUnitPrice;
    private String overdueUnit;
    private Long payStartTime;
    private Long payEndTime;
    private Long effectiveStartTime;
    private Long effectiveEndTime;
    private Byte isPeriodic;
    private Integer villageId;
    private String extendInfo;
    private Long addTime;
    private Byte isEffective;
    private VillageEntity villageEntity;


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDecription()
    {
        return decription;
    }

    public void setDecription(String decription)
    {
        this.decription = decription;
    }

    public Byte getFeeType()
    {
        return feeType;
    }

    public void setFeeType(Byte feeType)
    {
        this.feeType = feeType;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getOverdueUnitPrice()
    {
        return overdueUnitPrice;
    }

    public void setOverdueUnitPrice(BigDecimal overdueUnitPrice)
    {
        this.overdueUnitPrice = overdueUnitPrice;
    }

    public String getOverdueUnit()
    {
        return overdueUnit;
    }

    public void setOverdueUnit(String overdueUnit)
    {
        this.overdueUnit = overdueUnit;
    }

    public Long getPayStartTime()
    {
        return payStartTime;
    }

    public void setPayStartTime(Long payStartTime)
    {
        this.payStartTime = payStartTime;
    }

    public Long getPayEndTime()
    {
        return payEndTime;
    }

    public void setPayEndTime(Long payEndTime)
    {
        this.payEndTime = payEndTime;
    }

    public Long getEffectiveStartTime()
    {
        return effectiveStartTime;
    }

    public void setEffectiveStartTime(Long effectiveStartTime)
    {
        this.effectiveStartTime = effectiveStartTime;
    }

    public Long getEffectiveEndTime()
    {
        return effectiveEndTime;
    }

    public void setEffectiveEndTime(Long effectiveEndTime)
    {
        this.effectiveEndTime = effectiveEndTime;
    }

    public Byte getIsPeriodic()
    {
        return isPeriodic;
    }

    public void setIsPeriodic(Byte isPeriodic)
    {
        this.isPeriodic = isPeriodic;
    }

    public Integer getVillageId()
    {
        return villageId;
    }

    public void setVillageId(Integer villageId)
    {
        this.villageId = villageId;
    }

    public String getExtendInfo()
    {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo)
    {
        this.extendInfo = extendInfo;
    }

    public Long getAddTime()
    {
        return addTime;
    }

    public void setAddTime(Long addTime)
    {
        this.addTime = addTime;
    }

    public Byte getIsEffective()
    {
        return isEffective;
    }

    public void setIsEffective(Byte isEffective)
    {
        this.isEffective = isEffective;
    }


    public VillageEntity getVillageEntity()
    {
        return villageEntity;
    }

    public void setVillageEntity(VillageEntity villageEntity)
    {
        this.villageEntity = villageEntity;
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
