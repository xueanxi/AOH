package com.game.xianxue.ashesofhistory.game.model.person;

import com.game.xianxue.ashesofhistory.game.skill.SkillBase;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 人物的原始模型 只有一些最基础的资料
 * Created by anxi.xue on 8/29/17.
 */

public class BasePerson implements Serializable {
    // 基础资料
    public int _id;                 // 保存在数据库中的_id
    public int psersonId;           // id唯一标识这个人物
    public float aptitude;          // 资质（影响每次升级 基础属性的增加数量 资质取值为1～5 1为庸碌无为 5为天神下凡）
    public String name;             // 名字
    public String name2;            // 名字拼音
    public int sexuality;           // 男1 女0

    /**
     * 七大基础属性原始值：
     * <p>
     * 初始属性 即 0级时的属性，这个属性衡量了一个人物的天赋
     * 初始属性 和 资质 共同影响 升级后提升属性的值
     */
    public int strength_Raw;         //初始力量
    public int intellect_Raw;        //初始智力
    public int dexterity_Raw;        //初始敏捷
    public int physique_Raw;         //初始体魄
    public int spirit_Raw;           //初始精神
    public int luck_Raw;             //初始运气
    public int fascination_Raw;      //初始魅力

    /**
     * 天赋技能. 一个技能的列表字符串，存储了人物在各个等级能够解锁的技能。
     */
    public String skillStrings = null;

    public int getPsersonId() {
        return psersonId;
    }

    public void setPsersonId(int psersonId) {
        this.psersonId = psersonId;
    }

    public float getAptitude() {
        return aptitude;
    }

    public void setAptitude(float aptitude) {
        this.aptitude = aptitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getSexuality() {
        return sexuality;
    }

    public void setSexuality(int sexuality) {
        this.sexuality = sexuality;
    }

    public int getStrength_Raw() {
        return strength_Raw;
    }

    public void setStrength_Raw(int strength_Raw) {
        this.strength_Raw = strength_Raw;
    }

    public int getIntellect_Raw() {
        return intellect_Raw;
    }

    public void setIntellect_Raw(int intellect_Raw) {
        this.intellect_Raw = intellect_Raw;
    }

    public int getDexterity_Raw() {
        return dexterity_Raw;
    }

    public void setDexterity_Raw(int dexterity_Raw) {
        this.dexterity_Raw = dexterity_Raw;
    }

    public int getPhysique_Raw() {
        return physique_Raw;
    }

    public void setPhysique_Raw(int physique_Raw) {
        this.physique_Raw = physique_Raw;
    }

    public int getSpirit_Raw() {
        return spirit_Raw;
    }

    public void setSpirit_Raw(int spirit_Raw) {
        this.spirit_Raw = spirit_Raw;
    }

    public int getLuck_Raw() {
        return luck_Raw;
    }

    public void setLuck_Raw(int luck_Raw) {
        this.luck_Raw = luck_Raw;
    }

    public int getFascination_Raw() {
        return fascination_Raw;
    }

    public void setFascination_Raw(int fascination_Raw) {
        this.fascination_Raw = fascination_Raw;
    }

    public String getSkillStrings() {
        return skillStrings;
    }

    public void setSkillStrings(String skillStrings) {
        this.skillStrings = skillStrings;
    }

    /**
     * 在xml文件中，人物的技能是一个字符串，需要解析出来
     * 字符串是这样的“4,3,7,2:500,3,5,2”
     * 表示的意义是 ,使用 “：”分隔,所以这个字符串有两个技能分别为4,3,7,2 和 500,3,5,2
     * “4,3,7,2”
     * 4表示技能的id为4
     * 3表示技能当前的等级为3级
     * 7表示这个技能在人物7级的时候领悟
     * 2表示技能解锁之后，人物每升2级，技能升级一次
     */
    public void parseSkillList(){
        ArrayList<SkillBase> skillLists = null;
        if(this.skillStrings != null && skillStrings.length()!=0){
            String[] skillDatas = skillStrings.split(":");
            int skillSize = skillDatas.length;
            if(skillSize <= 0) return;
            for(int i =0;i<skillSize;i++){
                skillDatas[i];
            }
        }
    }

    @Override
    public String toString() {
        return "BasePerson{" +
                "_id=" + _id +
                ", psersonId=" + psersonId +
                ", aptitude=" + aptitude +
                ", name='" + name + '\'' +
                ", name2='" + name2 + '\'' +
                ", sexuality=" + sexuality +
                ", strength_Raw=" + strength_Raw +
                ", intellect_Raw=" + intellect_Raw +
                ", dexterity_Raw=" + dexterity_Raw +
                ", physique_Raw=" + physique_Raw +
                ", spirit_Raw=" + spirit_Raw +
                ", luck_Raw=" + luck_Raw +
                ", fascination_Raw=" + fascination_Raw +
                ", skillStrings='" + skillStrings + '\'' +
                '}';
    }
}
