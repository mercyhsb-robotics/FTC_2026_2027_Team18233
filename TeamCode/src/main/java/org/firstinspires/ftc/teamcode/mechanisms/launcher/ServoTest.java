package org.firstinspires.ftc.teamcode.mechanisms.launcher;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoTest extends OpMode {
    Servo servo;

    Servo feeder;

    private static float CLAW_POS_DOWN = 0.37F;

    private static float CLAW_POS_UP = 0.0F;

    private static float FEEDER_POS_DOWN = 1;
    private static float FEEDER_POS_UP = 0.67F;

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, "claw");
        servo.setPosition(0.0);

        feeder = hardwareMap.get(Servo.class, "feeder");
        feeder.setDirection(Servo.Direction.REVERSE);
        feeder.setPosition(FEEDER_POS_DOWN);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_left) {
            servo.setPosition(CLAW_POS_DOWN);
        } else if (gamepad1.dpad_right) {
            servo.setPosition(CLAW_POS_UP);
        } else if (gamepad1.dpad_up) {
            feeder.setPosition(FEEDER_POS_UP);
        } else if (gamepad1.dpad_down) {
            feeder.setPosition(FEEDER_POS_DOWN );
        }
    }
}
