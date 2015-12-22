package estate.dao;

import estate.entity.database.ConsoleUserEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-11-11.
 * 后台管理数据库访问层
 */
public interface AdminDao
{
    /**
     * 获取组列表
     * @param tableFilter
     * @return 组信息
     */
    TableData getGroupList(TableFilter tableFilter);

    /**
     * 获取管理员列表
     * @param tableFilter
     * @return 管理员信息
     */
    TableData getAdminList(TableFilter tableFilter);

    /**
     * 通过组id获取该组的所有成员
     * @param id
     * @return
     */
    ArrayList<ConsoleUserEntity> getAdminByGroupID(Integer id);
}
