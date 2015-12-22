package estate.controller;

import estate.common.util.LogUtil;
import estate.entity.database.BrakeEntity;
import estate.entity.database.ParkingLotEntity;
import estate.entity.json.BasicJson;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.BaseService;
import estate.service.BrakeService;
import estate.service.ParkLotService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kangbiao on 15-10-14.
 * 道闸控制器
 */
@RestController
@RequestMapping("/web/brake")
public class BrakeController
{
    Logger logger= LogUtil.getLogger(this.getClass());

    @Autowired
    private BaseService baseService;
    @Autowired
    private BrakeService brakeService;
    @Autowired
    private ParkLotService parkLotService;

    /**
     * 获取道闸列表
     * @param tableFilter
     * @param request
     * @return
     */
    @RequestMapping(value = "/getList")
    public TableData getList(TableFilter tableFilter,HttpServletRequest request)
    {
        tableFilter.setSearchValue(request.getParameter("search[value]"));
        if (tableFilter.getSearchValue().equals(""))
            tableFilter.setSearchValue(null);
        try
        {
           return brakeService.getList(tableFilter);
        }
        catch (Exception e)
        {
            logger.error("获取道闸列表异常:"+e.getMessage());
            return null;
        }
    }


    /**
     * 增加一个道闸
     * @param brakeEntity
     * @param request
     * @return
     */
    @RequestMapping(value = "/add")
    public BasicJson add(BrakeEntity brakeEntity,HttpServletRequest request)
    {
        BasicJson basicJson =new BasicJson();
        try
        {
            baseService.save(brakeEntity);
        }
        catch (Exception e)
        {
            logger.error("写入道闸到数据库失败"+e.getMessage());
            basicJson.getErrorMsg().setDescription("添加道闸信息失败\n详细信息:"+e.getMessage());
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 删除一个道闸
     * @param brakeID
     * @param request
     * @return 返回操作结果
     */
    @RequestMapping(value = "/delete/{brakeID}")
    public BasicJson delete(@PathVariable Integer brakeID,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson();
        try
        {
            if (parkLotService.getByBrakeID(brakeID) != null)
            {
                basicJson.getErrorMsg().setDescription("请先删除该道闸下的所有车位");
                return basicJson;
            }
            BrakeEntity brakeEntity=new BrakeEntity();
            brakeEntity.setId(brakeID);
            baseService.delete(brakeEntity);
        }
        catch (Exception e)
        {
            logger.error("删除道闸时发生异常:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("删除失败");
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }


    /**
     * 通过园区id返回所有的道闸信息
     * @param villageID
     * @param request
     * @return
     */
    @RequestMapping(value = "/getByVillageID/{villageID}")
    public BasicJson getByVillageID(@PathVariable Integer villageID,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson();
        try
        {
            basicJson.setJsonString(brakeService.getSelectListByVillageID(villageID));
        }
        catch (Exception e)
        {
            logger.error("根据园区获取道闸失败:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("获取道闸信息失败\n"+e.getMessage());
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }


}
