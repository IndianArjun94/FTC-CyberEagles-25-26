package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.module.AprilTagModule;
import org.firstinspires.ftc.teamcode.module.ObeliskPattern;

@TeleOp(name = "TeleOp ALPHA")
public class TeleOpALPHAMeet3 extends OpMode {

    private static DcMotor leftFrontMotor;
    private static DcMotor rightFrontMotor;
    private static DcMotor leftBackMotor;
    private static DcMotor rightBackMotor;

    private static double leftFrontPower;
    private static double leftBackPower;
    private static double rightFrontPower;
    private static double rightBackPower;

    private static final double SPEED_CAP = 0.8f;
    private static final double LAUNCHER_MIN = 0.5f;
    private static double SHOOTING_WHEEL_MULTIPLIER = 0.18f;
    private static double launcherPowerBoost = 0.0;
    private static boolean intakeActive = false;
    private static CRServo frontLeftLoad;
    private static CRServo backLeftLoad;
    private static CRServo frontRightLoad;
    private static CRServo backRightLoad;
    private static DcMotor launcherMotor;
    private static DcMotor intake;
    private static double previousIntakeUpdateTime = System.currentTimeMillis();
    private static String MODE = "Regular Power";
    private static boolean launcherActive = false;
    private static long previousLauncherUpdateTime;
    private static boolean launcherSequenceStarted = false;
    private static long launcherSequenceStartTime;
    private static boolean launcherSequenceLaunched = false;
    private static long launcherSequenceLaunchTime;
    private static boolean yJustPressed = false;
    private static boolean aJustPressed = false;
    private static long intakeStopTime;

    @Override
    public void init() {
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFront");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFront");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBack");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBack");
        intake = hardwareMap.get(DcMotor.class, "intake");

        frontLeftLoad = hardwareMap.get(CRServo.class, "frontLeftLoad");
        backLeftLoad = hardwareMap.get(CRServo.class, "backLeftLoad");
        frontRightLoad = hardwareMap.get(CRServo.class, "frontRightLoad");
        backRightLoad = hardwareMap.get(CRServo.class, "backRightLoad");

        launcherMotor = hardwareMap.get(DcMotor.class, "launcher");
        frontRightLoad.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightLoad.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        launcherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        AprilTagModule.init(hardwareMap, true);

    }


    //  Update Function that runs after every loop  //
    public static void updateDrivetrainPower() {
        leftFrontMotor.setPower(leftFrontPower * SPEED_CAP);
        leftBackMotor.setPower(leftBackPower * SPEED_CAP);
        rightBackMotor.setPower(rightBackPower * SPEED_CAP);
        rightFrontMotor.setPower(rightFrontPower * SPEED_CAP);
    }

    @Override
    public void loop() {

        leftFrontPower = 0;
        rightFrontPower = 0;
        leftBackPower = 0;
        rightBackPower = 0;

        double gamepad1_x2 = gamepad1.right_stick_x;
        double gamepad1_y = -gamepad1.left_stick_y;
        double gamepad1_x = -gamepad1.left_stick_x;

        // Rotation
        leftBackPower += gamepad1_x2;
        leftFrontPower += gamepad1_x2;
        rightFrontPower -= gamepad1_x2;
        rightBackPower -= gamepad1_x2;

        // Forward/Backward
        leftFrontPower += gamepad1_y;
        leftBackPower += gamepad1_y;
        rightFrontPower += gamepad1_y;
        rightBackPower += gamepad1_y;

        // Lateral
        leftFrontPower -= gamepad1_x;
        leftBackPower += gamepad1_x;
        rightFrontPower += gamepad1_x;
        rightBackPower -= gamepad1_x;

//        Intake
        if (gamepad2.left_bumper && System.currentTimeMillis()-previousIntakeUpdateTime > 250) {
            if (intakeActive) {
                intakeActive = false;
                previousIntakeUpdateTime = System.currentTimeMillis();
            } else {
                intakeActive = true;
                previousIntakeUpdateTime = System.currentTimeMillis();
            }
        }

        if (intakeActive) {
            intake.setPower(1);
            frontLeftLoad.setPower(1);
            backLeftLoad.setPower(1);
            frontRightLoad.setPower(1);
            backRightLoad.setPower(1);
        } else {
            intake.setPower(0);
            frontLeftLoad.setPower(0);
            backLeftLoad.setPower(0);
            frontRightLoad.setPower(0);
            backRightLoad.setPower(0);
        }

        // Modes to control power
        telemetry.addData("Launcher Mode: ", MODE);
        telemetry.addData("Launcher Speed Target: ", LAUNCHER_MIN + (SHOOTING_WHEEL_MULTIPLIER) + launcherPowerBoost);

//        Loading + Launching

        if (gamepad2.right_bumper && System.currentTimeMillis()-previousLauncherUpdateTime > 250) {
            if (launcherActive) {
                launcherActive = false;
                previousLauncherUpdateTime = System.currentTimeMillis();
            } else {
                launcherActive = true;
                previousLauncherUpdateTime = System.currentTimeMillis();
            }
        }

        if (launcherActive) launcherMotor.setPower(1);
        else launcherMotor.setPower(0);

//        Launcher Power Boost
        if (!gamepad2.y && yJustPressed) {
            yJustPressed = false;
        }

        if (gamepad2.y && !yJustPressed) {
            launcherPowerBoost += 0.025;
            yJustPressed = true;
        }

        if (!gamepad2.a && aJustPressed) {
            aJustPressed = false;
        }

        if (gamepad2.a && !aJustPressed) {
            launcherPowerBoost -= 0.025;
            aJustPressed = true;
        }

//        Launcher Power Reset
        if (gamepad2.dpadDownWasPressed()) {
            launcherPowerBoost = 0;
        }

//        OLD Launching
//        if (gamepad1.right_trigger > 0.05) {
//            launcherMotor.setPower(LAUNCHER_MIN + (SHOOTING_WHEEL_MULTIPLIER*gamepad1.right_trigger));
//        } else {
//            launcherMotor.setPower(0);
//        }

//        Camera Apriltag Detection
//        ObeliskPattern obeliskPattern = AprilTagModule.instance.getObeliskPattern();
//
//            if (obeliskPattern == ObeliskPattern.GPP) {
//                telemetry.addData("Pattern: ", "GPP");
//            } else if (obeliskPattern == ObeliskPattern.PGP) {
//                telemetry.addData("Pattern: ", "PGP");
//            } else if (obeliskPattern == ObeliskPattern.PPG) {
//                telemetry.addData("Pattern: ", "PPG");
//            } else if (obeliskPattern == ObeliskPattern.NONE_DETECTED) {
//                telemetry.addData("Pattern: ", "");
//            } else if (obeliskPattern == ObeliskPattern.MULTIPLE_DETECTED) {
//                telemetry.addData("Pattern: ", "multiple");
//        }

        telemetry.update();

//        Update Drivetrain Power
        updateDrivetrainPower();
    }
}
