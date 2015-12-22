package estate.dao;

import estate.entity.database.ParkingLotEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-10-15.
 *
 */
public interface ParkLotDao
{
    /**
     * 获取列表
     * @param tableFilter
     * @return
     */
    TableData getList(TableFilter tableFilter);

    /**
     * 通过类型计数
     * @param type
     * @return
     */
    Integer countByType(byte type);

    /**
     * 通过道闸id获取所有的车位
     * @param id
     * @return
     */
    ArrayList<ParkingLotEntity> getByBrakeID(Integer id);

}
