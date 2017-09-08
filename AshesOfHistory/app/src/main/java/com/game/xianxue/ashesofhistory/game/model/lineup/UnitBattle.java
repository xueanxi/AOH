package com.game.xianxue.ashesofhistory.game.model.lineup;

/**
 * 战斗阵型中每一个位置的信息
 * 对 UnitBase 进行了扩展
 * Created by user on 9/4/17.
 */
public class UnitBattle extends UnitBase {
    private static final String TAG = "=UnitBattle";

    int personIndex = -1;    //    站在这个位置的人物的索引值

    /**
     * 构造函数,
     *
     * @param base
     */
    public UnitBattle(UnitBase base) {
        this.x = base.getX();
        this.y = base.getY();
        this.buffIDs = base.getBuffIDs();
        this.canSetPerson = true;
    }

    public int getPersonIndex() {
        return personIndex;
    }

    public void setPersonIndex(int personIndex) {
        this.personIndex = personIndex;
    }

    @Override
    public String toString() {
        return "UnitBattle{" +
                "index=" + personIndex +
                ", x=" + x +
                ", y=" + y +
                ", buffIDs=" + buffIDs +
                ", canSetPerson=" + canSetPerson +
                '}';
    }
}
