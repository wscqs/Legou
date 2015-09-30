package com.cqs.util;

/**
 * Created by chenqiusong on 15/8/29.
 */
public interface Constant {
    String FILE_NAME="dianping";
    String MODE_NAME="welcome";

    //图灵
    String TLBASEURL="http://www.tuling123.com/openapi/api";
    String TLKEY="c5fae7384ae399888ae01320511d929b";

    //host
//    String HOST="http://192.168.31.141:8080";
    String HOST="http://10.0.3.2:8080";
    String CITY_LIST=HOST+"/servlet/CityServlet";
    String GATEGORY_LIST=HOST+"/servlet/CategoryServlet";
    String GOODS_LIST=HOST+"/servlet/GoodsServlet";//记得page与size
    String GOODS_NEATBY=HOST+"/servlet/NearbyServlet";
    String USER_REGISTER=HOST+"/servlet/UserServlet?flag=register";
    String USER_SOCIAL=HOST+"/servlet/UserServlet?flag=social";
    String USER_LOGIN=HOST+"/servlet/UserServlet?flag=login";
    String USER_UPDATE =HOST+ "/servlet/UserServlet?flag=update";


    //qq
    String QQ_APP_ID ="1104808707";

}
