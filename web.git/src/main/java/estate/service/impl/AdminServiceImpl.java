package estate.service.impl;

import estate.dao.AdminDao;
import estate.dao.BaseDao;
import estate.entity.database.ConsoleGroupEntity;
import estate.entity.database.ConsoleUserEntity;
import estate.entity.display.Group;
import estate.entity.json.Select2;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import estate.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangbiao on 15-11-11.
 *
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService
{

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private BaseDao baseDao;

    @Override
    public TableData getGroupList(TableFilter tableFilter)
    {
        TableData tableData=adminDao.getGroupList(tableFilter);
        ArrayList<Group> groups=new ArrayList<>();
        ArrayList<ConsoleGroupEntity> consoleGroupEntities= (ArrayList<ConsoleGroupEntity>) tableData.getJsonString();
        for (ConsoleGroupEntity consoleGroupEntity:consoleGroupEntities)
        {
            Group group=new Group(consoleGroupEntity);
            groups.add(group);
        }
        tableData.setJsonString(groups);
        tableData.setRecordsTotal(baseDao.count(ConsoleGroupEntity.class));
        return tableData;
    }

    @Override
    public TableData getAdminList(TableFilter tableFilter)
    {
        TableData tableData=adminDao.getAdminList(tableFilter);
        tableData.setRecordsTotal(baseDao.count(ConsoleUserEntity.class));
        return tableData;
    }

    @Override
    public ArrayList<Select2> getGroupList()
    {
        List<ConsoleGroupEntity> consoleGroupEntities= (List<ConsoleGroupEntity>) baseDao.getAll(ConsoleGroupEntity.class);
        if (consoleGroupEntities==null)
            return null;
        ArrayList<Select2> select2s=new ArrayList<>();
        for (ConsoleGroupEntity consoleGroupEntity:consoleGroupEntities)
        {
            Select2 select2=new Select2();
            select2.setId(String.valueOf(consoleGroupEntity.getId()));
            select2.setText(consoleGroupEntity.getName());
            select2s.add(select2);
        }
        return select2s;
    }

    @Override
    public ArrayList<ConsoleUserEntity> getAdminByGroupID(Integer id)
    {
        return adminDao.getAdminByGroupID(id);
    }
}
