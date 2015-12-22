package estate.dao.impl;

import estate.dao.BaseDao;
import estate.dao.PropertyDao;
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
 * Created by kangbiao on 15-9-16.
 *
 */
@Repository("propertyDao")
public class PropertyDaoImpl implements PropertyDao
{
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private BaseDao baseDao;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public TableData getList(TableFilter tableFilter)
    {
        Session session=getSession();
        TableData tableData=new TableData();
        ArrayList<PropertyEntity> entities;
        Query query;
        StringBuilder hql=new StringBuilder("from PropertyEntity t where 1=1 ");

        if (tableFilter.getSearchValue()!=null)
            hql.append(" and (t.code like('%")
                    .append(tableFilter.getSearchValue())
                    .append("%') or t.location like('%")
                    .append(tableFilter.getSearchValue()).append("%')) ");
        if (tableFilter.getType()!=null)
            hql.append(" and t.type=").append(tableFilter.getType());
        if (tableFilter.getStatus()!=null)
            hql.append(" and t.status=").append(tableFilter.getStatus());
        if (tableFilter.getVillageId()!=null)
            hql.append(" and t.villageId=").append(tableFilter.getVillageId());
        if(tableFilter.getControlId()!=null)
            hql.append(" and t.buildingId=").append(tableFilter.getControlId());

        query=session.createQuery(hql.toString());
        Integer count=query.list().size();
        entities=(ArrayList<PropertyEntity>)query.setFirstResult(tableFilter.getStart()).setMaxResults(tableFilter
                .getLength()).list();
        tableData.setRecordsFiltered(count);
        tableData.setJsonString(entities);
        return tableData;
    }

    @Override
    public ArrayList<PropertyEntity> getPropertyByBuildingID(Integer id)
    {
        Session session=getSession();
        String hql="from PropertyEntity t where t.buildingId=:id";
        List list=session.createQuery(hql).setInteger("id", id).list();
        if (list.size()>0)
            return (ArrayList<PropertyEntity>) list;
        return null;
    }

    @Override
    public ArrayList<PropertyEntity> getPropertiesByPhoneRole(String phone, Byte role)
    {
        Session session=getSession();
        String hql;
        List list;
        if (role==null)
        {
            hql = "select t.propertyEntity from PropertyOwnerInfoEntity t where t.phone=:phone";
            list=session.createQuery(hql).setString("phone",phone).list();
        }
        else
        {
            hql = "select t.propertyEntity from PropertyOwnerInfoEntity t where t.phone=:phone and t.userRole=:role";
            list=session.createQuery(hql).setString("phone",phone).setByte("role",role).list();
        }

        if (list.size()>0)
            return (ArrayList<PropertyEntity>) list;
        else return null;
    }

    @Override
    public PropertyEntity getByCode(String code)
    {
        Session session=getSession();
        String  hql="from PropertyEntity t where t.code=:code";
        List list=session.createQuery(hql).setString("code",code).list();
        if (list.size()>0)
            return (PropertyEntity) list.get(0);
        else
            return null;
    }

    @Override
    public Integer countByType(byte type)
    {
        Session session=getSession();
        String hql="from PropertyEntity t where t.type=:type";
        return session.createQuery(hql).setByte("type",type).list().size();
    }

    @Override
    public ArrayList<PropertyEntity> getAllProperty()
    {
        Session session=getSession();
        String hql="from PropertyEntity ";
        List list=session.createQuery(hql).list();
        if (list.size()>0)
            return (ArrayList<PropertyEntity>) list;
        return null;
    }


}
