package estate.common.config;

/**
 * Created by kangbiao on 15-11-11.
 * 管理员可操作的菜单栏定义
 */
public class AdminMenuDefine
{
        public static String INDEX="<li>" +
                "<a href='ajax/dashboard.html' class='active ajax-link'>" +
                "<i class='fa fa-dashboard'></i>" +
                "<span class='hidden-xs'>系统首页</span>" +
                "</a>" +
                "</li>";
        public static String PROPERTY ="<li class='dropdown'>" +
                "<a href='#' class='dropdown-toggle'>" +
                "<i class='fa fa-building-o'></i>" +
                "<span class='hidden-xs'>物业管理</span>" +
                "</a>" +
                "<ul class='dropdown-menu'>" +
                "<li><a class='ajax-link' href='ajax/propertyList.html'>物业信息</a></li>" +
                "<li><a class='ajax-link' href='ajax/villageList.html'>园区信息</a></li>" +
                "<li><a class='ajax-link' href='ajax/buildingList.html'>楼栋信息</a></li>" +
                "<li><a class='ajax-link' href='ajax/addProperty.html'>增加物业</a></li>" +
                "</ul>" +
                "</li>";
        public static String PARKLOT="<li class='dropdown'>" +
                "<a href='#' class='dropdown-toggle'>" +
                "<i class='fa fa-truck'></i>" +
                "<span class='hidden-xs'>车位管理</span>" +
                "</a>" +
                "<ul class='dropdown-menu'>" +
                "<li><a class='ajax-link' href='ajax/parkingLotList.html'>车位信息</a></li>" +
                "<li><a class='ajax-link' href='ajax/brakeList.html'>道闸信息</a></li>" +
                "</ul>" +
                "</li>";
        public static String USER="<li class='dropdown'>" +
                "<a href='#' class='dropdown-toggle'>" +
                "<i class='fa fa-users'></i>" +
                "<span class='hidden-xs'>用户管理</span>" +
                "</a>" +
                "<ul class='dropdown-menu'>" +
                "<li><a class='ajax-link' href='ajax/ownerList.html'>住户清单</a></li>" +
                "<li><a class='ajax-link' href='ajax/appUserList.html'>APP账号管理</a></li>" +
                "</ul>" +
                "</li>";
        public static String FEE="<li class='dropdown'>" +
                "<a href='#' class='dropdown-toggle'>" +
                "<i class='fa fa-cny'></i>" +
                " <span class='hidden-xs'>缴费管理</span>" +
                "</a>" +
                "<ul class='dropdown-menu'>" +
                "<li><a class='ajax-link' href='ajax/billInfo.html'>缴费清单</a></li>" +
                "<li><a class='ajax-link' href='ajax/estateFeeList.html'>物业管理费</a></li>" +
                "<li><a class='ajax-link' href='ajax/serviceFeeList.html'>物业服务费</a></li>" +
                "<li><a class='ajax-link' href='ajax/parkingLotFeeList.html'>车位管理费</a></li>" +
                "</ul>" +
                "</li>";
        public static String NOTICE="<li class='dropdown'>" +
                "<a href='#' class='dropdown-toggle'>" +
                "<i class='fa fa-bullhorn'></i>" +
                "<span class='hidden-xs'>公告管理</span>" +
                "</a>" +
                "<ul class='dropdown-menu'>" +
                "<li><a class='ajax-link' href='ajax/noticeAdd.html'>新增公告</a></li>" +
                "<li><a class='ajax-link' href='ajax/noticeList.html'>历史公告</a></li>" +
                "</ul>" +
                "</li>";
        public static String COMPLAINREPAIR="<li class='dropdown'>" +
                "<a href='#' class='dropdown-toggle'>" +
                "<i class='fa fa-wrench'></i>" +
                "<span class='hidden-xs'>投诉报修</span>" +
                "</a>" +
                "<ul class='dropdown-menu'>" +
                "<li><a class='ajax-link' href='ajax/complain.html'>投诉</a></li>" +
                "<li><a class='ajax-link' href='ajax/repair.html'>报修</a></li>" +
                "</ul>" +
                "</li>";
        public static String SECRET="<li class='dropdown'>" +
                "<a href='#' class='dropdown-toggle'>" +
                "<i class='fa fa-lock'></i>" +
                "<span class='hidden-xs'>YunPass管理</span>" +
                "</a>" +
                "<ul class='dropdown-menu'>" +
                "<li><a class='ajax-link' href='ajax/secretConfig.html'>密钥导入</a></li>" +
                "<li><a class='ajax-link' href='ajax/secretList.html'>密钥查看</a></li>" +
                "<li><a class='ajax-link' href='ajax/apkList.html'>APK管理</a></li>" +
                "</ul>" +
                "</li>";
        public static String ADMIN="<li class='dropdown'>" +
                "<a href='#' class='dropdown-toggle'>" +
                "<i class='fa fa-user'></i>" +
                "<span class='hidden-xs'>后台用户管理</span>" +
                "</a>" +
                "<ul class='dropdown-menu'>" +
                "<li><a class='ajax-link' href='ajax/userGroup.html'>用户组</a></li>" +
                "<li><a class='ajax-link' href='ajax/adminUser.html'>用户</a></li>" +
                "</ul>" +
                "</li>";
}
