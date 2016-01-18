package com.sogokids.utils.util;

/**
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
    public static final String UPLOAD_SMS = "ServicePort.PortUrl.SmsPath";

    public static final String SERVICE_PORT_TIME = "ServicePort.PortTime.Time";
    public static final String SERVICE_PORT_KEY = "ServicePort.PortKey";


    public static final String RETURN_USER = "user";
    public static final String RETURN_ENTITY = "model";
    public static final String RETURN_ENTITY_LIST = "entitys";


    public static final String RETURN_SUCCESS = "success";
    public static final String RETURN_MSG = "msg";

    public static final String RETURN_PRODUCT_S = "products";
    public static final String RETURN_CITY_S = "citys";
    public static final String RETURN_REGION_S = "regions";
    public static final String WHERE = "where status > 0 ";

    public static final String RETURN_BUSINESS_S = "business";
    public static final String RETURN_BUSINESS_Y_S = "yProducts";
    public static final String RETURN_BUSINESS_W_S = "wProducts";

    public static final String GMXZ_STR =
            "- 购买后，请在“我的-待选课程”中预约您的课程，才算选课成功；\n" +
            "- 每组家庭限1大1小参与，增加1个孩子需再购买1个课程；\n" +
            "- 预约课程，至少提前1天（例如：周六的课程，周五不能再选课）；\n" +
            "- 退订或修改课程，至少提前3天（例如：周六的课程，周四不可退改）。";

    public static final String GZMS_STR = "限1大1小参加";

    public static final String ST_GMXZ_STR =
            "- 购买后，请在“我的-待选课程”中预约您的课程，才算选课成功；\n" +
                    "- 预约成功的试听课不可退换，不可改期；\n" +
                    "- 预约课程，至少提前1天（例如：周六的课程，周五不能再选课）。";

    public static final String MS_TEMPLATE = "xxx，您好！您已通过简历筛选，请于####，到*****进行面试。";
    public static final String BTG_TEMPLATE = "xxx，您好！感谢您信任松果亲子，您的####与我们的要求暂不匹配，欢迎您过段时间再次申请。";


}
