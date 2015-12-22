package estate.service;

import estate.entity.database.ConsoleUserEntity;
import estate.entity.json.Select2;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-11-11.
 * 管理员功能服务层
 */
public interface AdminService
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
     * 获取select2形式的组列表
     * @return
     */
    ArrayList<Select2> getGroupList();

    /**
     * 通过组id获取该组的所有成员
     * @param id
     * @return
     */
    ArrayList<ConsoleUserEntity> getAdminByGroupID(Integer id);
}
