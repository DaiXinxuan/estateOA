package estate.dao;

import estate.entity.database.AppUserEntity;
import estate.entity.database.OwnerEntity;
import estate.entity.database.UserInfoEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-4.
 * 用户访问接口
 */
public interface UserDao
{

    /**
     * 获取业主信息列表
     * @param tableFilter
     * @return
     */
    TableData getOwnerList(TableFilter tableFilter);

    /**
     * 获取app用户信息列表
     * @param tableFilter
     * @return
     */
    TableData getAppUserList(TableFilter tableFilter);

    /**
     * 通过用户类型和电话号码获取用户信息
     * @param phone
     * @param type
     * @return 返回对应的用户实体
     */
    Object getUserInfoByPhoneRole(String phone, int type);

    /**
     * 通过用户的电话和类型删除用户
     * @param phone
     * @param type
     */
    void deleteUserByPhone(String phone,byte type);

    /**
     * 获取所有的app用户
     * @return
     */
    ArrayList<AppUserEntity> getAllAppUser();

    /**
     * 通过用户的电话号码获取用户详细信息
     * @param phone
     * @return
     */
    UserInfoEntity getUserDetailByPhone(String phone);

    /**
     * 获取所有业主
     * @return
     */
    ArrayList<OwnerEntity> getAllOwner();

}
