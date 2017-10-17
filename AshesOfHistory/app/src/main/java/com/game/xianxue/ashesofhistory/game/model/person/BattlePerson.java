package com.game.xianxue.ashesofhistory.game.model.person;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

/**
 * 人物战斗时的模型
 * 比普通状态下的模型多了一些战斗中才会有的属性
 */
public class BattlePerson extends NormalPerson {
    private static final String TAG = "BattlePerson";

    // 战斗属性，只有在战斗时才会有的属性
    private int battleId;                   // 一场战斗中分配的id
    private int lineupId;                   // 在阵型中的位置id
    private int activeValues;               // 当前的行动值，当行动值达到最大行动值之后，就可以行动。
    private int camp;                       // 阵营
    private boolean isLeader = false;       // 是否统帅
    private boolean isCounsellor = false;   // 是否军师
    private int distance;                   // 与攻击者的距离
    public int HP_Current;                  // 当前生命值

    public BattlePerson() {}

    /**
     * 构造函数 2
     * @param person
     * @param camp
     */
    public BattlePerson(NormalPerson person,int camp) {
        // 原始资料
        this.psersonId = person.getPsersonId();                 // id唯一标识这个人物
        this.aptitude = person.getAptitude();                   // 资质（影响每次升级 基础属性的增加数量 资质取值为1～5 1为庸碌无为 5为天神下凡）
        this.name = person.getName();                           // 名字
        this.name2 = person.getName2();                         // 名字拼音
        this.sexuality = person.getSexuality();                 // 男1 女0

        // 获得原始属性
        this.strength_Raw = person.getStrength_Raw();           // 原始力量
        this.intellect_Raw = person.getIntellect_Raw();         // 原始智力
        this.dexterity_Raw = person.getDexterity_Raw();         // 原始敏捷
        this.physique_Raw = person.getPhysique_Raw();           // 原始体质
        this.spirit_Raw = person.getSpirit_Raw();               // 原始精神
        this.fascination_Raw = person.getFascination_Raw();     // 原始魅力
        this.luck_Raw = person.getLuck_Raw();                   // 原始运气
        this.skillStrings = person.getSkillStrings();           // 原始技能字符串

        // 初始化技能
        setLevel(person.level);
        initSkill();

        // 刷新属性
        updateAttribute();

        // 初始战斗相关的数据
        this.activeValues = 0;// 战斗活跃值初始为0
        this.camp = camp;
    }

    /**
     * 构造函数 2
     * @param person
     */
    public BattlePerson(NormalPerson person) {
        // 原始资料
        this.psersonId = person.getPsersonId();                 // id唯一标识这个人物
        this.aptitude = person.getAptitude();                   // 资质（影响每次升级 基础属性的增加数量 资质取值为1～5 1为庸碌无为 5为天神下凡）
        this.name = person.getName();                           // 名字
        this.name2 = person.getName2();                         // 名字拼音
        this.sexuality = person.getSexuality();                 // 男1 女0

        // 获得原始属性
        this.strength_Raw = person.getStrength_Raw();           // 原始力量
        this.intellect_Raw = person.getIntellect_Raw();         // 原始智力
        this.dexterity_Raw = person.getDexterity_Raw();         // 原始敏捷
        this.physique_Raw = person.getPhysique_Raw();           // 原始体质
        this.spirit_Raw = person.getSpirit_Raw();               // 原始精神
        this.fascination_Raw = person.getFascination_Raw();     // 原始魅力
        this.luck_Raw = person.getLuck_Raw();                   // 原始运气
        this.skillStrings = person.getSkillStrings();           // 获得原始技能列表

        // 初始化技能
        setLevel(person.level);
        initSkill();

        // 刷新属性
        updateAttribute();

        // 初始战斗相关的数据
        this.HP_Current = HP_MAX;                               // 当前的生命值
        this.activeValues = 0;                                  // 战斗活跃值初始为0
    }

    /**
     * 初始化
     */
    public void init(int battleId) {
        this.activeValues = 0;
        this.battleId = battleId;
    }

    /**
     * 刷新战斗时的属性
     */
    public void updateBattleAttribute() {
        updateBasicAttribute();

        // TODO: 8/29/17 处理影响 基础属性 的效果比如增加力量的装备等等
        // TODO： 处理基础属性 包括 被动技能中涉及基础属性的部分、武器装备中涉及基础属性的部分、阵型中涉及基础属性的部分
        addBasisBuff();

        calcultePanelAttribute();

        // TODO: 8/29/17 处理 面板属性 的效果
        addPanelBuff();
    }

