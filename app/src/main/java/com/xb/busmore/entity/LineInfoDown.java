package com.xb.busmore.entity;

import org.greenrobot.greendao.annotation.Entity;

import java.util.List;

public class LineInfoDown {

    /**
     * line : 0516
     * version : 20180730151620
     * up_station : 4
     * down_station : 4
     * chinese_name : 周村分公司 22路
     * is_fixed_price : 2
     * is_keyboard : 0
     * fixed_price : 500
     * coefficient : 090090090000090090090100100100
     * shortcut_price : 010002000300040005000600070008000900
     * up_position : [{"no":"1","name":"周村","latitude":"117.86698240728","longitude":"36.797499004843","distance":"150"},{"no":"2","name":"高塘路口","latitude":"117.9154184494","longitude":"36.768622954425","distance":"150"},{"no":"3","name":"水产城","latitude":"117.94719774555","longitude":"36.658248744891","distance":"150"},{"no":"4","name":"淄川","latitude":"117.95856687737","longitude":"36.648386210927","distance":"150"}]
     * down_position : [{"no":"1","name":"淄川","latitude":"117.95856687737","longitude":"36.648386210927","distance":"150"},{"no":"2","name":"康桥","latitude":"117.947754131","longitude":"36.658257152581","distance":"150"},{"no":"3","name":"石佛","latitude":"117.92427096886","longitude":"36.746220699119","distance":"150"},{"no":"4","name":"周村","latitude":"117.86698158957","longitude":"36.797498746902","distance":"150"}]
     * up_price : [{"no":"1","price":"500"},{"no":"2","price":"300"},{"no":"3","price":"200"},{"no":"4","price":"500"}]
     * down_price : [{"no":"1","price":"500"},{"no":"2","price":"300"},{"no":"3","price":"200"},{"no":"4","price":"500"}]
     */

    private List<LineStation> up_position;
    private List<LineStation> down_position;
    private List<LinePriceInfo> up_price;
    private List<LinePriceInfo> down_price;

    public List<LinePriceInfo> getUp_price() {
        return up_price;
    }

    public void setUp_price(List<LinePriceInfo> up_price) {
        this.up_price = up_price;
    }

    public List<LinePriceInfo> getDown_price() {
        return down_price;
    }

    public void setDown_price(List<LinePriceInfo> down_price) {
        this.down_price = down_price;
    }

    public List<LineStation> getUp_position() {
        return up_position;
    }

    public void setUp_position(List<LineStation> up_position) {
        this.up_position = up_position;
    }

    public List<LineStation> getDown_position() {
        return down_position;
    }

    public void setDown_position(List<LineStation> down_position) {
        this.down_position = down_position;
    }
}
