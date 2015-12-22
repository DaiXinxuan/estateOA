package estate.controller;

import estate.common.util.LogUtil;
import estate.entity.database.ComplainEntity;
import estate.entity.json.BasicJson;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.BaseService;
import estate.service.ComplainService;
import estate.service.PictureService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kangbiao on 15-9-16.
 *
 */
@RestController
@RequestMapping("/web/complain")
public class ComplainController
{

    Logger logger= LogUtil.getLogger(this.getClass());

    @Autowired
    private ComplainService complainService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private PictureService pictureService;

    @RequestMapping(value = "/list")
    public TableData getList(TableFilter tableFilter,HttpServletRequest request)
    {
        if (request.getParameter("search[value]")!=null)
            tableFilter.setSearchValue(request.getParameter("search[value]"));
        else
            tableFilter.setSearchValue("");
        try
        {
            return complainService.getList(tableFilter);
        }
        catch (Exception e)
        {
            logger.error("获取投诉列表失败:"+e.getMessage());
            return null;
        }
    }

    /**
     * 根据投诉id删除删除该条投诉
     * @param complainID
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete/{complainID}")
    public BasicJson deleteComplain(@PathVariable Integer complainID,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson();

        if (complainID==null)
        {
            basicJson.getErrorMsg().setDescription("参数错误!");
            return basicJson;
        }

        ComplainEntity complainEntity=new ComplainEntity();
        complainEntity.setId(complainID);
        try
        {
            baseService.delete(complainEntity);
        }
        catch (Exception e)
        {
            logger.error("删除投诉失败:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("删除失败");
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 获取投诉图片列表
     * @param complainID
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPathsByID/{complainID}")
    public BasicJson getPathByID(@PathVariable Integer complainID,HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson();
        try
        {
            ComplainEntity complainEntity = (ComplainEntity) baseService.get(complainID, ComplainEntity.class);
            basicJson.setJsonString(pictureService.getPathsByIDs(complainEntity.getImageIdList(),request));
        }
        catch (Exception e)
        {
            logger.error("获取投诉图片失败:"+e.getMessage());
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("获取图片列表失败");
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }
}
