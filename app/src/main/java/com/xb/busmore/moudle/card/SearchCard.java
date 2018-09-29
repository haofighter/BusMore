package com.xb.busmore.moudle.card;


import com.xb.busmore.util.HexUtil;

import static java.lang.System.arraycopy;

/**
 * 作者：Tangren on 2018-07-19
 * 包名：com.szxb.buspay.db.entity.bean.card
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class SearchCard {
    //状态：00为寻到卡
    public String status;
    //城市代码
    public String cityCode;
    //卡号
    public String cardNo;
    //卡消费类型
    public String cardType;
    //卡模块类型CPU/M1
    public String cardModuleType;

    public SearchCard(byte[] datas) {
        int index = 0;
        byte[] status_byte = new byte[1];
        arraycopy(datas, index, status_byte, 0, status_byte.length);
        status = HexUtil.printHexBinary(status_byte);

        byte[] city_code_byte = new byte[4];
        arraycopy(datas, index += status_byte.length, city_code_byte, 0, city_code_byte.length);
        cityCode = HexUtil.printHexBinary(city_code_byte);

        byte[] card_no_byte = new byte[4];
        arraycopy(datas, index += city_code_byte.length, card_no_byte, 0, card_no_byte.length);
        cardNo = HexUtil.printHexBinary(card_no_byte);

        byte[] card_type_byte = new byte[1];
        arraycopy(datas, index += card_no_byte.length, card_type_byte, 0, card_type_byte.length);
        cardType = HexUtil.printHexBinary(card_type_byte);

        byte[] card_module_type_byte = new byte[1];
        arraycopy(datas, index + card_type_byte.length, card_module_type_byte, 0, card_module_type_byte.length);
        cardModuleType = HexUtil.printHexBinary(card_module_type_byte);
    }

    @Override
    public String toString() {
        return "SearchCard[" +
                "状态='" + status + '\'' +
                ", 城市代码='" + cityCode + '\'' +
                ", 卡号='" + cardNo + '\'' +
                ", 卡类型='" + cardType + '\'' +
                ", 卡结构类型='" + cardModuleType + '\'' +
                ']';
    }
}
