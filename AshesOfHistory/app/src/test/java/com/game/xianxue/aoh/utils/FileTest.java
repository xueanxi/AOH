package com.game.xianxue.aoh.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 8/25/17.
 */
public class FileTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testWriteObject() throws Exception {
        Skill s1 = new Skill("普通攻击",0,0);
        Skill s2 = new Skill("破血狂攻",0.3f,0);
        Skill s3 = new Skill("力劈华山",0.3f,0);
        Skill s4 = new Skill("回血",0.1f,0);
        ArrayList<Skill> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        SerializableUtils.writeObjectToFileForTest2(list,"test2");
    }

    @Test
    public void testReadObject() throws Exception {
        ArrayList<String> list = SerializableUtils.readObjectFromFileForTest2("test1");
        ShowUtils.showArrayListsJava("TAG",list);
    }

    public class Skill implements Serializable{
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