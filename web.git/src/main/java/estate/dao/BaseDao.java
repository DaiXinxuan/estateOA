package estate.dao;

import estate.common.enums.Entity;

import java.io.Serializable;

/**
 * Created by kangbiao on 15-9-16.
 * 定义每个DAO具有的基础的增删改查操作
 */
public interface BaseDao
{

    /**
     * 包含基础的增加和更新操作,适用于单个对象
     * @param object
     */
    void save(Object object);

    /**
     * 根据ID获取某个对象
     * @param id
     * @return 返回对应的对象
     */
    Object get(Serializable id,Object object);

    /**
     * 根据id获取对象
     * @param id id
     * @param object 实体类类名
     * @return 返回对应的对象
     */
    Object get(Serializable id,Class object);

    /**
     * 获取所有的对象
     * @param cls
     * @return 对象列表
     */
    Object getAll(Class cls);

    /**
     * 根据对象信息删除某个对象
     * @param object
     */
    void delete(Object object);


    /**
     * 通过代码和对象名获取对象
     * @param code
     * @param entity
     * @return 对象
     */
    Object getByCode(String code,Entity entity);

    /**
     * 根据表名计算总数
     * @param table 对象类名
     * @return 返回对象总数
     */
    Integer count(String table);

    /**
     * 对象技术
     * @param cls 对象类名
     * @return 返回对象总数
     */
    Integer count(Class cls);

}
