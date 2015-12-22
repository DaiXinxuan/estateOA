package estate.dao;

import estate.entity.database.PropertyBillEntity;
import estate.entity.database.UserBillEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by kangbiao on 15-10-6.
 *
 */
public interface BillDao
{

    /**
     * 返回datatable形式的数据
     * @param tableFilter
     * @return
     */
    TableData getList(TableFilter tableFilter);

    /**
     * 根据物业id获取不同状态的账单
     * @param propertyID
     * @param status 为null则返回所有状态
     * @param startTime 查询开始日期,可以为null
     * @param endTime 查询结束日期,可以为null
     * @return
     */
    ArrayList<PropertyBillEntity> getPropertyBillByPropertyID(Integer propertyID,Byte status,Long startTime,Long endTime);

    /**
     * 通过用户电话等返回用户的账单
     * @param phone
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    ArrayList<UserBillEntity> getUserBillByPhone(String phone,Byte status,Long startTime,Long endTime);

}
