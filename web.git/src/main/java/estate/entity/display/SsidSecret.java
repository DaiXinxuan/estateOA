package estate.entity.display;


import estate.entity.database.SsidSecretEntity;

/**
 * Created by kangbiao on 15-10-29.
 * 包含控制对象信息的实体类,为适应后台显示
 */
public class SsidSecret
{
    private Integer id;
    private Byte controlType;
    private String symbol;
    private String secret;
    private String password;
    private Byte type;
    private Object controlEntity;

    public SsidSecret(){}

    public SsidSecret(SsidSecretEntity ssidSecretEntity)
    {
        this.id=ssidSecretEntity.getId();
        this.controlType=ssidSecretEntity.getControlType();
        this.symbol=ssidSecretEntity.getSymbol();
        this.secret=ssidSecretEntity.getSecret();
        this.password=ssidSecretEntity.getPassword();
        this.type=ssidSecretEntity.getType();
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Byte getControlType()
    {
        return controlType;
    }

    public void setControlType(Byte controlType)
    {
        this.controlType = controlType;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public String getSecret()
    {
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Byte getType()
    {
        return type;
    }

    public void setType(Byte type)
    {
        this.type = type;
    }

    public Object getControlEntity()
    {
        return controlEntity;
    }

    public void setControlEntity(Object controlEntity)
    {
        this.controlEntity = controlEntity;
    }
}
