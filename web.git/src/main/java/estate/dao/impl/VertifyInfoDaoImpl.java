package estate.dao.impl;

import estate.dao.VertifyInfoDao;
import estate.entity.database.VertifyInfoEnity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dxx on 2015/12/21.
 */
@Repository("vertifyInfoDao")
public class VertifyInfoDaoImpl implements VertifyInfoDao{
    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public VertifyInfoEnity getByPhone(String phone) {
        Session session=getSession();
        String hql="from VertifyInfoEnity t where t.phone=:phone";
        List list=session.createQuery(hql).setString("phone",phone).list();
        if (list.size()>0)
            return (VertifyInfoEnity) list.get(0);
        else
            return null;
    }
}
