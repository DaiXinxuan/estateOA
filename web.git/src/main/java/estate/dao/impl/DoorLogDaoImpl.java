package estate.dao.impl;

import estate.dao.DoorLogDao;
import estate.entity.database.OpenDoorRecordEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangbiao on 15-11-3.
 *
 */
@Repository("doorLogDao")
public class DoorLogDaoImpl implements DoorLogDao
{

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }


    @Override
    public ArrayList<OpenDoorRecordEntity> getByPhoneTimeNum(String phone, Long startTime, Long endTime, Integer num)
    {
        Session session=getSession();
        StringBuilder hql=new StringBuilder("from OpenDoorRecordEntity t where t.phone='"+phone+"'");
        if (startTime!=null)
            hql.append(" and t.openTime>=").append(startTime);
        if (endTime!=null)
            hql.append(" and t.openTime<=").append(endTime);
        hql.append(" order by t.openTime desc");
        List list=session.createQuery(hql.toString()).setMaxResults(num).list();
        if (list.size()>0)
            return (ArrayList<OpenDoorRecordEntity>) list;
        return null;
    }
}
