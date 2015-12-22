package estate.dao.impl;

import estate.dao.AdminDao;
import estate.entity.database.ConsoleGroupEntity;
import estate.entity.database.ConsoleUserEntity;
import estate.entity.database.PropertyEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangbiao on 15-11-11.
 *
 */
@Repository("adminDao")
public class AdminDaoImpl implements AdminDao
{

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }



    @Override
    public TableData getGroupList(TableFilter tableFilter)
    {
        Session session=getSession();
        TableData tableData=new TableData();
        ArrayList<ConsoleGroupEntity> entities;
        Query query;
        StringBuilder hql=new StringBuilder("from ConsoleGroupEntity t where 1=1 ");

        if (tableFilter.getSearchValue()!=null)
            hql.append(" and t.name like('%").append(tableFilter.getSearchValue()).append("%')");
        query=session.createQuery(hql.toString());
        Integer count=query.list().size();
        entities=(ArrayList<ConsoleGroupEntity>)query.setFirstResult(tableFilter.getStart()).setMaxResults(tableFilter
                .getLength()).list();
        tableData.setRecordsFiltered(count);
        tableData.setJsonString(entities);
        return tableData;
    }

    @Override
    public TableData getAdminList(TableFilter tableFilter)
    {
        Session session=getSession();
        TableData tableData=new TableData();
        ArrayList<ConsoleUserEntity> entities;
        Query query;
        StringBuilder hql=new StringBuilder("from ConsoleUserEntity t where 1=1 ");

        if (tableFilter.getSearchValue()!=null)
            hql.append(" and t.phone like('%").append(tableFilter.getSearchValue()).append("%')");
        query=session.createQuery(hql.toString());
        Integer count=query.list().size();
        entities=(ArrayList<ConsoleUserEntity>)query.setFirstResult(tableFilter.getStart()).setMaxResults(tableFilter
                .getLength()).list();
        tableData.setRecordsFiltered(count);
        tableData.setJsonString(entities);
        return tableData;
    }

    @Override
    public ArrayList<ConsoleUserEntity> getAdminByGroupID(Integer id)
    {
        Session session=getSession();
        String hql="from ConsoleUserEntity t where t.consoleGroupId=:id";
        List list=session.createQuery(hql).setInteger("id",id).list();
        if (list.size()>0)
            return (ArrayList<ConsoleUserEntity>) list;
        return null;
    }
}
