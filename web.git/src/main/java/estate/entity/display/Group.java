package estate.entity.display;

import estate.common.util.GsonUtil;
import estate.common.util.LogUtil;
import estate.entity.database.ConsoleGroupEntity;

/**
 * Created by kangbiao on 15-11-11.
 * 用户组信息转换
 */
public class Group
{
    private Integer id;
    private String name;
    private AdminMenu adminMenu;

    public Group(){}
    public Group(ConsoleGroupEntity consoleGroupEntity)
    {
        this.id=consoleGroupEntity.getId();
        this.name=consoleGroupEntity.getName();
        this.adminMenu= GsonUtil.getGson().fromJson(consoleGroupEntity.getMenu(),AdminMenu.class);
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public AdminMenu getAdminMenu()
    {
        return adminMenu;
    }

    public void setAdminMenu(AdminMenu adminMenu)
    {
        this.adminMenu = adminMenu;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
