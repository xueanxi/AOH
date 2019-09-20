package com.game.xianxue.aoh.game.model.person;

import com.game.xianxue.aoh.game.skill.SkillBase;

/**
 * 存放人物技能相关的信息
 */

public class SkillBean {
    protected int skillID;                  // 技能ID
    protected SkillBase skillBase = null;   // 技能的实体类
    protected int unLockLevel;              // 解锁此技能时，人物的等级
    protected int startLevel;               // 人物解锁此技能时，此技能的初始等级
    protected int grow;                     // 技能的成长，比如当grow等于3时,技能解锁之后，人物每升3级，技能等级提高一级

    public int getSkillID() {
        return skillID;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public SkillBase getSkill() {
        return skillBase;
    }

    public void setSkill(SkillBase skillBase) {
        this.skillBase = skillBase;
    }

    public int getUnLockLevel() {
        return unLockLevel;
    }

    public void setUnLockLevel(int unLockLevel) {
        this.unLockLevel = unLockLevel;
    }

    public int getStartLevel() {
        return startLevel;
    }

    public void setStartLevel(int startLevel) {
        this.startLevel = startLevel;
    }

    public int getGrow() {
        return grow;
    }

    public void setGrow(int grow) {
        this.grow = grow;
    }

    /**
     * 根据人物当前的等级，返回 此技能成长之后的等级
     * @param personLevel
     * @return
     */
    public int getCurrentSkillLevel(int personLevel){
        int skillLevel = this.startLevel;
        if(personLevel > this.unLockLevel){
            skillLevel = this.startLevel + (personLevel - this.unLockLevel)/this.grow;
        }
        return skillLevel;
    }

    @Override
    public String toString() {
        return "SkillBean{" +
                "skillID=" + skillID +
                ", skillBase=" + skillBase +
                ", unLockLevel=" + unLockLevel +
                ", startLevel=" + startLevel +
                ", grow=" + grow +
                '}';
    }
}
