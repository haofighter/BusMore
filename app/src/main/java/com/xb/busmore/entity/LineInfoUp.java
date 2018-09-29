package com.xb.busmore.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class LineInfoUp {


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

    @Id(autoincrement = true)
    private Long id;
    private String line;
    private String version;
    private String up_station;
    private String down_station;
    private String chinese_name;
    private String is_fixed_price;
    private String is_keyboard;
    private String fixed_price;
    private String coefficient;
    private String shortcut_price;

    @Generated(hash = 1332158737)
    public LineInfoUp(Long id, String line, String version, String up_station, String down_station, String chinese_name, String is_fixed_price, String is_keyboard, String fixed_price, String coefficient, String shortcut_price) {
        this.id = id;
        this.line = line;
        this.version = version;
        this.up_station = up_station;
        this.down_station = down_station;
        this.chinese_name = chinese_name;
        this.is_fixed_price = is_fixed_price;
        this.is_keyboard = is_keyboard;
        this.fixed_price = fixed_price;
        this.coefficient = coefficient;
        this.shortcut_price = shortcut_price;
    }

    @Generated(hash = 658077448)
    public LineInfoUp() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUp_station() {
        return up_station;
    }

    public void setUp_station(String up_station) {
        this.up_station = up_station;
    }

    public String getDown_station() {
        return down_station;
    }

    public void setDown_station(String down_station) {
        this.down_station = down_station;
    }

    public String getChinese_name() {
        return chinese_name;
    }

    public void setChinese_name(String chinese_name) {
        this.chinese_name = chinese_name;
    }

    public String getIs_fixed_price() {
        return is_fixed_price;
    }

    public void setIs_fixed_price(String is_fixed_price) {
        this.is_fixed_price = is_fixed_price;
    }

    public String getIs_keyboard() {
        return is_keyboard;
    }

    public void setIs_keyboard(String is_keyboard) {
        this.is_keyboard = is_keyboard;
    }

    public String getFixed_price() {
        return fixed_price;
    }

    public void setFixed_price(String fixed_price) {
        this.fixed_price = fixed_price;
    }

    public String getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(String coefficient) {
        this.coefficient = coefficient;
    }

    public String getShortcut_price() {
        return shortcut_price;
    }

    public void setShortcut_price(String shortcut_price) {
        this.shortcut_price = shortcut_price;
    }
}
