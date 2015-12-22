package estate.dao;

import estate.entity.json.TableData;
import estate.entity.json.TableFilter;

/**
 * Created by kangbiao on 15-9-15.
 *
 */
public interface FeeItemDao
{
    TableData getList(TableFilter tableFilter,byte feeType);


    /**
     * 通过费用类型和费用id删除费用.
     * @param feeType
     * @param id
     */
    void deleteByFeeTypeID(byte feeType,Integer id);


    /**
     * 通过园区id和车位费类型返回车位费信息
     * @param villageID
     * @param type
     * @return
     */
    Object getParkLotByVillageIdType(Integer villageID,String type);

}
