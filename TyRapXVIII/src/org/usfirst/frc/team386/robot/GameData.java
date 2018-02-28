package org.usfirst.frc.team386.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Provide a nice API for reading and querying game data.
 */
public class GameData {

    // Game data configurations
    public static final String LLL = "LLL";
    public static final String LRL = "LRL";
    public static final String RRR = "RRR";
    public static final String RLR = "RLR";

    private String gameData = "";

    /**
     * Read game data from the driver station and save it for later use.
     * 
     * Will also put the game data string into the dashboard for debugging purposes.
     */
    public void readGameData() {
	while (gameData.length() < 3)
	    gameData = DriverStation.getInstance().getGameSpecificMessage();
	SmartDashboard.putString(Robot.GAME_DATA, gameData);
    }

    /**
     * Return true if the alliance switch is on the left.
     * 
     * @return True if the switch is left.
     */
    public boolean isSwitchLeft() {
	return gameData.equals(LLL) || gameData.equals(LRL);
    }

    /**
     * Return true if the alliance switch is on the left.
     * 
     * @return True if the switch is left.
     */
    public boolean isSwitchRight() {
	return gameData.equals(RRR) || gameData.equals(RLR);
    }

    /**
     * Return true if the alliance scale is on the left.
     * 
     * @return True if the scale is left
     */
    public boolean isScaleLeft() {
	return gameData.equals(LLL) || gameData.equals(RLR);
    }

    /**
     * Return true if the alliance scale is on the right.
     * 
     * @return True if the scale is right
     */
    public boolean isScaleRight() {
	return gameData.equals(RRR) || gameData.equals(LRL);
    }
}
