package estate.controller;

import estate.common.config.ParkLot;
import estate.common.config.PropertyType;
import estate.entity.database.*;
import estate.entity.json.BasicJson;
import estate.entity.statis.PieData;
import estate.entity.statis.SystemInfo;
import estate.service.BaseService;
import estate.service.ParkLotService;
import estate.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Basic;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by kangbiao on 15-11-2.
 *
 */
@RestController
@RequestMapping("/web/statis")
public class StatisController
{

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private ParkLotService parkLotService;

    @RequestMapping(value = "/parkLot")
    public BasicJson getParkLotData(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);

        try
        {
            PieData pieData1=new PieData();
            pieData1.setLabel("小型车位");
            pieData1.setValue(parkLotService.countByType(ParkLot.SMALL));
            pieData1.setHighlight("#5AD3D1");
            pieData1.setColor("#46BFBD");

            PieData pieData2=new PieData();
            pieData2.setLabel("中型车位");
            pieData2.setValue(parkLotService.countByType(ParkLot.MEDIUM));
            pieData2.setHighlight("#A8B3C5");
            pieData2.setColor("#949FB1");

            PieData pieData3=new PieData();
            pieData3.setLabel("大型车位");
            pieData3.setValue(parkLotService.countByType(ParkLot.LARGE));
            pieData3.setHighlight("#616774");
            pieData3.setColor("#4D5360");

            ArrayList<PieData> pieDatas=new ArrayList<>();
            pieDatas.add(pieData1);
            pieDatas.add(pieData2);
            pieDatas.add(pieData3);
            basicJson.setJsonString(pieDatas);
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("获取车位统计信息失败");
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }

    @RequestMapping(value = "/property")
    public BasicJson getPropertyData(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);

        try
        {
            PieData pieData1 = new PieData();
            pieData1.setLabel("商户");
            pieData1.setValue(propertyService.getNumByType(PropertyType.SHOP));
            pieData1.setHighlight("#FF5A5E");
            pieData1.setColor("#F7464A");

            PieData pieData2 = new PieData();
            pieData2.setLabel("住宅");
            pieData2.setValue(propertyService.getNumByType(PropertyType.APPARTEMENT));
            pieData2.setColor("#FDB45C");
            pieData2.setHighlight("#FFC870");

            ArrayList<PieData> pieDatas = new ArrayList<>();
            pieDatas.add(pieData1);
            pieDatas.add(pieData2);
            basicJson.setJsonString(pieDatas);
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("获取物业统计信息失败");
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 获取系统状态
     * @param request
     * @return
     */
    @RequestMapping(value = "/systemInfo")
    public BasicJson getSystemInfo(HttpServletRequest request)
    {
        BasicJson basicJson=new BasicJson(false);

        SystemInfo systemInfo=new SystemInfo();
        try
        {
            systemInfo.setAppNum(baseService.count(AppUserEntity.class));
            systemInfo.setComplainNum(baseService.count(ComplainEntity.class));
            systemInfo.setRepairNum(baseService.count(RepairEntity.class));
            systemInfo.setOwnerNum(baseService.count(OwnerEntity.class));
            systemInfo.setVillageNum(baseService.count(VillageEntity.class));
            systemInfo.setRepairManNum(baseService.count(RepairManEntity.class));
            basicJson.setJsonString(systemInfo);
        }
        catch (Exception e)
        {
            basicJson.getErrorMsg().setCode(e.getMessage());
            basicJson.getErrorMsg().setDescription("获取数据失败");
            return basicJson;
        }

        basicJson.setStatus(true);
        return basicJson;
    }
}
