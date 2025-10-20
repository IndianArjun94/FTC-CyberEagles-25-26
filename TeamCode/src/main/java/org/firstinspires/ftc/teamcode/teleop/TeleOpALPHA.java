package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.module.AprilTagModule;
import org.firstinspires.ftc.teamcode.module.ObeliskPattern;

@TeleOp(name = "TeleOpALPHA")
public class TeleOpALPHA extends OpMode {

    private static DcMotor leftFrontMotor;
    private static DcMotor rightFrontMotor;
    private static DcMotor leftBackMotor;
    private static DcMotor rightBackMotor;

    private static double frontLeftPower;
    private static double backLeftPower;
    private static double frontRightPower;
    private static double backRightPower;

    private static final double DRIVETRAIN_MULTIPLIER = 0.5f;
    private static final double LAUNCHER_MIN = 0.5f;
    private static double SHOOTING_WHEEL_MULTIPLIER = 0.05f;

    private static boolean loading = false;
    private static CRServo leftLoadServo;
    private static CRServo rightLoadServo;
    private static DcMotor launcherMotor;

    private static String MODE = "One";

    @Override
    public void init() {
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFront");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFront");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBack");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBack");

        leftLoadServo = hardwareMap.get(CRServo.class, "leftLoad");
        rightLoadServo = hardwareMap.get(CRServo.class, "rightLoad");

        launcherMotor = hardwareMap.get(DcMotor.class, "launcher");

        leftLoadServo.setDirection(DcMotorSimple.Direction.REVERSE);

//        launcherMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        launcherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        AprilTagModule.init(hardwareMap, true);

    }


    //  Update Function that runs after every loop  //
    public static void updateDrivetrainPower() {
        leftFrontMotor.setPower(frontLeftPower * DRIVETRAIN_MULTIPLIER);
        leftBackMotor.setPower(backLeftPower * DRIVETRAIN_MULTIPLIER);
        rightBackMotor.setPower(backRightPower * DRIVETRAIN_MULTIPLIER);
        rightFrontMotor.setPower(frontRightPower * DRIVETRAIN_MULTIPLIER);
    }

    @Override
    public void loop() {

        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        frontLeftPower = (y + x + rx) / denominator;
        backLeftPower = (y - x + rx) / denominator;
        frontRightPower = (y - x - rx) / denominator;
        backRightPower = (y + x - rx) / denominator;




//        Loading
        if (gamepad1.a) {
            loading = true;
        } else if (gamepad1.b) {
            loading = false;
        }

        if (loading) {
            rightLoadServo.setPower(1);
            leftLoadServo.setPower(1);
        } else {
            rightLoadServo.setPower(0);
            leftLoadServo.setPower(0);
        }

        // Modes to control power
//        if (gamepad1.dpadUpWasPressed()) {
//            SHOOTING_WHEEL_MULTIPLIER = 0.3;
//            MODE = "Four";
//        }
        if (gamepad1.dpadRightWasPressed()) {
            SHOOTING_WHEEL_MULTIPLIER = 0.3;
            MODE = "Three";
        }
        if (gamepad1.dpadDownWasPressed()) {
            SHOOTING_WHEEL_MULTIPLIER = 0.1;
            MODE = "Two";
        }
        if (gamepad1.dpadLeftWasPressed()) {
            SHOOTING_WHEEL_MULTIPLIER = 0.05;
            MODE = "One";
        }
        telemetry.addData("Launcher Mode: ", MODE);
        telemetry.addData("Launcher Speed Target: ", LAUNCHER_MIN + (SHOOTING_WHEEL_MULTIPLIER*gamepad1.right_trigger));


//        Launching
        if (gamepad1.right_trigger > 0.05) {
            launcherMotor.setPower(LAUNCHER_MIN + (SHOOTING_WHEEL_MULTIPLIER*gamepad1.right_trigger));
        } else {
            launcherMotor.setPower(0);
        }

        ObeliskPattern obeliskPattern = AprilTagModule.instance.getObeliskPattern();

        if (obeliskPattern == ObeliskPattern.GPP) {
            telemetry.addData("Pattern: ", "GPP");
        } else if (obeliskPattern == ObeliskPattern.PGP) {
            telemetry.addData("Pattern: ", "PGP");
        } else if (obeliskPattern == ObeliskPattern.PPG) {
            telemetry.addData("Pattern: ", "PPG");
        } else if (obeliskPattern == ObeliskPattern.NONE_DETECTED) {
            telemetry.addData("Pattern: ", "");
        } else if (obeliskPattern == ObeliskPattern.MULTIPLE_DETECTED) {
            telemetry.addData("Pattern: ", "multiple");
        }

        telemetry.update();

//        Update Drivetrain Power
        updateDrivetrainPower();
    }
}
