package estate.app;

import estate.common.config.SsidControlType;
import estate.common.util.LogUtil;
import estate.entity.database.OpenDoorRecordEntity;
import estate.entity.database.SsidSecretEntity;
import estate.entity.display.SsidSecret;
import estate.entity.json.BasicJson;
import estate.service.AuthorityService;
import estate.service.BaseService;
import estate.service.SsidSecretService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.management.remote.JMXConnector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-21.
 * 门禁权限,门禁记录上传,门禁密钥获取
 */
@RestController
@RequestMapping("api/auth")
public class AuthorityHandler
{

    private Logger logger= LogUtil.getLogger(this.getClass());

    @Autowired
    AuthorityService authorityService;
    @Autowired
    SsidSecretService ssidSecretService;
    @Autowired
    private BaseService baseService;

    /**
     * 获取门禁密钥
     * @param symbol 门禁编号
     * @param request
     * @return 有权限则返回密钥,无则不返回
     */
    @RequestMapping(value = "/getSecret/{symbol}",method = RequestMethod.GET)
    public BasicJson getSsidSecret(@PathVariable String symbol,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        SsidSecretEntity ssidSecretEntity;

        String phone= (String) request.getSession().getAttribute("phone");
        if (symbol!=null&&!symbol.equals(""))
        {
            try
            {
                ssidSecretEntity=ssidSecretService.getSelfBySymbol(symbol);
                if (ssidSecretEntity==null||ssidSecretEntity.getControlId()==null)
                {
                    logger.info("配置错误：密钥未配置！");
                    basicJson.getErrorMsg().setDescription("该密钥未配置!");
                    return basicJson;
                }
            }
            catch (Exception e)
            {
                logger.error("配置错误：获取门禁密钥异常,该ssid不存在!:"+e.getMessage());
                basicJson.getErrorMsg().setDescription("该ssid不存在!");
                return basicJson;
            }
        }
        else
        {
            logger.info("软件错误：SSID为空,APP未传递symbol值！");
            basicJson.getErrorMsg().setCode("100000");
            basicJson.getErrorMsg().setDescription("SSID不能为空");
            return basicJson;
        }

        ArrayList<Integer> ids;
        try
        {
            ids = authorityService.getAuthorityIDsByPhoneType(phone, ssidSecretEntity.getControlType());
        }
        catch (Exception e)
        {
            logger.error("配置错误：获取密钥失败,获取权限时异常:"+e.getMessage());
            basicJson.getErrorMsg().setDescription("获取密钥失败");
            return basicJson;
        }

        if (ids==null)
        {
            logger.error("配置错误：没有门禁权限");
            basicJson.getErrorMsg().setDescription("您没有该门禁权限");
            return basicJson;
        }

        if(ids.contains(ssidSecretEntity.getControlId()))
        {
            basicJson.setJsonString(ssidSecretEntity);
        }
        else
        {
            logger.error("配置错误：没有门禁权限");
            basicJson.getErrorMsg().setDescription("您没有该门禁权限");
            return basicJson;
        }

        basicJson.setStatus(true);
        logger.info("开锁逻辑已经走通，锁应该已开，若未开，请调查硬件原因");
        return basicJson;
    }

    /**
     * 获取app用户有权限的所有密钥
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAllowSecret")
    public BasicJson getAllAuthSecret(HttpServletRequest request)
    {
        String phone= (String) request.getSession().getAttribute("phone");
        BasicJson basicJson=new BasicJson(false);

        ArrayList<SsidSecret> ssidSecrets=new ArrayList<>();
        try
        {
            for (Integer villageID : authorityService.getAuthorityIDsByPhoneType(phone, SsidControlType.VILLAGE))
            {
                ArrayList<SsidSecret> temp = ssidSecretService.getByControlIdControlType(villageID, SsidControlType.VILLAGE);
                if (temp != null)
                {
                    temp.stream().filter(ssidSecretEntity -> !ssidSecrets.contains(ssidSecretEntity)).forEach(ssidSecrets::add);
                }
            }
            for (Integer buildingID : authorityService.getAuthorityIDsByPhoneType(phone, SsidControlType.BUILDING))
            {
                ArrayList<SsidSecret> temp = ssidSecretService.getByControlIdControlType(buildingID, SsidControlType.BUILDING);
                if (temp != null)
                {
                    temp.stream().filter(ssidSecretEntity -> !ssidSecrets.contains(ssidSecretEntity)).forEach(ssidSecrets::add);
                }
            }
            for (Integer brakeID : authorityService.getAuthorityIDsByPhoneType(phone, SsidControlType.BRAKE))
            {
                ArrayList<SsidSecret> temp = ssidSecretService.getByControlIdControlType(brakeID, SsidControlType.BRAKE);
                if (temp != null)
                {
                    temp.stream().filter(ssidSecretEntity -> !ssidSecrets.contains(ssidSecretEntity)).forEach(ssidSecrets::add);
                }
            }
            if (ssidSecrets.size()>0)
                basicJson.setJsonString(ssidSecrets);
            else
            {
                basicJson.getErrorMsg().setDescription("您没有门禁权限");
                return basicJson;
            }
        }
        catch (Exception e)
        {
            logger.error("app获取所有权限密钥失败:" + e.getMessage());
            basicJson.getErrorMsg().setDescription("获取密钥失败");
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }


    /**
     * 上传门禁记录
     * @param openDoorRecordEntity 门禁记录实体
     * @param request
     * @return 返回操作状态
     */
    @RequestMapping(value = "/uploadDoorLog")
    public BasicJson uploadDoorLog(OpenDoorRecordEntity openDoorRecordEntity,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson();
        HttpSession httpSession=request.getSession();

        openDoorRecordEntity.setOpenTime(System.currentTimeMillis());
        openDoorRecordEntity.setPhone((String) httpSession.getAttribute("phone"));
        try
        {
            baseService.save(openDoorRecordEntity);
        }
        catch (Exception e)
        {
            logger.error("上传门禁记录异常:"+e.getMessage());
            basicJson.getErrorMsg().setDescription("保存出错-"+e.getMessage());
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }
}
