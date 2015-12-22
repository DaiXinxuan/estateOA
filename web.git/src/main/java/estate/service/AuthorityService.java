package estate.service;

import java.util.ArrayList;

/**
 * Created by kangbiao on 15-9-21.
 *
 */
public interface AuthorityService
{

    /**
     * 通过电话和控制对象类型获取可进入的控制对象id
     * @param phone
     * @param type village,building,brake
     * @return 异常时返回null,无控制对象返回空ArrayList
     */
    ArrayList<Integer> getAuthorityIDsByPhoneType(String phone,Byte type);
}
