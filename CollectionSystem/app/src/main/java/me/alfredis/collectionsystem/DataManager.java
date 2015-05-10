package me.alfredis.collectionsystem;

import java.util.ArrayList;

import me.alfredis.collectionsystem.datastructure.Hole;

/**
 * Created by Alfred on 15/5/10.
 */
public class DataManager {
    public static ArrayList<Hole> holes;

    static {
        holes = new ArrayList<Hole>();
    }

    public static boolean isHoleIdExist(String holeId) {
        for (Hole hole : holes) {
            if (hole.getHoleId().equals(holeId)) {
                return true;
            }
        }

        return false;
    }
}
