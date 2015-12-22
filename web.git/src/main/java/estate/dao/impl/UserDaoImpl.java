package estate.dao.impl;

import estate.common.config.AppUserStatus;
import estate.common.config.UserType;
import estate.dao.UserDao;
import estate.entity.database.AppUserEntity;
import estate.entity.database.OwnerEntity;
import estate.entity.database.UserInfoEntity;
import estate.entity.json.TableData;
import estate.entity.json.TableFilter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangbiao on 15-9-16.
 *
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }



    public TableData getOwnerList(TableFilter tableFilter)
    {
        Session session = getSession();
        TableData tableData = new TableData(true);
        Query query;
        if (!tableFilter.getSearchValue().equals(""))
        {
            String hql = "from OwnerEntity o where o.name like (?) or o.phone like (?)";
            query = session.createQuery(hql).setString(0, "%" + tableFilter.getSearchValue() + "%")
                    .setString(1, "%" + tableFilter.getSearchValue() + "%");
        }
        else
        {
            String hql = "from OwnerEntity o";
            query = session.createQuery(hql);
        }
        Integer count=query.list().size();
        List list=query.setFirstResult(tableFilter.getStart()).setMaxResults(tableFilter.getLength()).list();

        tableData.setRecordsFiltered(count);
        tableData.setJsonString(list);
        return tableData;
    }


    @Override
    public TableData getAppUserList(TableFilter tableFilter)
    {
        Session session = getSession();
        TableData tableData = new TableData(true);
        Query query;
        if (tableFilter.getSearchValue()!=null)
        {
            String hql = "from AppUserEntity t where t.phone like (?)";
            query = session.createQuery(hql).setString(0, "%" + tableFilter.getSearchValue() + "%");
        }
        else
        {
            String hql = "from AppUserEntity t";
            query = session.createQuery(hql);
        }
        Integer count=query.list().size();
        List list=query.setFirstResult(tableFilter.getStart()).setMaxResults(tableFilter.getLength()).list();

        tableData.setRecordsFiltered(count);
        tableData.setJsonString(list);
        return tableData;
    }

    @Override
    public Object getUserInfoByPhoneRole(String phone, int type)
    {
        Session session=getSession();
        String hql;
        if (type==UserType.APPUSER)
            hql="from AppUserEntity t where t.phone=:phone";
        else if (type==UserType.OWNER)
            hql="from OwnerEntity t where t.phone=:phone";
        else
            return null;
        List list=session.createQuery(hql).setString("phone",phone).list();
        if (list.size()>0)
            return list.get(0);
        else
            return null;
    }

    @Override
    public void deleteUserByPhone(String phone, byte type)
    {
        if (phone==null)
            return;
        Session session=getSession();
        String hql;
        if (type==UserType.OWNER)
        {
            hql = "delete from OwnerEntity t where t.phone=:phone";
            session.createQuery(hql).setString("phone",phone).executeUpdate();
        }
        else if (type==UserType.APPUSER)
        {
            hql = "update AppUserEntity t set t.status=:status where t.phone=:phone";
            session.createQuery(hql).setByte("status", AppUserStatus.DELETE).setString("phone",phone).executeUpdate();
        }
    }

    @Override
    public ArrayList<AppUserEntity> getAllAppUser()
    {
        Session session =getSession();
        String hql="from AppUserEntity ";
        List list=session.createQuery(hql).list();
        if (list.size()>0)
            return (ArrayList<AppUserEntity>) list;
        return null;
    }

    @Override
    public UserInfoEntity getUserDetailByPhone(String phone)
    {
        Session session=getSession();
        String hql="from UserInfoEntity t where t.phone=:phone";
        List list=session.createQuery(hql).setString("phone",phone).list();
        if (list.size()>0)
            return (UserInfoEntity) list.get(0);
        return null;
    }

    @Override
    public ArrayList<OwnerEntity> getAllOwner()
    {
        Session session =getSession();
        String hql="from OwnerEntity ";
        List list=session.createQuery(hql).list();
        if (list.size()>0)
            return (ArrayList<OwnerEntity>) list;
        return null;
    }
}