    public int getActiveValues() {
        return activeValues;
    }

    public void setActiveValues(int activeValues) {
        this.activeValues = activeValues;
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

    public int getCamp() {
        return camp;
    }

    public void setCamp(int camp) {
        this.camp = camp;
    }

    public int getDistance() {
        return distance;
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * 计算当前活跃值的百分比
     * @return
     */
    public float getActiveValuePencent(){
        return (float)activeValues / (float)actionValuesMax;
    }

    /**
     * 执行行动之后，需要消耗掉活跃值
     */
    public void reduceActiveValue(){
        int newValues = activeValues - actionValuesMax;
        if(newValues <= 0 ) newValues = 0;
        activeValues = newValues;
    }

    /**
     * 战斗中，隔一段时间会增加活跃值
     */
    public void increaseActiveValues(){
        activeValues += actionSpeed;
    }

    public int getLineupId() {
        return lineupId;
    }

    public void setLineupId(int lineupId) {
        this.lineupId = lineupId;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public boolean isCounsellor() {
        return isCounsellor;
    }

    public void setCounsellor(boolean counsellor) {
        isCounsellor = counsellor;
    }

    public int getHP_Current() {
        return HP_Current;
    }

    public void setHP_Current(int HP_Current) {
        if(HP_Current >= HP_MAX){
            HP_Current = this.HP_MAX;
        }
        this.HP_Current = HP_Current;
    }

    @Override
    public String toString() {
        return "NormalPerson{" +
                "psersonId=" + psersonId +
                ", aptitude=" + aptitude +
                ", name='" + name + '\'' +
                ", HP_current='" + HP_Current +
                ", sexuality=" + sexuality +
                ", strength_Raw=" + strength_Raw +
                ", intellect_Raw=" + intellect_Raw +
                ", dexterity_Raw=" + dexterity_Raw +
                ", physique_Raw=" + physique_Raw +
                ", spirit_Raw=" + spirit_Raw +
                ", luck_Raw=" + luck_Raw +
                ", fascination_Raw=" + fascination_Raw +
                ", battleId=" + battleId +
                ", activeValues=" + activeValues +
                ", camp=" + camp +
                ", isCounsellor=" + isCounsellor +
                ", isLeader=" + isLeader +
                ", strength=" + strength +
                ", intellect=" + intellect +
                ", dexterity=" + dexterity +
                ", physique=" + physique +
                ", spirit=" + spirit +
                ", luck=" + luck +
                ", skillStrings='" + skillStrings + '\'' +
                ", skillArrays=" + skillArrays +
                ", startLevel=" + level +
                ", HP_MAX=" + HP_MAX +
                ", experiencePoint=" + experiencePoint +
                ", physicDamage=" + physicDamage +
                ", magicDamage=" + magicDamage +
                ", realDamage=" + realDamage +
                ", physicsPenetrate=" + physicsPenetrate +
                ", magicPenetrate=" + magicPenetrate +
                ", accuracy=" + accuracy +
                ", criteRate=" + criteRate +
                ", reduceBeCriteRate=" + reduceBeCriteRate +
                ", criteDamage=" + criteDamage +
                ", armor=" + armor +
                ", magicResist=" + magicResist +
                ", dodge=" + dodge +
                ", block=" + block +
                ", actionSpeed=" + actionSpeed +
                ", hpRestore=" + hpRestore +
                ", fascination=" + fascination +
                '}';
    }

    @Override
    public void showSkill() {
        SimpleLog.logd(TAG,"===showSkill()===");
        if(buffPassive == null){
            SimpleLog.loge(TAG,"buffPassive == null");
        }else{
            if(buffPassive.size() == 0){
                SimpleLog.loge(TAG,"buffPassive size == 0");
            }
        }
        if(skillActivesLists == null){
            SimpleLog.loge(TAG,"skillActivesLists == null");
        }else{
            if(skillActivesLists.size() == 0){
                SimpleLog.loge(TAG,"skillActivesLists size == 0");
            }
        }
        ShowUtils.showArrayLists(TAG,buffPassive);
        ShowUtils.showArrayLists(TAG,skillActivesLists);
    }
}
