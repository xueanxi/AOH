package com.game.xianxue.ashesofhistory.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by user on 8/25/17.
 */
public class SkillTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSkillHappen() throws Exception {
        Skill s1 = new Skill("普通攻击",0,0);
        Skill s2 = new Skill("破血狂攻",0.3f,0);
        Skill s3 = new Skill("力劈华山",0.3f,0);
        Skill s4 = new Skill("回血",0.1f,0);
        ArrayList<Skill> lists = new ArrayList<Skill>();
        lists.add(s1);
        lists.add(s2);
        lists.add(s3);
        lists.add(s4);

        int result = 0;
        int one = 0;
        int two = 0;
        int thr = 0;
        int fou = 0;
        for(int i =0 ;i< 100000;i++){
            Skill get = null;
            if(RandomUtils.isHappen(0.5f)){
                get = lists.get(0);
                one++;
            }else{
                float[] arrays = new float[lists.size()];
                for(int j = 0;j<lists.size();j++){
                    arrays[j] = lists.get(j).probability;
                }
                result = RandomUtils.selectOneByProbability(arrays);
                if(1 == result){
                    two ++;
                }else if(2 == result){
                    thr ++;
                }else if(3 == result){
                    fou ++;
                }
            }
        }
        System.out.println("普通攻击 :"+one);
        System.out.println("破血狂攻 :"+two);
        System.out.println("力劈华山 :"+thr);
        System.out.println("回血回血 :"+fou);
    }

    class Skill{
        float probability;
        String name;
        int cdTime;

        Skill(String name,float probability,int cdTime){
            this.name = name;
            this.probability = probability;
            this.cdTime = cdTime;
        }
    }
}