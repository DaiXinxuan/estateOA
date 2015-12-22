package estate.dao.impl;

import estate.dao.BrakeDao;
import estate.entity.database.BrakeEntity;
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
 * Created by kangbiao on 15-10-15.
 *
 */
@Repository("brakeDao")
public class BrakeDaoImpl implements BrakeDao
{

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public TableData getList(TableFilter tableFilter)
    {
        Session session=getSession();
        TableData tableData=new TableData();
        ArrayList<BrakeEntity> entities;
        Query query;

        StringBuilder hql=new StringBuilder("from BrakeEntity t where 1=1 ");
        if (tableFilter.getSearchValue()!=null)
            hql.append(" and (t.code like('%")
                    .append(tableFilter.getSearchValue())
                    .append("%') or t.name like('%")
                    .append(tableFilter.getSearchValue())
                    .append("%'))");
        if (tableFilter.getVillageId()!=null)
            hql.append(" and t.villageId=").append(tableFilter.getVillageId());
        query=session.createQuery(hql.toString());
        Integer count=query.list().size();
        entities=(ArrayList<BrakeEntity>)query
                .setFirstResult(tableFilter.getStart())
                .setMaxResults(tableFilter.getLength())
                .list();
        tableData.setRecordsFiltered(count);
        tableData.setJsonString(entities);
        return tableData;
    }

    @Override
    public ArrayList<BrakeEntity> getByVillageID(Integer id)
    {
        Session session=getSession();
        String hql="from BrakeEntity t where t.villageId=:id";
        List list=session.createQuery(hql).setInteger("id",id).list();
        if (list.size()>0)
            return (ArrayList<BrakeEntity>) list;
        return null;
    }
}
