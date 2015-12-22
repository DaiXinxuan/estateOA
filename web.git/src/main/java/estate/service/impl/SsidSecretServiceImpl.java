package estate.service.impl;

import estate.common.config.SecretType;
import estate.common.config.SsidControlType;
import estate.dao.BaseDao;
import estate.dao.SsidSecretDao;
import estate.entity.database.BrakeEntity;
import estate.entity.database.BuildingEntity;
import estate.entity.database.SsidSecretEntity;
import estate.entity.database.VillageEntity;
import estate.entity.display.SsidSecret;
import estate.entity.json.Select2;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.SsidSecretService;
import org.apache.bcel.verifier.statics.LONG_Upper;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-21.
 *
 */
@Service("ssidSecretService")
public class SsidSecretServiceImpl implements SsidSecretService
{
    @Autowired
    private SsidSecretDao ssidSecretDao;
    @Autowired
    private BaseDao baseDao;

    @Override
    public SsidSecretEntity getSelfBySymbol(String symbol)
    {
        return ssidSecretDao.getBySymbol(symbol);
    }

    @Override
    public TableData getList(TableFilter tableFilter)
    {

        TableData tableData=ssidSecretDao.getList(tableFilter);
        ArrayList<SsidSecretEntity> ssidSecretEntities= (ArrayList<SsidSecretEntity>) tableData.getJsonString();
        ArrayList<SsidSecret> ssidSecrets=new ArrayList<>();
        for (SsidSecretEntity ssidSecretEntity:ssidSecretEntities)
        {
            SsidSecret ssidSecret=new SsidSecret(ssidSecretEntity);
            if (ssidSecretEntity.getControlId()!=null)
            {
                if (ssidSecretEntity.getControlType() == SsidControlType.VILLAGE)
                    ssidSecret.setControlEntity(baseDao.get(ssidSecretEntity.getControlId(), VillageEntity.class));
                else if (ssidSecretEntity.getControlType() == SsidControlType.BUILDING)
                    ssidSecret.setControlEntity(baseDao.get(ssidSecretEntity.getControlId(), BuildingEntity.class));
                else if (ssidSecretEntity.getControlType() == SsidControlType.BRAKE)
                    ssidSecret.setControlEntity(baseDao.get(ssidSecretEntity.getControlId(), BrakeEntity.class));
            }
            else
            {
                ssidSecret.setControlEntity("该密钥未使用");
            }
            ssidSecrets.add(ssidSecret);
        }
        tableData.setJsonString(ssidSecrets);
        tableData.setRecordsTotal(baseDao.count("SsidSecretEntity"));
        return tableData;

    }

    @Override
    public ArrayList<Select2> getSelect2()
    {
        ArrayList<SsidSecretEntity> ssidSecretEntities = ssidSecretDao.getNotUsed();
        if (ssidSecretEntities==null)
            return null;
        ArrayList<Select2> select2s=new ArrayList<>();
        for (SsidSecretEntity ssidSecretEntity:ssidSecretEntities)
        {
            Select2 select2=new Select2();
            select2.setId(String.valueOf(ssidSecretEntity.getId()));
            if (ssidSecretEntity.getType()== SecretType.LANYA)
                select2.setText(ssidSecretEntity.getSymbol()+"&ensp;&ensp;蓝牙");
            else if (ssidSecretEntity.getType()==SecretType.WIFI)
                select2.setText(ssidSecretEntity.getSymbol()+"&ensp;&ensp;WIFI");

            select2s.add(select2);
        }
        return select2s;
    }

    @Override
    public ArrayList<SsidSecret> getByControlIdControlType(Integer controlID, byte controlType)
    {
        ArrayList<SsidSecretEntity> ssidSecretEntities= ssidSecretDao.getByControTypeControId(controlID, controlType);
        ArrayList<SsidSecret> ssidSecrets=new ArrayList<>();
        for (SsidSecretEntity ssidSecretEntity:ssidSecretEntities)
        {
            SsidSecret ssidSecret=new SsidSecret(ssidSecretEntity);
            if (controlType == SsidControlType.VILLAGE)
            {
                VillageEntity villageEntity=(VillageEntity)baseDao.get(ssidSecretEntity.getControlId(), VillageEntity.class);
                ssidSecret.setControlEntity(villageEntity.getName());
            }
            else if (controlType== SsidControlType.BUILDING)
            {
                BuildingEntity buildingEntity= (BuildingEntity) baseDao.get(ssidSecretEntity.getControlId(), BuildingEntity.class);
                ssidSecret.setControlEntity(buildingEntity.getBuildingName());
            }
            else if (controlType == SsidControlType.BRAKE)
            {
                BrakeEntity brakeEntity= (BrakeEntity) baseDao.get(ssidSecretEntity.getControlId(), BrakeEntity.class);
                ssidSecret.setControlEntity(brakeEntity.getName());
            }
            ssidSecrets.add(ssidSecret);
        }
        return ssidSecrets;
    }

}
