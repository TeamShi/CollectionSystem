package me.alfredis.collectionsystem;

import java.util.ArrayList;

import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.RigEvent;

/**
 * Created by Alfred on 15/5/10.
 */
public class DataManager {
    public static ArrayList<Hole> holes;

    public static Hole lastHole;
    public static RigEvent lastRig;

    public static RigEvent copiedRig;

    static {
        holes = new ArrayList<Hole>();
    }

    public static Hole getHole(String holeId) {
        for (Hole hole : holes) {
            if (hole.getHoleId().equals(holeId)) {
                return hole;
            }
        }
        return null;
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
                return hole.getRigList();
            }
        }
        return null;
    }

    public static void AddRigByHoleId (String holeId, RigEvent rig) {
        for (Hole hole : holes) {
            if (hole.getHoleId().equals(holeId)) {
                hole.getRigList().add(rig);
            }
        }
    }

    public static void deleteRig(String holeId, int index) {
        for (Hole hole : holes) {
            if (hole.getHoleId().equals(holeId)) {
                hole.getRigList().remove(index);
            }
        }
    }

    public static int getLatestRigPipeId(String holeId) {
        for (Hole hole : holes) {
            if (hole.getHoleId().equals(holeId)) {
                int pipeId = 0;
                for (RigEvent rig : hole.getRigList()) {
                    if (rig.getDrillPipeId() > pipeId) {
                        pipeId = rig.getDrillPipeId();
                    }
                }

                return pipeId + 1;
            }
        }

        return -1;
    }

    public static double calculateCumulativePipeLength(String holeId) {
        for (Hole hole : holes) {
            if (hole.getHoleId().equals(holeId)) {
                double length = 0;
                for (RigEvent rig : hole.getRigList()) {
                    length += rig.getDrillPipeLength();
                }
                return length;
            }
        }

        return -1;
    }

    public static double getLatestRoundTripDepth(String holeId) {
        for (Hole hole : holes) {
            if (hole.getHoleId().equals(holeId)) {
                if (hole.getRigList().size() == 0) {
                    return -1;
                } else {
                    return hole.getRigList().get(hole.getRigList().size() - 1).getCumulativeMeterage();
                }
            }
        }

        return -1;
    }

    public static boolean needAddPipeId(String holeId) {
        for (Hole hole : holes) {
            if (hole.getHoleId().equals(holeId)) {
                if(hole.getRigList().get(hole.getRigList().size() - 1).getRoundTripMeterage() > 0
                        && hole.getRigList().get(hole.getRigList().size() - 1).getDrillToolRemainLength() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
