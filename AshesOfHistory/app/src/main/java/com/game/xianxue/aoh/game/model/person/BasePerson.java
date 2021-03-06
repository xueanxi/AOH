package com.game.xianxue.aoh.game.model.person;

import com.game.xianxue.aoh.Log.LogUtils;
import com.game.xianxue.aoh.database.SkillDataManager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 人物的原始模型 只有一些最基础的资料
 * Created by anxi.xue on 8/29/17.
 */
// TODO: 10/19/17 应该新增加几个数据库表存放 士兵类 怪物类 这些数量无限的，跟武将区分开来
public class BasePerson implements Serializable {
    private static final String TAG = "BasePerson";

    // 基础资料
    protected int _id;                 // 保存在数据库中的_id
    protected int personId;           // id唯一标识这个人物
    protected String name;             // 名字
    protected String namePinyin;       // 名字拼音
    protected int sexuality;           // 男1 女0

    /**
     * 七大基础属性原始值：
     * <p>
     * 初始属性 即 0级时的属性，这个属性衡量了一个人物的天赋
     * 初始属性 和 资质 共同影响 升级后提升属性的值
     */
    protected int strength_Raw;         //初始力量
    protected int intellect_Raw;        //初始智力
    protected int dexterity_Raw;        //初始敏捷
    protected int physique_Raw;         //初始体魄
    protected int spirit_Raw;           //初始精神
    protected int luck_Raw;             //初始运气
    protected int fascination_Raw;      //初始魅力


    protected int leadSkillId;          // 领导技能ID
    /**
     * 天赋技能的字符串. 一个技能的列表字符串，存储了人物在各个等级能够解锁的技能。
     */
    protected String skillStrings = null;
    protected ArrayList<SkillBean> skillAllLists;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
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

    public int getLeadSkillId() {
        return leadSkillId;
    }

    public void setLeadSkillId(int leadSkillId) {
        this.leadSkillId = leadSkillId;
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
    public ArrayList<SkillBean> parseSkillList(){
        LogUtils.d(TAG,"parseSkillList() Start");
        skillAllLists = new ArrayList<SkillBean>();
        SkillBean skillBean = null;
        if(this.skillStrings != null && skillStrings.length()!=0){
            String[] skillDatas;
            String[] skilldata;

            skillDatas = skillStrings.split(":");
            int skillSize = skillDatas.length;
            if(skillSize <= 0) return null;

            for(int i =0;i<skillSize;i++){
                skilldata = skillDatas[i].split(",");
                int size = skilldata.length;
                if(size != 4) return null;
                skillBean = new SkillBean();
                int skillID = Integer.valueOf(skilldata[0]);
                int level = Integer.valueOf(skilldata[1]);
                int unLockLevel = Integer.valueOf(skilldata[2]);
                int grow = Integer.valueOf(skilldata[3]);

                skillBean.setSkillID(skillID);
                skillBean.setStartLevel(level);
                skillBean.setUnLockLevel(unLockLevel);
                skillBean.setGrow(grow);
                skillBean.setSkill(SkillDataManager.getSkillFromDataBaseById(skillID));
                skillAllLists.add(skillBean);
            }
            //ShowUtils.showArrayLists(TAG+"after parse skill",skillAllLists);
            return skillAllLists;
        }else{
            LogUtils.e(TAG,"parseSkillList() Fail !!! skillStrings == null");
        }
        return null;
    }

    @Override
    public String toString() {
        return "BasePerson{" +
                "_id=" + _id +
                ", personId=" + personId +
                ", name='" + name + '\'' +
                ", namePinyin='" + namePinyin + '\'' +
                ", sexuality=" + sexuality +
                ", strength_Raw=" + strength_Raw +
                ", intellect_Raw=" + intellect_Raw +
                ", dexterity_Raw=" + dexterity_Raw +
                ", physique_Raw=" + physique_Raw +
                ", spirit_Raw=" + spirit_Raw +
                ", luck_Raw=" + luck_Raw +
                ", fascination_Raw=" + fascination_Raw +
                ", leadSkillId=" + leadSkillId +
                ", skillStrings='" + skillStrings + '\'' +
                '}';
    }
}
