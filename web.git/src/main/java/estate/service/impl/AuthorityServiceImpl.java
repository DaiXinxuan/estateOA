package estate.service.impl;

import estate.common.config.BindStatus;
import estate.common.config.SsidControlType;
import estate.common.util.LogUtil;
import estate.dao.BrakeDao;
import estate.dao.ParkLotOwnerInfoDao;
import estate.dao.PropertyOwnerInfoDao;
import estate.entity.database.ParklotOwnerInfoEntity;
import estate.entity.database.PropertyOwnerInfoEntity;
import estate.service.AuthorityService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-21.
 *
 */
@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService
{
    private Logger logger= LogUtil.getLogger(this.getClass());
    @Autowired
    private PropertyOwnerInfoDao propertyOwnerInfoDao;
    @Autowired
    private ParkLotOwnerInfoDao parkLotOwnerInfoDao;

    @Override
    public ArrayList<Integer> getAuthorityIDsByPhoneType(String phone, Byte type)
    {
        ArrayList<Integer> ids=new ArrayList<>();
        if (type== SsidControlType.VILLAGE||type==SsidControlType.BUILDING)
        {
            ArrayList<PropertyOwnerInfoEntity> propertyOwnerInfoEntities = propertyOwnerInfoDao.getByPhoneStatus(phone, BindStatus.CHECKED);
            if (propertyOwnerInfoEntities!=null)
            {
                if (type == SsidControlType.BUILDING)
                {
                    for (PropertyOwnerInfoEntity propertyOwnerInfoEntity : propertyOwnerInfoEntities)
                    {
                        if (!ids.contains(propertyOwnerInfoEntity.getPropertyEntity().getBuildingId()))
                            ids.add(propertyOwnerInfoEntity.getPropertyEntity().getBuildingId());
                    }
                } else
                {
                    for (PropertyOwnerInfoEntity propertyOwnerInfoEntity : propertyOwnerInfoEntities)
                    {
                        if (!ids.contains(propertyOwnerInfoEntity.getPropertyEntity().getBuildingEntity().getVillageId()))
                            ids.add(propertyOwnerInfoEntity.getPropertyEntity().getBuildingEntity().getVillageId());
                    }
                }
            }else{
                logger.info("手机号有误");
            }
        }
        else if (type==SsidControlType.BRAKE)
        {
            ArrayList<ParklotOwnerInfoEntity> parklotOwnerInfoEntities=parkLotOwnerInfoDao.getByPhone(phone);
            if (parklotOwnerInfoEntities!=null)
            {
                for (ParklotOwnerInfoEntity parklotOwnerInfoEntity : parklotOwnerInfoEntities)
                {
                    if (!ids.contains(parklotOwnerInfoEntity.getParkingLotEntity().getBrakeId()))
                        ids.add(parklotOwnerInfoEntity.getParkingLotEntity().getBrakeId());
                }
            }else{
                logger.info("手机号有误");
            }
        }
        else {
            logger.error("密钥控制类型不为园区，住房，闸道的任一种！");
            return null;
        }
        return ids;
    }
}
