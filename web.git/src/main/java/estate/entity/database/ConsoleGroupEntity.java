package estate.entity.database;

/**
 * Created by kangbiao on 15-9-13.
 *
 */
public class ConsoleGroupEntity
{
    private int id;
    private String name;
    private String authorization;
    private String menu;


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAuthorization()
    {
        return authorization;
    }

    public void setAuthorization(String authorization)
    {
        this.authorization = authorization;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ConsoleGroupEntity that = (ConsoleGroupEntity) o;

        if (id != that.id)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (authorization != null ? !authorization.equals(that.authorization) : that.authorization != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (authorization != null ? authorization.hashCode() : 0);
        return result;
    }

    public String getMenu()
    {
        return menu;
    }

    public void setMenu(String menu)
    {
        this.menu = menu;
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
