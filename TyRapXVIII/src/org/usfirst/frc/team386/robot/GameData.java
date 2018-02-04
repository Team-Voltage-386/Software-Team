package org.usfirst.frc.team386.robot;

public class GameData {

    public static String gamedata;

    /**
     * Return true if the alliance switch is on the left.
     * 
     * @return True if the switch is left.
     */
    public static boolean isSwitchLeft() {
	return gamedata.equals(Robot.LLL) || gamedata.equals(Robot.LRL);
    }

    /**
     * Return true if the alliance switch is on the left.
     * 
     * @return True if the switch is left.
     */
    public static boolean isSwitchRight() {
	return gamedata.equals(Robot.RRR) || gamedata.equals(Robot.RLR);
    }

    /**
     * Return true if the alliance scale is on the left.
     * 
     * @return True if the scale is left
     */
    public static boolean isScaleLeft() {
	return gamedata.equals(Robot.LLL) || gamedata.equals(Robot.RLR);
    }

    /**
     * Return true if the alliance scale is on the right.
     * 
     * @return True if the scale is right
     */
    public static boolean isScaleRight() {
	return gamedata.equals(Robot.RRR) || gamedata.equals(Robot.LRL);
    }
}
