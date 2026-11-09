package org.firstinspires.ftc.teamcode.robots.biobuzz;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.mechanisms.elevator.Elevator;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.launcher.Launcher;
import org.firstinspires.ftc.teamcode.mechanisms.mecanumDrive.MecanumDrive;

@TeleOp
public class BioBuzzRobot extends OpMode {
    private MecanumDrive mecanumDrive;

    private Launcher launcher;
    private Intake intake;
    private Servo claw;

    double forward;
    double strafe;
    double rotate;

    private static float CLAW_POS_DOWN = 0.37F;

    private static float CLAW_POS_UP = 0.0F;

    private static float FEEDER_POS_DOWN = 1;
    private static float FEEDER_POS_UP = 0.67F;

    @Override
    public void init() {
        // Initialize mecanum drive
        mecanumDrive = new MecanumDrive();
        mecanumDrive.init(hardwareMap);

        // Initialize launcher
        launcher = new Launcher();
        launcher.init(hardwareMap, telemetry);

        // Initialize intake
        intake = new Intake();
        intake.init(hardwareMap);

        // Initialize claw
        claw = hardwareMap.get(Servo.class, "claw");
        claw.setPosition(0.0);
    }

    @Override
    public void stop() {
        intake.stopIntake();
        launcher.stop();
    }

    @Override
    public void loop() {
        forward = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        telemetry.addData("forward", forward);
        telemetry.addData("strafe", strafe);
        telemetry.addData("rotate", rotate);

        mecanumDrive.drive(forward, strafe, rotate);

        // Controls for claw
        if (gamepad1.left_trigger > 0.5) {
            claw.setPosition(CLAW_POS_UP);
        }

        if (gamepad1.right_trigger > 0.5) {
            claw.setPosition(CLAW_POS_DOWN);
        }

        // Controls for feeder
        if (gamepad1.left_bumper)
            launcher.feederDown();

        if (gamepad1.right_bumper)
            launcher.feederUp();

        // Controls for launcher
        if (gamepad1.triangle) {
            launcher.startFlywheel();
        }
        if (gamepad1.square) {
            launcher.launch(false);
        }

        // Controls for intake
        if (gamepad1.cross)
            intake.stopIntake();
        if (gamepad1.circle)
            intake.startIntake();

        // Controls for DPad
        // TODO:  We can use this for automated features, such as automatically
        // search for pollen and intake.
        if (gamepad1.dpad_up) {
            // TODO
            telemetry.addLine("'dpad_up' was pressed");
            launcher.startFlywheel();
            launcher.increaseRpm(700);
        }
        if (gamepad1.dpad_down) {
            // TODO
            telemetry.addLine("'dpad_down' was pressed");
            launcher.startFlywheel();
            launcher.increaseRpm(800);
        }
        if (gamepad1.dpad_left) {
            launcher.reverseFlywheel();
        }
        if (gamepad1.dpad_right) {
            intake.reverseIntake();
        }


    }
}
