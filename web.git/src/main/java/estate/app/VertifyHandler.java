package estate.app;

import estate.common.config.VertifyStatus;
import estate.common.util.LogUtil;
import estate.entity.database.VertifyInfoEnity;
import estate.entity.json.BasicJson;
import estate.exception.PictureUploadException;
import estate.service.BaseService;
import estate.service.PictureService;
import estate.service.VertifyInfoService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by dxx on 2015/12/16.
 * 该类用来处理实名认证请求
 */
@RestController
@RequestMapping(value = "/api/vertify")
public class VertifyHandler {

    private Logger logger= LogUtil.getLogger(this.getClass());

    @Autowired
    private BaseService baseService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private VertifyInfoService vertifyInfoService;

    /**
     * 新增一条身份认证
     * @param vertifyInfoEnity
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public BasicJson vertifyCode(VertifyInfoEnity vertifyInfoEnity,HttpServletRequest httpServletRequest){
        BasicJson basicJson = new BasicJson(false);
        HttpSession httpSession = httpServletRequest.getSession();
        vertifyInfoEnity.setPhone((String)httpSession.getAttribute("phone"));
        vertifyInfoEnity.setStatus(VertifyStatus.UNCHECKED);

        //判断有无图片上传
        if(vertifyInfoEnity.getType()!=1) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) httpServletRequest;
            Map<String, MultipartFile> map = multipartRequest.getFileMap();

            try {
                vertifyInfoEnity.setImageIdList(pictureService.saveAndReturnID(map));
            } catch (PictureUploadException e) {
                basicJson.getErrorMsg().setDescription(e.getMessage());
                return basicJson;
            } catch (Exception e) {
                logger.error("图片上传失败");
                basicJson.getErrorMsg().setDescription("图片上传失败,请重试");
                return basicJson;
            }
        }

        try{
            baseService.save(vertifyInfoEnity);
        }catch (Exception e){
            logger.error("认证信息保存失败");
            basicJson.getErrorMsg().setDescription("认证信息保存失败");
            return basicJson;
        }
        basicJson.setStatus(true);
        return basicJson;
    }

    /**
     * 获取身份认证信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/get")
    public BasicJson getVertifyInfo(HttpServletRequest request){
        BasicJson basicJson = new BasicJson(false);
        HttpSession httpSession = request.getSession();
        String phone = (String)httpSession.getAttribute("phone");
        VertifyInfoEnity vertify;
        try {
            vertify = (VertifyInfoEnity)vertifyInfoService.getByPhone(phone);
        }catch (Exception e){
            logger.error("未查找到任何有效数据");
            basicJson.getErrorMsg().setDescription("未查找到任何有效数据");
            return basicJson;
        }
        //将图片的ID号变为路径URL
        if(vertify.getType()!=1) {
            vertify.setImageIdList(pictureService.getPathsByIDs(vertify.getImageIdList(), request));
        }
        basicJson.setStatus(true);
        basicJson.setJsonString(vertify);
        return basicJson;
    }
}
