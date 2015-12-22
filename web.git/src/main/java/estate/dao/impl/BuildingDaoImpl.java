package estate.dao.impl;

import estate.dao.BuildingDao;
import estate.entity.database.BuildingEntity;
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
 * Created by kangbiao on 15-9-22.
 *
 */
@Repository("buildingDao")
public class BuildingDaoImpl implements BuildingDao
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
        ArrayList<BuildingEntity> entities;
        Query query;

        StringBuilder hql=new StringBuilder("from BuildingEntity t where 1=1 ");

        if (tableFilter.getSearchValue()!=null)
            hql.append(" and (t.buildingCode like ('%")
                    .append(tableFilter.getSearchValue())
                    .append("%') or t.buildingName like ('%")
                    .append(tableFilter.getSearchValue())
                    .append("%')) ");
        if (tableFilter.getVillageId()!=null)
            hql.append(" and t.villageId=").append(tableFilter.getVillageId());

        query=session.createQuery(hql.toString());

        Integer count=query.list().size();
        entities=(ArrayList<BuildingEntity>)query
                .setFirstResult(tableFilter.getStart())
                .setMaxResults(tableFilter.getLength())
                .list();
        tableData.setRecordsFiltered(count);
        tableData.setJsonString(entities);
        return tableData;
    }

    @Override
    public BuildingEntity getByCode(String code)
    {
        Session session=getSession();
        String hql="from BuildingEntity b where b.buildingCode=:code";
        List list=session.createQuery(hql).setString("code",code).list();
        if (list.size()>0)
            return (BuildingEntity)list.get(0);
        else
            return null;
    }

    @Override
    public ArrayList<BuildingEntity> getAllBuildingsByVillageId(Integer id)
    {
        Session session=getSession();
        String hql="from BuildingEntity t where t.villageId=:id";
        List list= session.createQuery(hql).setInteger("id",id).list();
        if (list.size()>0)
            return (ArrayList<BuildingEntity>) list;
        return null;
    }
}
