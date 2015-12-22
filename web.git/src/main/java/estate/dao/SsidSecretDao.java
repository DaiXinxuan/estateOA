package estate.dao;

import estate.entity.database.SsidSecretEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-21.
 *
 */
public interface SsidSecretDao
{
    /**
     * 通过ssid返回SsidSecretEntity实体
     * @param symbol
     * @return
     */
    SsidSecretEntity getBySymbol(String symbol);

    /**
     * 获取供datatable显示的数据
     * @param tableFilter
     * @return
     */
    TableData getList(TableFilter tableFilter);


    /**
     * 根据控制对象id和类型获取锁
     * @param contrlId
     * @param type
     * @return
     */
    ArrayList<SsidSecretEntity> getByControTypeControId(Integer contrlId, Byte type);

    /**
     * 获取所有未使用的密钥
     * @return
     */
    ArrayList<SsidSecretEntity> getNotUsed();
}
