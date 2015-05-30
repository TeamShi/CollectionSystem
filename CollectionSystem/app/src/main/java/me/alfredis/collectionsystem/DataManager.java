package me.alfredis.collectionsystem;

import java.util.ArrayList;

import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.RigEvent;

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

    public static int getHoleIndexByHoleId(String id) {
        for (int i = 0; i < holes.size(); i++) {
            if (holes.get(i).getHoleId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public static ArrayList<RigEvent> getRigEventListByHoleId(String holeId) {
        for (Hole hole : holes) {
            if (hole.getHoleId().equals(holeId)) {
                return hole.getRigLists();
            }
        }
        return null;
    }

    public static void AddRigByHoleId (String holeId, RigEvent rig) {
        for (Hole hole : holes) {
            if (hole.getHoleId().equals(holeId)) {
                hole.getRigLists().add(rig);
            }
        }
    }

    public static void deleteRig(String holeId, int index) {
        for (Hole hole : holes) {
            if (hole.getHoleId().equals(holeId)) {
                hole.getRigLists().remove(index);
            }
        }
    }
}
