package estate.controller;

import estate.common.config.AdminMenuDefine;
import estate.common.util.GsonUtil;
import estate.common.util.LogUtil;
import estate.entity.database.ConsoleUserEntity;
import estate.entity.display.AdminMenu;
import estate.entity.json.BasicJson;
import estate.service.BaseService;
import estate.service.ConsoleUserService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kangbiao on 15-10-6.
 *
 */
@RestController
@RequestMapping("/web/auth")
public class AuthController
{
    Logger logger= LogUtil.getLogger(this.getClass());

    @Autowired
    private ConsoleUserService consoleUserService;
    @Autowired
    private BaseService baseService;

    /**
     * 管理员登陆
     * @param request
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public BasicJson login(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson();
        ConsoleUserEntity consoleUserEntity;
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        if (username==null||username.equals(""))
        {
            basicJson.getErrorMsg().setDescription("请输入用户名");
            return basicJson;
        }

        if (password==null||password.equals(""))
        {
            basicJson.getErrorMsg().setDescription("请输入密码");
            return basicJson;
        }
        try
        {
            consoleUserEntity = consoleUserService.getConsoleUserByPhone(username);
            if (consoleUserEntity==null)
            {
                basicJson.getErrorMsg().setDescription("用户不存在");
                return basicJson;
            }
            if (!consoleUserEntity.getPassword().equals(password))
            {
                basicJson.getErrorMsg().setDescription("密码错误!");
                return basicJson;
            }
            consoleUserEntity.setLastLogin(System.currentTimeMillis());
            baseService.save(consoleUserEntity);
        }
        catch (Exception e)
        {
            logger.error("登陆时数据库异常:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("登陆异常");
            return basicJson;
        }

        basicJson.setStatus(true);
        request.getSession().setAttribute("user",consoleUserEntity);
        return basicJson;
    }

    /**
     * 会话保持,可扩展为循环收取通知
     * @param request
     * @return 返回标准json对象
     */
    @RequestMapping(value = "/keepSession")
    public BasicJson keepSession(HttpServletRequest request)
    {
        return new BasicJson(true);
    }

    /**
     * 登出
     * @param request
     * @return 返回操作结果
     */
    @RequestMapping(value = "/loginOut")
    public BasicJson loginOut(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 修改密码,需要重新登陆
     * @param request
     * @return 操作结果
     */
    @RequestMapping(value = "/changePassword")
    public BasicJson changePassword(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        try
        {
            String oldPassword=request.getParameter("oldPassword");
            String newPassword=request.getParameter("newPassword");
            ConsoleUserEntity consoleUserEntity= (ConsoleUserEntity) request.getSession().getAttribute("user");
            if (!oldPassword.equals(consoleUserEntity.getPassword()))
            {
                basicJson.getErrorMsg().setDescription("原密码错误");
                return basicJson;
            }
            consoleUserEntity.setPassword(newPassword);
            baseService.save(consoleUserEntity);
        }
        catch (Exception e)
        {

            logger.error("修改密码时发生异常:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("修改密码失败");
            return basicJson;
        }

        request.getSession().removeAttribute("user");
        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 管理员获取可操作的菜单栏
     * @param request
     * @return 菜单栏目
     */
    @RequestMapping(value = "getMenu")
    public BasicJson getMenu(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        StringBuilder stringBuilder=new StringBuilder(AdminMenuDefine.INDEX);
        ConsoleUserEntity consoleUserEntity= (ConsoleUserEntity) request.getSession().getAttribute("user");
        AdminMenu adminMenu=GsonUtil.getGson().fromJson(consoleUserEntity.getConsoleGroupEntity().getMenu(),AdminMenu.class);
        try
        {
            if (adminMenu.getProperty().equals("on"))
                stringBuilder.append(AdminMenuDefine.PROPERTY);
            if (adminMenu.getParkLot().equals("on"))
                stringBuilder.append(AdminMenuDefine.PARKLOT);
            if (adminMenu.getUser().equals("on"))
                stringBuilder.append(AdminMenuDefine.USER);
            if (adminMenu.getFee().equals("on"))
                stringBuilder.append(AdminMenuDefine.FEE);
            if (adminMenu.getNotice().equals("on"))
                stringBuilder.append(AdminMenuDefine.NOTICE);
            if (adminMenu.getComplainRepair().equals("on"))
                stringBuilder.append(AdminMenuDefine.COMPLAINREPAIR);
            if (adminMenu.getSecret().equals("on"))
                stringBuilder.append(AdminMenuDefine.SECRET);
            if (adminMenu.getAdmin().equals("on"))
                stringBuilder.append(AdminMenuDefine.ADMIN);
            basicJson.setJsonString(stringBuilder.toString());
        }
        catch (Exception e)
        {
            logger.error("获取权限菜单异常:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("获取菜单出错");
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 获取管理员的资料
     * @param request
     * @return 管理员个人资料
     */
    @RequestMapping(value = "/getAdminProfile")
    public BasicJson getAdminProfile(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        ConsoleUserEntity consoleUserEntity= (ConsoleUserEntity) request.getSession().getAttribute("user");
        basicJson.setJsonString(consoleUserEntity);

        basicJson.setStatus(true);
        return basicJson;
    }
}
