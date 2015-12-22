package estate.service;

import estate.entity.database.UserBillEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-10-6.
 *
 */
public interface BillService
{

    /**
     * 获取用户账单列表
     * @param tableFilter
     * @return
     */
    TableData getList(TableFilter tableFilter);

    /**
     * 根据查询条件获取某个用户的账单
     * @param phone
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    ArrayList<UserBillEntity> getUserBill(String phone,Byte status,Long startTime,Long endTime);

    /**
     * 根据查询条件获取某个用户的物业账单,字符串组成
     * @param phone
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    String getPropertyBillString(String phone,Byte status,Long startTime,Long endTime);

    /**
     * 根据物业id生成这个物业的账单
     * @param id
     */
    void generateBillByPropertyID(Integer id);

    /**
     * 根绝用户电话生成某个用户的账单
     * @param phone
     */
    void generateUserBill(String phone);
}
