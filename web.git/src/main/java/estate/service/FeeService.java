package estate.service;

import estate.entity.json.TableData;
import estate.entity.json.TableFilter;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-15.
 * 费用接口,提供物业费,服务费和车位费的录入和查询功能
 */
public interface FeeService
{

    /**
     * 根据datatable过滤条件返回datatable格式的项目列表
     * @param tableFilter
     * @return
     */
    TableData feeList(TableFilter tableFilter,byte feeType);

    /**
     * 根据费用类型和id删除费用,物业费会检查是否有依赖
     * @param feeType
     * @param id
     */
    void deleteFee(byte feeType,Integer id);


    /**
     * 将费用和楼栋下的所有物业绑定在一起
     * @param buildingIDs
     * @param feeItemID
     */
    void relateBuilding(ArrayList<Integer> buildingIDs,Integer feeItemID);

    /**
     * 通过园区id和车位类型获取费用信息,用来防止对一个园区添加多个相同类型的车位费用信息
     * @param villageID
     * @param type
     * @return
     */
    Object getParkLotFeeByVillageIdType(Integer villageID,String type);

}
