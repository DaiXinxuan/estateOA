package estate.service.impl;

import estate.dao.*;
import estate.entity.database.AppUserEntity;
import estate.entity.database.OwnerEntity;
import estate.entity.database.UserInfoEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-16.
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private BaseDao baseDao;

    public TableData getOwnerList(TableFilter tableFilter)
    {
        TableData tableData=userDao.getOwnerList(tableFilter);
        tableData.setRecordsTotal(baseDao.count(OwnerEntity.class));
        return tableData;
    }

    @Override
    public TableData getAppUserList(TableFilter tableFilter)
    {
        TableData tableData= userDao.getAppUserList(tableFilter);
        tableData.setRecordsTotal(baseDao.count(AppUserEntity.class));
        return tableData;
    }

    @Override
    public Object getUserInfoByPhoneRole(String phone, int type)
    {
        return userDao.getUserInfoByPhoneRole(phone, type);
    }

    @Override
    public UserInfoEntity getUserDetailByPhone(String phone)
    {
         return userDao.getUserDetailByPhone(phone);
    }

    @Override
    public void deleteOwner(String phone,byte type)
    {
        userDao.deleteUserByPhone(phone, type);
    }

    @Override
    public ArrayList<AppUserEntity> getAllAppUser()
    {
        return userDao.getAllAppUser();
    }

    @Override
    public ArrayList<OwnerEntity> getAllOwner()
    {
        return userDao.getAllOwner();
    }
}
