package estate.service;

import estate.entity.database.AppUserEntity;
import estate.entity.database.OwnerEntity;
import estate.entity.database.UserInfoEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-4.
 * 用户服务
 */
public interface UserService
{

    /**
     * 获取业主信息
     * @param tableFilter
     * @return
     */
    TableData getOwnerList(TableFilter tableFilter);

    /**
     * 获取app用户列表,同时返回每个用户绑定的房产id(1,2,3)
     * @param tableFilter
     * @return
     */
    TableData getAppUserList(TableFilter tableFilter);

    /**
     * 通过用户的电话和类型获取用户信息
     * @param phone
     * @param type
     * @return
     */
    Object getUserInfoByPhoneRole(String phone, int type);

    /**
     * 通过APP用户手机号码获取用户详细信息
     * @param phone
     * @return
     */
    UserInfoEntity getUserDetailByPhone(String phone);

    /**
     * 删除业主
     * @param phone
     */
    void deleteOwner(String phone,byte type);

    /**
     * 一次性获取所有的app用户
     * @return
     */
    ArrayList<AppUserEntity> getAllAppUser();

    /**
     * 获取系统中所有的业主信息
     * @return
     */
    ArrayList<OwnerEntity> getAllOwner();
}
