package com.sogokids.utils.util;

/**
 * 常量值定义类
 * Created by hoze on 15/8/25.
 */
public class Quantity {
    /**
     * STATUS 状态值
     */
    public static final int STATUS_ZERO = 0;
    public static final int STATUS_ONE = 1;
    public static final int STATUS_TWO = 2;
    public static final int STATUS_THREE = 3;
    public static final int STATUS_FOUR = 4;
    public static final int STATUS_FIVE = 5;
    public static final int STATUS_SIX = 6;
    public static final int STATUS_SEVEN = 7;
    public static final int STATUS_EIGHT = 8;
    public static final int STATUS_NINE = 9;
    public static final int STATUS_TEN = 10;
    public static final int STATUS_ELEVEN = 11;
    public static final int STATUS_TWELVE = 12;
    public static final int STATUS_THIRTEEN = 13;
    public static final int STATUS_FOURTEEN = 14;
    public static final int STATUS_FIFTEEN = 15;
    public static final int STATUS_SIXTEEN = 16;
    public static final int STATUS_SEVENTEEN = 17;
    public static final int STATUS_EIGHTEEN = 18;
    public static final int STATUS_NINETEEN = 19;
    public static final int STATUS_TWENTY = 20;
    public static final int STATUS_TWENTY_ONE = 21;
    public static final int STATUS_TWENTY_TWO = 22;
    public static final int STATUS_TWENTY_THREE = 23;
    public static final int STATUS_TWENTY_FOUR = 24;
    public static final int STATUS_TWENTY_FIVE = 25;
    public static final int STATUS_TWENTY_SIX = 26;
    public static final int STATUS_TWENTY_SEVEN = 27;
    public static final int STATUS_TWENTY_EIGHT = 28;
    public static final int STATUS_TWENTY_NINE = 29;
    public static final int STATUS_THIRTY = 30;

    public static final int RESULT_CODE_SUCESS = 0;
    public static final int RESULT_CODE_ERROR = -1;

    public static final String SQL_QUERY_SORT_DESC = "desc";
    public static final String SQL_QUERY_SORT_ASC = "asc";

    public static final String DISPLAY_IMAGE = "Image.Tongqu.DisplayPath";
    public static final String UPLOAD_IMAGE = "Image.Tongqu.UploadPath";

    public static final String UPLOAD_QZ = "ServicePort.PortUrl.QzPath";
    public static final String UPLOAD_QZ_JOIN = "ServicePort.PortUrl.QzJoinPath";
    public static final String UPLOAD_QZ_LEAVE = "ServicePort.PortUrl.QzLeavePath";
    public static final String UPLOAD_GROUP_COURSE = "ServicePort.PortUrl.GroupCoursePath";
    public static final String UPLOAD_APP_PUSH = "ServicePort.PortUrl.AppPushPath";
    public static final String UPLOAD_USERS_APP_PUSH = "ServicePort.PortUrl.UsersAppPushPath";
    public static final String UPLOAD_SMS = "ServicePort.PortUrl.SmsPath";
    public static final String UPLOAD_CANCEL_COURSE = "ServicePort.PortUrl.CancelCoursePath";

    public static final String SERVICE_PORT_TIME = "ServicePort.PortTime.Time";
    public static final String SERVICE_PORT_KEY = "ServicePort.PortKey";


    public static final String RETURN_USER = "user";
    public static final String RETURN_ENTITY = "model";
    public static final String RETURN_ENTITY_LIST = "entitys";

    public static final String RETURN_BUS_USER = "busUser";


    public static final String RETURN_SUCCESS = "success";
    public static final String RETURN_MSG = "msg";
    public static final String RETURN_MENUS = "menus";

    public static final String RETURN_PRODUCT_S = "products";
    public static final String RETURN_CITY_S = "citys";
    public static final String RETURN_REGION_S = "regions";
    public static final String WHERE = "where status > 0 ";

    public static final String RETURN_BUSINESS_S = "business";
    public static final String RETURN_BUSINESS_Y_S = "yProducts";
    public static final String RETURN_BUSINESS_W_S = "wProducts";

    //推荐课购买须知默认值
    public static final String GMXZ_STR =
            "- 购买后，请在“我的-预约课程”中预约您喜欢的课程，预约之后才能上课哦；\n" +
                    "- 自购买日起必须在1个月内选课，超出1个月课程作废；\n" +
                    "- 每组家庭建议1大1小参与，每增加1个孩子需再购买1个课程；\n" +
                    "- 很多课程非常火爆，建议您至少提前3天预约哦；\n" +
                    "- 预约课程后，若需改期，请于开课前3天在“我的-已选课程”取消，然后重新选课；低于这个时间，就不能改期啦；\n" +
                    "- 开课前1天不支持预约课程（如：周六开课，周五就不能预约课程了）；\n" +
                    "- 如需申请退款，则需在开课前2天，跟客服提前申请，否则不予退款。";

    public static final String GZMS_STR = "限1大1小参加";

    //试听课购买须知默认值
    public static final String ST_GMXZ_STR =
            "- 每个用户只有一次试听机会，限1大1小参与；\n" +
                    "- 购买1元试听后，只能约10天之内的课程，自购买日起超出10天没约课，试听机会作废；\n" +
                    "- 购买后，请在“我的-预约课程”中预约您的课程，预约之后才能上课哦；\n" +
                    "- 试听课不支持退款，不支持改期；\n" +
                    "- 开课前1天不支持预约课程（如：周六开课，周五就不能预约课程了）。";

    //老师审核－消息发送
    public static final String MS_TEMPLATE = "xxx，您好！您已通过简历筛选，请于####，到*****进行面试。";
    public static final String BTG_TEMPLATE = "xxx，您好！感谢您信任松果亲子，您的####与我们的要求暂不匹配，欢迎您过段时间再次申请。";
    //取消课程－取消原因
    public static final String CANCEL_COURSE_SMS = "非常抱歉地通知您，xxx，####课程要被取消了。不过别担心，您课程包的有效期将延期一个月，再去看看其他感兴趣的课程吧~";
    public static final String CANCEL_COURSE_APP = "嘤嘤嘤，对不起，xxx，本次课程要被取消了。不过别担心，大家课程包的有效期将延期一个月，再去看看其他感兴趣的课程吧~";
    public static final String CANCEL_COURSE_URL = "duoladebug://book";

}
