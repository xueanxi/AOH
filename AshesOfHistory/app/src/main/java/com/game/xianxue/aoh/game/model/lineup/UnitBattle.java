package com.game.xianxue.aoh.game.model.lineup;

/**
 * 战斗阵型中每一个位置的信息
 * 对 FormationUnitBase 进行了扩展
 * Created by user on 9/4/17.
 */
public class UnitBattle extends FormationUnitBase {
    private static final String TAG = "=UnitBattle";

    private int personIndex = -1;     // 站在这个位置的人物的索引值
    private boolean isEmpty = true;   // 位置是否是空的，是否被人站了

    /**
     * 构造函数,
     *
     * @param base
     */
    public UnitBattle(FormationUnitBase base) {
        this.row = base.getRow();
        this.col = base.getCol();
        this.isCounsellor = base.isCounsellor();
        this.isLeader = base.isLeader();
        this.canSetPerson = base.isCanSetPerson();
        this.buffIDs = base.getBuffIDs();
        this.isEmpty = true;
    }

    public int getPersonIndex() {
        return personIndex;
    }

    public void setPersonIndex(int personIndex) {
        this.personIndex = personIndex;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    @Override
    public String toString() {
        return "UnitBattle{" +
                "index=" + personIndex +
                ", row=" + row +
                ", col=" + col +
                ", buffIDs=" + buffIDs +
                ", isEmpty=" + isEmpty +
                ", canSetPerson=" + canSetPerson +
                '}';
    }
}
