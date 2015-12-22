package estate.entity.database;

/**
 * Created by kangbiao on 15-11-3.
 */
public class UserInfoEntity
{
    private Integer id;
    private String phone;
    private Long birthday;
    private String name;
    private Byte sex;
    private String urgentName;
    private String urgentPhone;
    private Byte identityType;
    private String identityCode;
    private String vehicleList;


    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Long getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Long birthday)
    {
        this.birthday = birthday;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Byte getSex()
    {
        return sex;
    }

    public void setSex(Byte sex)
    {
        this.sex = sex;
    }

    public String getUrgentName()
    {
        return urgentName;
    }

    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }

    public String getUrgentPhone()
    {
        return urgentPhone;
    }

    public void setUrgentPhone(String urgentPhone)
    {
        this.urgentPhone = urgentPhone;
    }

    public Byte getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType(Byte identityType)
    {
        this.identityType = identityType;
    }

    public String getIdentityCode()
    {
        return identityCode;
    }

    public void setIdentityCode(String identityCode)
    {
        this.identityCode = identityCode;
    }

    public String getVehicleList()
    {
        return vehicleList;
    }

    public void setVehicleList(String vehicleList)
    {
        this.vehicleList = vehicleList;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }
}
