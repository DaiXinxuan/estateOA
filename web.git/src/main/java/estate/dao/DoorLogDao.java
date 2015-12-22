package estate.dao;

import estate.entity.database.OpenDoorRecordEntity;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-11-3.
 *
 */
public interface DoorLogDao
{
    /**
     * 根据用户电话,开始时间,结束时间获取指定数量的门禁记录
     * @param phone
     * @param startTime
     * @param endTime
     * @param num
     * @return
     */
    ArrayList<OpenDoorRecordEntity> getByPhoneTimeNum(String phone,Long startTime,Long endTime,Integer num);
}
