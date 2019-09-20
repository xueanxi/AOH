package com.game.xianxue.aoh.comparator;

import com.game.xianxue.aoh.game.model.person.BattlePerson;

import java.util.Comparator;

public class ActiveValueComparator implements Comparator<BattlePerson> {
    @Override
    public int compare(BattlePerson o1, BattlePerson o2) {
        if(o1.getActiveValues() < o2.getActiveValues()){
            return -1;
        }else if(o1.getActiveValues() > o2.getActiveValues()){
            return 1;
        }
        return 0;
    }
}
