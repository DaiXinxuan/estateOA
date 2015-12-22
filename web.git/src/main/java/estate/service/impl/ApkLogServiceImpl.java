package estate.service.impl;

import estate.dao.ApkLogDao;
import estate.dao.BaseDao;
import estate.entity.database.ApkLogEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.ApkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kangbiao on 15-11-22.
 *
 */
@Service("apkLotService")
public class ApkLogServiceImpl implements ApkLogService
{
    @Autowired
    private ApkLogDao apkLogDao;
    @Autowired
    private BaseDao baseDao;

    @Override
    public ApkLogEntity getByVersionCode(String code)
    {
        return apkLogDao.getByCode(code);
    }

    @Override
    public ApkLogEntity getNewestApk()
    {
        return apkLogDao.getNewestApk();
    }

    @Override
    public TableData getList(TableFilter tableFilter)
    {
        TableData tableData=apkLogDao.getList(tableFilter);
        tableData.setRecordsTotal(baseDao.count(ApkLogEntity.class));
        return tableData;
    }
}
