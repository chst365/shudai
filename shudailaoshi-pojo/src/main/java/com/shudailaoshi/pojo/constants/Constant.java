package com.shudailaoshi.pojo.constants;

public interface Constant {

    /**
     * 当前登陆用户
     */
    String CURRENT_USER = "current_user";
    /**
     * 管理员角色名称
     */
    String ADMIN = "admin";
    /**
     * 会员角色Id
     */
    long CUSTOMER_ID = 3L;
    /**
     * 会员角色名
     */
    String CUSTOMER = "customer";
    /**
     * 服务员名
     */
    String WAITER = "waiter";
    /**
     * 私有密钥
     */
    String PRIVATE_KEY = "private_key";
    /**
     * 共有密钥
     */
    String PUBLIC_KEY = "public_key";

    /**
     * POST
     */
    String POST = "POST";

    /**
     * STATE
     */
    String STATE = "status";

    /**
     * 资源树root
     */
    long ROOT = 1L;

    /**
     * 提交Token
     */
    String SUBMIT_TOKEN = "SUBMIT_TOKEN";

    /**
     * 访问Token
     */
    String ACCESS_TOKEN = "ACCESS_TOKEN";

    /**
     * 刷新Token
     */
    String REFRESH_TOKEN = "REFRESH_TOKEN";

    /**
     * 提交Token错误状态码
     */
    int TOKEN_ERROR_STATUS = 999;

    /**
     * 认证授权错误
     */
    int AUTH_ERROR_STATUS = 403;

    /**
     * session过期
     */
    int SESSION_ERROR_STATUS = 401;
    /**
     * 验证码记录前缀
     */
    String VALIDCODE = "valid_code:";
    /**
     * 密码错误次数记录前缀
     */
    String PWD_ERROR_NUM = "pwd_error_num:";
    /**
     * 好评等级
     */
    int COMMENT_GOOD_LEVEL = 5;
    /**
     * 常用联系人上限值
     */
    int CAR_LINK_MAN_LIMIT = 5;
    /**
     * 截止预约时间毫秒数
     */
    long BESPEAK_ADVANCE_MILLISECOND = 1000 * 60 * 20;
    /**
     * 请求url
     */
    String REQUESTURL = "RequestUrl";
    /**
     * 返回url
     */
    String REFERER = "Referer";
    /**
     * 静态页面
     */
    String HTML = ".html";
    /**
     * SUCCESS
     */
    String SUCCESS = "SUCCESS";
    /**
     * FAIL
     */
    String FAIL = "FAIL";

    /**
     * 忘记密码手机号码sessionkey
     */
    String CHANGE_PWD_MOBILE = "CHANGE_PWD_MOBILE";

    /**
     * 微信原生扫码支付类型
     */
    String WECHAT_ORDER_NATIVE = "NATIVE";
    /**
     * 微信手机APP支付类型
     */
    String WECHAT_ORDER_APP = "APP";
    /**
     * && 分隔符
     */
    String SPLIT_DOUBLE_F7 = "&&";
    /**
     * 通用洗车券ID
     */
    long CURRENCY_COUPON_ID = 1L;
    /**
     * 资产类科目类别
     */
    long ZICHAN_ID = 1L;

    /**
     * 货币资金科目代码
     */
    String HOBIZIJIN = "1001";

    String SALE_FEE = "6601";
    String GUANLI_FEE = "6602";
    String CAIWU_FEE = "6603";
    /**
     * 本年利润
     */
    String BNLS = "4103";
    /**
     * 营业外支出
     */
    String YYWZC = "6711";
    /**
     * 营业外收入
     */
    String YYWSR = "6301";
}
