package com.game.xianxue.ashesofhistory.game.model.lineup;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.game.model.person.BattlePerson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * 阵型中的每一个基本单位(战斗中)
 * Created by user on 9/4/17.
 */
public class LineupUnit extends LineupUnitBase{

    private static final String TAG = "=LineupUnit";

    BattlePerson person = null;         // 站在整形这个位置上面的人物

    public LineupUnit(LineupUnitBase base) {
        this.x = base.x;
        this.y = base.y;
        this.buffIDs = base.buffIDs;
        this.canSetPerson = base.canSetPerson;
    }

    @Override
    public BattlePerson getPerson() {
        return person;
    }

    @Override
    public void setPerson(BattlePerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "LineupUnitBase{" +
                "x=" + x +
                ", y=" + y +
                ", buffIDs=" + buffIDs +
                ", canSetPerson=" + canSetPerson +
                ", person=" + person +
                '}';
    }
}
