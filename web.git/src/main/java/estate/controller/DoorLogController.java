package estate.controller;

import estate.common.util.Convert;
import estate.common.util.LogUtil;
import estate.entity.json.BasicJson;
import estate.service.DoorLogService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kangbiao on 15-11-3.
 * 门禁记录控制器
 */
@RestController
@RequestMapping("/web/doorLog")
public class DoorLogController
{
    Logger logger= LogUtil.getLogger(this.getClass());
    @Autowired
    private DoorLogService doorLogService;

    /**
     * 通过电话号码和时间区间获取门禁记录,最多返回二十条
     * @param request
     * @return
     */
    @RequestMapping(value = "/getByPhone/{phone}")
    public BasicJson getDoorLogByPhone(@PathVariable(value = "phone") String phone,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        try
        {
            Long startTime= Convert.time2num(request.getParameter("startTime"));
            Long endTime=Convert.time2num(request.getParameter("endTime"));
            basicJson.setJsonString(doorLogService.getByPhoneTimeNum(phone,startTime,endTime,20));
        }
        catch (Exception e)
        {
            logger.error("获取门禁记录时参数错误:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("获取门禁记录出错");
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }
}
