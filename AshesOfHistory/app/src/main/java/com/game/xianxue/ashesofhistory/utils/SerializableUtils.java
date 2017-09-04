package com.game.xianxue.ashesofhistory.utils;

import com.game.xianxue.ashesofhistory.game.model.person.BasePerson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by user on 8/30/17.
 */

public class SerializableUtils {

    private static final String dirName = "SerializableObject";

    public static void writeObjectToFile(ArrayList<BasePerson> datas, String fileName) {
        String writePath = FileUtils.getMyPath() + File.separator + dirName;
        FileUtils.mkdirsIfNotExists(writePath);
        File file = new File(writePath + File.separator + fileName);

        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(datas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<BasePerson> readObjectFromFile(String fileName) {
        String readPath = FileUtils.getMyPath() + File.separator + dirName;
        FileUtils.mkdirsIfNotExists(readPath);
        File file = new File(readPath + File.separator + fileName);

        ObjectInputStream in = null;
        ArrayList<BasePerson> result = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file));
            result = (ArrayList<BasePerson>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


    //===================以下方法只用于单元测试==================
    public static void writeObjectToFileForTest(ArrayList<String> datas, String fileName) {
        File file = new File(fileName);
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(datas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<BasePerson> readObjectFromFileForTest(String fileName) {
        File file = new File(fileName);
        ObjectInputStream in = null;
        ArrayList<BasePerson> result = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file));
            result = (ArrayList<BasePerson>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
