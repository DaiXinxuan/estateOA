package estate.service;

import estate.entity.database.ParkingLotEntity;
import estate.entity.database.ParklotOwnerInfoEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-10-15.
 *
 */
public interface ParkLotService
{
    /**
     * 返回datatable数据
     * @param tableFilter
     * @return
     */
    TableData getList(TableFilter tableFilter);

    /**
     * 根据车位id获取绑定关系
     * @param id
     * @return
     */
    ArrayList<ParklotOwnerInfoEntity> getByParkLotID(Integer id);

    /**
     * 通过道闸id获取所有的车位
     * @param id
     * @return
     */
    ArrayList<ParkingLotEntity> getByBrakeID(Integer id);

    /**
     * 通过用户的电话获取车位绑定关系
     * @param phone
     * @return
     */
    ArrayList<ParklotOwnerInfoEntity> getByPhone(String phone);

    /**
     * 通过车位类型计数
     * @param type
     * @return
     */
    Integer countByType(byte type);
}
