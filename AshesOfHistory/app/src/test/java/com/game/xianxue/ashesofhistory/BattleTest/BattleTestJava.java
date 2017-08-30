package com.game.xianxue.ashesofhistory.BattleTest;

import com.game.xianxue.ashesofhistory.game.engine.BattleEngine;
import com.game.xianxue.ashesofhistory.model.TeamModel;
import com.game.xianxue.ashesofhistory.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.model.person.NormalPerson;
import com.game.xianxue.ashesofhistory.utils.SerializableUtils;
import com.game.xianxue.ashesofhistory.utils.ShowUtils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by user on 8/25/17.
 */
public class BattleTestJava {
    private static String TAG = "==BattleTestJava";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testStartBattle() throws Exception {
        ArrayList<BasePerson> personlists = getAllPerson();
        BasePerson play1 = personlists.get(0);
        BasePerson play2 = personlists.get(1);
        BasePerson play3 = personlists.get(2);
        BasePerson play4 = personlists.get(3);
        BasePerson play5 = personlists.get(4);
        BasePerson play6 = personlists.get(5);
        BasePerson play7 = personlists.get(6);
        BasePerson play8 = personlists.get(7);

        NormalPerson n1 = new NormalPerson(play1);
        NormalPerson n2 = new NormalPerson(play2);
        NormalPerson n3 = new NormalPerson(play3);
        NormalPerson n4 = new NormalPerson(play4);
        NormalPerson n5 = new NormalPerson(play5);
        NormalPerson n6 = new NormalPerson(play6);
        NormalPerson n7 = new NormalPerson(play7);
        NormalPerson n8 = new NormalPerson(play8);

        BattlePerson b1 = new BattlePerson(n1, TeamModel.CAMP_LEFT);
        BattlePerson b2 = new BattlePerson(n2, TeamModel.CAMP_LEFT);
        BattlePerson b3 = new BattlePerson(n3, TeamModel.CAMP_LEFT);
        BattlePerson b4 = new BattlePerson(n4, TeamModel.CAMP_LEFT);
        BattlePerson b5 = new BattlePerson(n5, TeamModel.CAMP_RIGHT);
        BattlePerson b6 = new BattlePerson(n6, TeamModel.CAMP_RIGHT);
        BattlePerson b7 = new BattlePerson(n7, TeamModel.CAMP_RIGHT);
        BattlePerson b8 = new BattlePerson(n8, TeamModel.CAMP_RIGHT);

        ArrayList<BattlePerson> playerList1 = new ArrayList<BattlePerson>();
        playerList1.add(b1);
        playerList1.add(b2);
        playerList1.add(b3);
        playerList1.add(b4);

        ArrayList<BattlePerson> playerList2 = new ArrayList<BattlePerson>();
        playerList2.add(b5);
        playerList2.add(b6);
        playerList2.add(b7);
        playerList2.add(b8);

        TeamModel t1 = new TeamModel(TeamModel.CAMP_LEFT, playerList1);
        TeamModel t2 = new TeamModel(TeamModel.CAMP_RIGHT, playerList2);

        BattleEngine engine = BattleEngine.getInstance();
        engine.setmTimeIncreseActive(200);
        engine.setmTimePerAction(3000);
        engine.setBattleTeam(t1, t2);
        engine.startBattle();

        try {
            Thread.sleep(999999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPerson() throws Exception {
        ShowUtils.showArraysJava(TAG,getAllPerson());
    }

    public ArrayList<BasePerson> getAllPerson() {
        ArrayList<BasePerson> list = null;
        list = SerializableUtils.readObjectFromFileForTest("data/baseperson");

        return list;
    }
}