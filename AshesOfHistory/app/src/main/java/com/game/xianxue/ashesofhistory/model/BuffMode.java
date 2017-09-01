package com.game.xianxue.ashesofhistory.model;

import com.game.xianxue.ashesofhistory.interfaces.Interface_Buff;

/**
 * Buff 模型
 * Created by user on 9/1/17.
 */

public class BuffMode implements Interface_Buff{
    int buff_id;            // Buff ID
    String name;            // Buff名字
    String introduce;       // Buff 描述
    int buff_type;          // Buff 类型 详细请查看 Interface_Buff 的 BUFF_TYPE_ 开头的常量
    int time;               // Buff 持续时间
    int time_surplus;       // Buff 剩余回合
    int buff_level;         // Buff 等级

    public BuffMode(int buff_id,String name,String introduce,int buff_type,int time,int buff_level){
        this.buff_id = buff_id;
        this.name = name;
        this.introduce = introduce;
        this.buff_type = buff_type;
        this.time = time;
        this.buff_level = buff_level;
        time_surplus = time;
    }

    public int getBuff_id() {
        return buff_id;
    }

    public void setBuff_id(int buff_id) {
        this.buff_id = buff_id;
    }

    public int getBuff_type() {
        return buff_type;
    }

    public void setBuff_type(int buff_type) {
        this.buff_type = buff_type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime_surplus() {
        return time_surplus;
    }

    public void setTime_surplus(int time_surplus) {
        this.time_surplus = time_surplus;
    }

    public int getBuff_level() {
        return buff_level;
    }

    public void setBuff_level(int buff_level) {
        this.buff_level = buff_level;
    }
}
