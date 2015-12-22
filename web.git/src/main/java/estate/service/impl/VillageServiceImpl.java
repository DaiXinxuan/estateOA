package estate.service.impl;

import estate.dao.BaseDao;
import estate.dao.VillageDao;
import estate.entity.database.VillageEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.VillageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kangbiao on 15-10-15.
 *
 */
@Service("villageService")
public class VillageServiceImpl implements VillageService
{

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private VillageDao villageDao;

    @Override
    public TableData getList(TableFilter tableFilter)
    {
        TableData tableData=villageDao.getList(tableFilter);
//        ArrayList<VillageEntity> villageEntities= (ArrayList<VillageEntity>) tableData.getJsonString();
//        for (VillageEntity villageEntity:villageEntities)
//        {
//            SsidSecretEntity ssidSecretEntity=ssidSecretDao.
//                    getByControTypeControId(villageEntity.getId(), SsidControlType.VILLAGE);
//            if (ssidSecretEntity==null)
//                villageEntity.setSymbol(null);
//            else villageEntity.setSymbol(ssidSecretEntity.getSymbol());
//        }
        tableData.setRecordsTotal(baseDao.count(VillageEntity.class));
        return tableData;
    }
}
