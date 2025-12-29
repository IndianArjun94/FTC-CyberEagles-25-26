package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.teleop.modules.LifterTeleOp;
import org.firstinspires.ftc.teamcode.teleop.modules.PIDFlyWheelTeleOp;
import org.firstinspires.ftc.teamcode.teleop.modules.StopperTeleOp;
import org.firstinspires.ftc.teamcode.teleop.modules.TripleBallQuadLoaderTeleOp;

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
    private static boolean isLifterUp;
    private static final double OPEN_POSITION = 0.6;    //right
    private static final double CLOSED_POSITION = 1.0;    //forward
    private static final double UP_POSITION = 0.27;    //up
    private static final double DOWN_POSITION = 0.55;   //down
    private static final double SPEED_CAP = 0.8f;
    private static final double LAUNCHER_MIN = 0.5f;
    private static double SHOOTING_WHEEL_MULTIPLIER = 0.18f;
    private static double launcherPowerBoost = 0.0;
    private static boolean intakeActive = false;
//    private static CRServo frontLeftLoad;
//    private static CRServo backLeftLoad;
//    private static CRServo frontRightLoad;
//    private static CRServo backRightLoad;
    private static DcMotor intake;
    private static double lastLeftBumperPress = System.currentTimeMillis();
    private static long previousRightBumperPress;
    private static boolean launching = false;
    private static long launcherSequenceStartTime;
    private static boolean launcherSequenceLaunched = false;
    private static long intakeStopTime;
    private static PIDFlyWheelTeleOp flyWheel;
    private static LifterTeleOp lifter;
    private static StopperTeleOp stopper;
    private static TripleBallQuadLoaderTeleOp loader;
    private static long stopperOpenedTime;
    private static long previousLaunchingStageTime;
    private static int launchingStage = 1;

    @Override
    public void init() {
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFront");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFront");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBack");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBack");
        intake = hardwareMap.get(DcMotor.class, "intake");

//        lifter = hardwareMap.get(Servo.class, "lifter");
//        frontLeftLoad = hardwareMap.get(CRServo.class, "frontLeftLoad");
//        backLeftLoad = hardwareMap.get(CRServo.class, "backLeftLoad");
//        frontRightLoad = hardwareMap.get(CRServo.class, "frontRightLoad");
//        backRightLoad = hardwareMap.get(CRServo.class, "backRightLoad");

//        launcherMotor = hardwareMap.get(DcMotorEx.class, "launcher");
//        frontRightLoad.setDirection(DcMotorSimple.Direction.REVERSE);
//        backRightLoad.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        lifter = new LifterTeleOp(hardwareMap, telemetry);
        flyWheel = new PIDFlyWheelTeleOp(hardwareMap, telemetry);
        stopper = new StopperTeleOp(hardwareMap, telemetry);
        loader = new TripleBallQuadLoaderTeleOp(hardwareMap);

    }

//    public static void doLiftingSequence() {
//        lifter.setPosition(UP_POSITION);
//        isLifterUp = true;
//        lifter.setPosition(DOWN_POSITION);
//        isLifterUp = false;
//        lifterSequenceTime = System.currentTimeMillis();
//        shotCount += 1;
//        for (int i = 0;i > 2;i++) {
//            if (System.currentTimeMillis() - lifterSequenceTime >= 750 || System.currentTimeMillis() - lifterSequenceTimeTwo >= 750)
//            lifter.setPosition(UP_POSITION);
//            isLifterUp = true;
//            lifter.setPosition(DOWN_POSITION);
//            isLifterUp = false;
//            shotCount += 1;
//            lifterSequenceTimeTwo = System.currentTimeMillis();
//        }
//    }

    //  Update Function that runs after every loop  //
    public static void updateDrivetrainPower() {
        leftFrontMotor.setPower(leftFrontPower * SPEED_CAP);
        leftBackMotor.setPower(leftBackPower * SPEED_CAP);
        rightBackMotor.setPower(rightBackPower * SPEED_CAP);
        rightFrontMotor.setPower(rightFrontPower * SPEED_CAP);
    }

    @Override
    public void loop() {

//        Drivetrain
        leftFrontPower = 0;
        rightFrontPower = 0;
        leftBackPower = 0;
        rightBackPower = 0;

        double gamepad1_x2 = gamepad1.right_stick_x;
        double gamepad1_y = -gamepad1.left_stick_y;
        double gamepad1_x = -gamepad1.left_stick_x;

        leftBackPower += gamepad1_x2;
        leftFrontPower += gamepad1_x2;
        rightFrontPower -= gamepad1_x2;
        rightBackPower -= gamepad1_x2;

        leftFrontPower += gamepad1_y;
        leftBackPower += gamepad1_y;
        rightFrontPower += gamepad1_y;
        rightBackPower += gamepad1_y;

        leftFrontPower -= gamepad1_x;
        leftBackPower += gamepad1_x;
        rightFrontPower += gamepad1_x;
        rightBackPower -= gamepad1_x;

//        Intake
        if (gamepad2.left_bumper && System.currentTimeMillis() - lastLeftBumperPress > 250) {
            if (intakeActive) {
                intakeActive = false;
                lastLeftBumperPress = System.currentTimeMillis();
            } else {
                intakeActive = true;
                lastLeftBumperPress = System.currentTimeMillis();
            }
        }

        if (intakeActive && !launching) {
            intake.setPower(1);
            loader.start();
        } else {
            intake.setPower(0);
        }

//        Loading + Launching Sequence
        if (gamepad2.right_bumper && System.currentTimeMillis() - previousRightBumperPress > 250) {
            if (!launching) {
                launching = true;
            } else {
                launching = false;
            }
            previousRightBumperPress = System.currentTimeMillis();
        }

        if (launching) {
            if (launchingStage == 1) {
                stopper.open();
                previousLaunchingStageTime = System.currentTimeMillis();
                launchingStage++;
            } else if (launchingStage == 2 && System.currentTimeMillis()-previousLaunchingStageTime >= 200) {
                lifter.lift();
                previousLaunchingStageTime = System.currentTimeMillis();
                launchingStage++;
            } else if (launchingStage == 3 && System.currentTimeMillis()-previousLaunchingStageTime >= 500) {
                lifter.reset();
                previousLaunchingStageTime = System.currentTimeMillis();
                launchingStage++;
            } else if (launchingStage == 4 && System.currentTimeMillis()-previousLaunchingStageTime >= 200) {
                loader.start();
                previousLaunchingStageTime = System.currentTimeMillis();
                launchingStage++;
            } else if (launchingStage == 5 && System.currentTimeMillis()-previousLaunchingStageTime >= 1000) {

            }
            stopperOpenedTime = System.currentTimeMillis();
            if (System.currentTimeMillis() - stopperOpenedTime)
            lifter.lift();
        }

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

//        Update Drivetrain Power
        updateDrivetrainPower();
        flyWheel.run();
        telemetry.update();

    }
}
