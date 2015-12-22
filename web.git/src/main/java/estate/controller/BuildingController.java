package estate.controller;

import estate.common.util.LogUtil;
import estate.entity.database.BuildingEntity;
import estate.entity.json.BasicJson;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.BaseService;
import estate.service.BuildingService;
import estate.service.PropertyService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kangbiao on 15-10-15.
 *
 */
@RestController
@RequestMapping("/web/building")
public class BuildingController
{

    Logger logger= LogUtil.getLogger(this.getClass());

    @Autowired
    private BuildingService buildingService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private PropertyService propertyService;

    @RequestMapping(value = "/getList")
    public TableData getList(TableFilter tableFilter,HttpServletRequest request)
    {
        tableFilter.setSearchValue(request.getParameter("search[value]"));
        if (tableFilter.getSearchValue().equals(""))
            tableFilter.setSearchValue(null);
        try
        {
            return buildingService.getList(tableFilter);
        }
        catch (Exception e)
        {
            logger.error("获取楼栋列表失败:"+e.getMessage());
            return null;
        }
    }

    /**
     * 增加楼栋信息
     * @param buildingEntity
     * @param request
     * @return
     */
    @RequestMapping(value = "/add")
    public BasicJson addBuilding(BuildingEntity buildingEntity ,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);
        try
        {
            baseService.save(buildingEntity);
        }
        catch (Exception e)
        {
            logger.error("增加楼栋失败:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("楼栋信息添加失败\n详细信息:"+e.getMessage());
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 删除楼栋,如果该园区下面有物业的话,则不能删除
     * @param buildingID
     * @return
     */
    @RequestMapping(value = "/delete/{buildingID}")
    public BasicJson delete(@PathVariable Integer buildingID)
    {
        BasicJson basicJson=new BasicJson();
        if (propertyService.getByBuildingID(buildingID)!=null)
        {
            basicJson.getErrorMsg().setDescription("删除失败,请先删除该楼栋下的所有物业");
            return basicJson;
        }
        BuildingEntity buildingEntity=new BuildingEntity();
        buildingEntity.setId(buildingID);
        try
        {
            baseService.delete(buildingEntity);
        }
        catch (Exception e)
        {
            logger.error("删除楼栋失败:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("删除失败");
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }
}
