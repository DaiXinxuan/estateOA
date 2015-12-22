package estate.service;

import estate.entity.database.ApkLogEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;

/**
 * Created by kangbiao on 15-11-22.
 *
 */
public interface ApkLogService
{
    ApkLogEntity getByVersionCode(String code);

    /**
     * 获取最新版本的apk
     * @return
     */
    ApkLogEntity getNewestApk();


    TableData getList(TableFilter tableFilter);
}
