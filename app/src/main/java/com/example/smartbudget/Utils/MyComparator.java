package com.example.smartbudget.Utils;

import java.util.Comparator;
import java.util.Map;

public class MyComparator implements Comparator {

    Map map;

    public MyComparator(Map map) {
        this.map = map;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return (o2.toString()).compareTo(o1.toString());
    }
}
