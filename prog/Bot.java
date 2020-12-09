package prog;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

import java.awt.*;

public class Bot extends AdvancedRobot {
    int moveDirection=1;//Hvilken vej den skal køre

    public void run() {
        setAdjustRadarForRobotTurn(true);//Den holder radaren stille når vi drejer
        setBodyColor(new Color(128, 128, 50));
        setGunColor(new Color(50, 50, 20));
        setRadarColor(new Color(200, 200, 70));
        setScanColor(Color.white);
        setBulletColor(Color.blue);
        setAdjustGunForRobotTurn(true); // Holder gun stille når vi drejer
        turnRadarRightRadians(Double.POSITIVE_INFINITY);//hele tiden dejer radaren til højre
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        double absBearing=e.getBearingRadians()+getHeadingRadians();//enemies absolute bearing
        double latVel=e.getVelocity() * Math.sin(e.getHeadingRadians() -absBearing);//fjender senere hastighed
        double gunTurnAmt;//amount to turn our gun
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());//låse på radaren
        if(Math.random()>.9){
            setMaxVelocity((12*Math.random())+12);//tilfældigt ændre hastigheden
        }
        if (e.getDistance() > 150) {
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/22);//Drej din pistol en lille smule
            setTurnGunRightRadians(gunTurnAmt); //Drej din pistol
            setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing-getHeadingRadians()+latVel/getVelocity()));//kør mod fjendernes forudsagte fremtidige placering
            setAhead((e.getDistance() - 140)*moveDirection);//Fremad
            setFire(3);//Åben ild
        }
        else{//Hvis fjenden er tæt nok på
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing- getGunHeadingRadians()+latVel/15);//Drej din pistol en lille smule
            setTurnGunRightRadians(gunTurnAmt);//Drej vores pistol
            setTurnLeft(-90-e.getBearing()); //drej vinkelret på fjenden
            setAhead((e.getDistance() - 140)*moveDirection);//Fremad
            setFire(3);//ÅBEN ILD!
        }
    }
    public void onHitWall(HitWallEvent e){
        moveDirection=-moveDirection;//Modstatte vej når du rammer en væg
    }
    /**
     * onWin:  Laver en sejers dans
     */
    public void onWin(WinEvent e) {
        for (int i = 0; i < 50; i++) {
            turnRight(30);
            turnLeft(30);
        }
    }

}
