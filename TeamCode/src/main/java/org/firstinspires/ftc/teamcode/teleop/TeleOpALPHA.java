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

    private static double leftFrontSpeed;
    private static double leftBackSpeed;
    private static double rightFrontSpeed;
    private static double rightBackSpeed;

    private static final double DRIVETRAIN_MULTIPLIER = 0.5f;

    private static boolean loading = false;
    private static CRServo leftLoadServo;
    private static CRServo rightLoadServo;
    private static DcMotor launcherMotor;

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
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        AprilTagModule.init(hardwareMap, true);

    }


    //  Update Function that runs after every loop  //
    public static void updateDrivetrainPower() {
        leftFrontMotor.setPower(leftFrontSpeed * DRIVETRAIN_MULTIPLIER);
        leftBackMotor.setPower(leftBackSpeed * DRIVETRAIN_MULTIPLIER);
        rightBackMotor.setPower(rightBackSpeed * DRIVETRAIN_MULTIPLIER);
        rightFrontMotor.setPower(rightFrontSpeed * DRIVETRAIN_MULTIPLIER);
    }

    @Override
    public void loop() {

        double gamepad1_y = gamepad1.left_stick_y;
        double gamepad1_x = gamepad1.left_stick_x;
        double gamepad1_x2 = gamepad1.right_stick_x;

        leftFrontSpeed = 0;
        rightFrontSpeed = 0;
        leftBackSpeed = 0;
        rightBackSpeed = 0;

//       Rotate
        leftBackSpeed -= gamepad1_x2;
        leftFrontSpeed -= gamepad1_x2;
        rightFrontSpeed += gamepad1_x2;
        rightBackSpeed += gamepad1_x2;

//       Forward/Backward
        leftFrontSpeed += gamepad1_y;
        leftBackSpeed += gamepad1_y;
        rightFrontSpeed += gamepad1_y;
        rightBackSpeed += gamepad1_y;

//       Lateral
        leftFrontSpeed -= gamepad1_x;
        leftBackSpeed += gamepad1_x;
        rightFrontSpeed += gamepad1_x;
        rightBackSpeed -= gamepad1_x;

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

//        Launching
        if (gamepad1.right_trigger > 0.05) {
            launcherMotor.setPower(gamepad1.right_trigger);
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
