package com.example.demo.service;

import com.example.demo.entity.Welcome;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: boot-kafka
 * @description: 全局存储消息记录（目前没用到）
 * @author: 001977
 * @create: 2018-07-11 14:42
 */
public class GlobalSave {
    private GlobalSave(){}

    private static class Inner{
        private static GlobalSave instance = new GlobalSave();
    }

    public static GlobalSave getInstance(){
        return Inner.instance;
    }

    private ConcurrentHashMap<String, List<Welcome>> map = new ConcurrentHashMap<>();

    public void add(String key, Welcome value){
        List<Welcome> welcomes = map.get(key);
        if (welcomes == null){
            welcomes = new ArrayList<>();
        }
        welcomes.add(value);
    }

    public List<Welcome> getAll(String key){
        return map.get(key);
    }

    public Welcome getLast(String key){
        List<Welcome> list = map.get(key);
        if (list.isEmpty())
            return null;
        return list.get(list.size()-1);
    }
}
