package estate.controller;

import estate.common.util.DigestUtil;
import estate.common.util.GsonUtil;
import estate.common.util.LogUtil;
import estate.entity.database.ConsoleGroupEntity;
import estate.entity.database.ConsoleUserEntity;
import estate.entity.display.AdminMenu;
import estate.entity.json.BasicJson;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.AdminService;
import estate.service.BaseService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Basic;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by kangbiao on 15-11-3.
 * 管理人员相关的控制器
 */
@RestController
@RequestMapping("/web/admin")
public class AdminController
{

    Logger logger= LogUtil.getLogger(this.getClass());

    @Autowired
    private BaseService baseService;
    @Autowired
    private AdminService adminService;

    /**
     * 获取用户组列表
     * @param tableFilter
     * @param request
     * @return 返回用户组列表
     */
    @RequestMapping(value = "/groupList")
    public TableData groupList(TableFilter tableFilter,HttpServletRequest request)
    {
        tableFilter.setSearchValue(request.getParameter("search[value]"));
        try
        {
            return adminService.getGroupList(tableFilter);
        }
        catch (Exception e)
        {
            logger.error("获取后台用户组时发生异常:"+e.getMessage());
            return null;
        }
    }

    /**
     * 获取后台管理人员用户列表
     * @param tableFilter
     * @param request
     * @return 返回相应的后台管理人员信息
     */
    @RequestMapping(value = "/adminList")
    public TableData adminList(TableFilter tableFilter,HttpServletRequest request)
    {
        tableFilter.setSearchValue(request.getParameter("search[value]"));
        try
        {
            return adminService.getAdminList(tableFilter);
        }
        catch (Exception e)
        {
            logger.error("获取后台用户时发生异常:"+e.getMessage());
            return null;
        }
    }

    /**
     * 增加后台管理人员
     * @param consoleGroupEntity
     * @param request
     * @return 返回操作状态
     */
    @RequestMapping(value = "/addGroup")
    public BasicJson add(ConsoleGroupEntity consoleGroupEntity,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        AdminMenu adminMenu=new AdminMenu();

        Map<String,String[]> params=request.getParameterMap();
        try
        {
            adminMenu.setProperty(params.get("property")==null?"off":"on");
            adminMenu.setAdmin(params.get("admin")==null?"off":"on");
            adminMenu.setComplainRepair(params.get("complainRepair")==null?"off":"on");
            adminMenu.setFee(params.get("fee")==null?"off":"on");
            adminMenu.setNotice(params.get("notice")==null?"off":"on");
            adminMenu.setParkLot(params.get("parkLot")==null?"off":"on");
            adminMenu.setSecret(params.get("secret")==null?"off":"on");
            adminMenu.setUser(params.get("user")==null?"off":"on");
            consoleGroupEntity.setMenu(GsonUtil.getGson().toJson(adminMenu));
            baseService.save(consoleGroupEntity);
        }
        catch (Exception e)
        {
            logger.error("添加用户组是数据库异常:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("添加用户组失败,请重试");
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 添加后台管理人员
     * @param consoleUserEntity
     * @param request
     * @return 返回操作结果
     */
    @RequestMapping(value = "/addAdmin")
    public BasicJson addAdmin(ConsoleUserEntity consoleUserEntity ,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        try
        {
            consoleUserEntity.setPassword(DigestUtil.MD5(consoleUserEntity.getPassword()));
            baseService.save(consoleUserEntity);
        }
        catch (Exception e)
        {
            logger.error("添加管理员出现错误:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("添加失败");
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }


    /**
     * 根据组id删除组
     * @param id
     * @return 返回操作结果
     */
    @RequestMapping(value = "/deleteGroup/{id}")
    public BasicJson deleteGroup(@PathVariable(value = "id") Integer id)
    {
        BasicJson basicJson=new BasicJson(false);
        ConsoleGroupEntity consoleGroupEntity=new ConsoleGroupEntity();
        if (id==null)
        {
            basicJson.getErrorMsg().setDescription("参数错误");
            return basicJson;
        }
        try
        {
            if (adminService.getAdminByGroupID(id)!=null)
            {
                basicJson.getErrorMsg().setDescription("请先删除该组下的成员");
                return basicJson;
            }
            consoleGroupEntity.setId(id);
            baseService.delete(consoleGroupEntity);
        }
        catch (Exception e)
        {
            logger.error("删除后台管理人员信息错误:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("删除错误");
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 删除后台管理人员,不能删除自己,不能删除有操作记录的管理人员
     * @param id
     * @param request
     * @return 返回操作结果
     */
    @RequestMapping(value = "/deleteAdmin/{id}")
    public BasicJson deleteAdmin(@PathVariable(value = "id") Integer id,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);

        ConsoleUserEntity consoleUserEntity=new ConsoleUserEntity();
        if (id==null)
        {
            basicJson.getErrorMsg().setDescription("参数错误");
            return basicJson;
        }
        ConsoleUserEntity thisEntity= (ConsoleUserEntity) request.getSession().getAttribute("user");
        if (thisEntity.getId()==id)
        {
            basicJson.getErrorMsg().setDescription("不能删除自己");
            return basicJson;
        }
        try
        {
            consoleUserEntity.setId(id);
            baseService.delete(consoleUserEntity);
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setDescription("不能删除有记录的用户");
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 获取select2形式的组信息
     * @return
     */
    @RequestMapping(value = "/getGroupList")
    public BasicJson getGroupList()
    {
        BasicJson basicJson=new BasicJson(false);
        try
        {
            basicJson.setJsonString(adminService.getGroupList());
        }
        catch (Exception e)
        {
            logger.error("获取select2形式的组信息时发生异常:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("获取组信息失败");
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }

}
