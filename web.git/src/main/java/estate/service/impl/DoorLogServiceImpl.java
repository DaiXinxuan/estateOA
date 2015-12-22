package estate.service.impl;

import estate.dao.DoorLogDao;
import estate.entity.database.OpenDoorRecordEntity;
import estate.service.DoorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-11-3.
 *
 */
@Service("doorLogService")
public class DoorLogServiceImpl implements DoorLogService
{

    @Autowired
    private DoorLogDao doorLogDao;

    @Override
    public ArrayList<OpenDoorRecordEntity> getByPhoneTimeNum(String phone, Long startTime, Long endTime, Integer num)
    {
        return doorLogDao.getByPhoneTimeNum(phone,startTime,endTime,num);
    }
}
