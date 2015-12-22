package estate.dao.impl;

import estate.common.util.LogUtil;
import estate.dao.ParkLotDao;
import estate.entity.database.ParkingLotEntity;
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
@Repository("parkLotDao")
public class ParkLotDaoImpl implements ParkLotDao
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
        ArrayList<ParkingLotEntity> entities;
        Query query;

        StringBuilder hql=new StringBuilder("from ParkingLotEntity t where 1=1 ");
        if (tableFilter.getSearchValue()!=null)
            hql.append("and (t.code like('%")
                    .append(tableFilter.getSearchValue())
                    .append("%') or t.location like('%")
                    .append(tableFilter.getSearchValue()).append("%') )");
        if (tableFilter.getType()!=null)
            hql.append(" and t.type=").append(tableFilter.getType());
        if (tableFilter.getVillageId()!=null)
            hql.append(" and t.brakeEntity.villageId=").append(tableFilter.getVillageId());
        if (tableFilter.getControlId()!=null)
            hql.append(" and t.brakeId=").append(tableFilter.getControlId());
        query=session.createQuery(hql.toString());
        Integer count=query.list().size();
        entities=(ArrayList<ParkingLotEntity>)query
                .setFirstResult(tableFilter.getStart())
                .setMaxResults(tableFilter.getLength())
                .list();
        tableData.setRecordsFiltered(count);
        tableData.setJsonString(entities);
        return tableData;
    }

    @Override
    public Integer countByType(byte type)
    {
        Session session=getSession();
        String hql="from ParkingLotEntity t where t.type=:type";
        return session.createQuery(hql).setByte("type",type).list().size();
    }

    @Override
    public ArrayList<ParkingLotEntity> getByBrakeID(Integer id)
    {
        Session session=getSession();
        String hql="from ParkingLotEntity t where t.brakeId=:id";
        List list=session.createQuery(hql).setInteger("id",id).list();
        if (list.size()>0)
            return (ArrayList<ParkingLotEntity>) list;
        return null;
    }
}
