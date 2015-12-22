package estate.entity.database;

/**
 * Created by kangbiao on 15-9-13.
 *
 */
public class ConsoleUserEntity
{
    private int id;
    private String password;
    private String phone;
    private String email;
    private Integer consoleGroupId;
    private String name;
    private Byte identityType;
    private String identityId;
    private Long lastLogin;
    private ConsoleGroupEntity consoleGroupEntity;



    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getConsoleGroupId()
    {
        return consoleGroupId;
    }

    public void setConsoleGroupId(Integer consoleGroupId)
    {
        this.consoleGroupId = consoleGroupId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Byte getIdentityType()
    {
        return identityType;
    }

    public void setIdentityType(Byte identityType)
    {
        this.identityType = identityType;
    }

    public String getIdentityId()
    {
        return identityId;
    }

    public void setIdentityId(String identityId)
    {
        this.identityId = identityId;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Long getLastLogin()
    {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin)
    {
        this.lastLogin = lastLogin;
    }

    public ConsoleGroupEntity getConsoleGroupEntity()
    {
        return consoleGroupEntity;
    }

    public void setConsoleGroupEntity(ConsoleGroupEntity consoleGroupEntity)
    {
        this.consoleGroupEntity = consoleGroupEntity;
    }
}
