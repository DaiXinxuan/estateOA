package estate.service;

/**
 * Created by dxx on 2015/12/21.
 */
public interface VertifyInfoService {
    /**
     * 通过app用户的手机号码获取认证信息
     * @param phone
     * @return
     */
    Object getByPhone(String phone);
}
