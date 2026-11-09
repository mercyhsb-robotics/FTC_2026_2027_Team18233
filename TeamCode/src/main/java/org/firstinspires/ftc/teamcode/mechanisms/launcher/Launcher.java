package org.firstinspires.ftc.teamcode.mechanisms.launcher;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robots.biobuzz.HardwareMapConstants;

public class Launcher {

    private Telemetry telemetry;
    private DcMotorEx flywheel;
    private Servo feeder;

    public final static int LAUNCHER_TARGET_VELOCITY = 1200;
    public final static int LAUNCHER_MIN_VELOCITY = 1200;

    private static float FEEDER_POS_DOWN = 1;
    private static float FEEDER_POS_UP = 0.67F;

    public void init (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        // Initialize flywheel and feeder
        flywheel = hardwareMap.get(DcMotorEx.class, HardwareMapConstants.FLYWHEEL);
        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel.setDirection(DcMotor.Direction.REVERSE);
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        feeder = hardwareMap.get(Servo.class, HardwareMapConstants.FEEDER);
        feeder.setDirection(Servo.Direction.REVERSE);
        feeder.setPosition(FEEDER_POS_DOWN);
    }

    public void increaseRpm(long rpm) {
        telemetry.addLine("Increasing rpm to " + rpm);
        telemetry.update();

        // Add code to increase rpm
        flywheel.setPower(0.3);
        flywheel.setVelocity(rpm);
    }
    public void launch(boolean isLaunch) {
        telemetry.addLine("Launching game piece...");
        telemetry.update();

        // Add code to launch the game piece
        if (isLaunch) {
            flywheel.setVelocity(LAUNCHER_TARGET_VELOCITY);
        } else {
            flywheel.setVelocity(0);
        }

        /*
         * Here we ask if the driver is currently pressing the right bumper, AND the launcher is
         * spinning fast enough to make a successful shot. If it is, then we will turn on the
         * windmill servo to start feeding the elements into the launcher motor. We also
         * add some power to the intake power. This can sometimes help dislodge stuck elements from
         * inside the hopper.
         */
        if (isLaunch && flywheel.getVelocity() > LAUNCHER_MIN_VELOCITY) {
            feeder.setPosition(FEEDER_POS_UP);
//            intakePower += 0.5;
        } else {
            feeder.setPosition(FEEDER_POS_DOWN );
        }
    }

    public  boolean isAtTargetRpm() {
        // Add code to check if the flywheel is at the target RPM
        return true;
    }

    public void stop() {

        flywheel.setPower(0);
        feeder.setPosition(FEEDER_POS_DOWN );
    }

    public void startFlywheel() {
        flywheel.setPower(1);
        flywheel.setVelocity(LAUNCHER_TARGET_VELOCITY);
    }

    public void reverseFlywheel()   {
        flywheel.setPower(-1);
    }

    public void feederUp() {
        feeder.setPosition(FEEDER_POS_UP );
    }

    public void feederDown() {
        feeder.setPosition(FEEDER_POS_DOWN );
    }

    public void feederStop() {
        feeder.setPosition(FEEDER_POS_DOWN );;
    }
}
