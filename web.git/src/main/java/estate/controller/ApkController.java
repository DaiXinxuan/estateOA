package estate.controller;

import estate.entity.database.ApkLogEntity;
import estate.entity.json.BasicJson;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.ApkLogService;
import estate.service.BaseService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kangbiao on 15-11-22.
 *
 */
@RestController
@RequestMapping("/web/apk")
public class ApkController
{

    @Autowired
    private ApkLogService apkLogService;
    @Autowired
    private BaseService baseService;

    @RequestMapping(value = "/getList")
    public TableData getList(TableFilter tableFilter)
    {
        try
        {
            return apkLogService.getList(tableFilter);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 删除apk
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public BasicJson delete(@PathVariable Integer id,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);

        try
        {
            ApkLogEntity apkLogEntity=new ApkLogEntity();
            apkLogEntity.setId(id);
            baseService.delete(apkLogEntity);
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("删除失败");
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }

}
