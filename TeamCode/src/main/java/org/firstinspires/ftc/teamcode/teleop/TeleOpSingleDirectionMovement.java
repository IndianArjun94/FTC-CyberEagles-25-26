package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Disabled
public class TeleOpSingleDirectionMovement extends OpMode {

    private static DcMotor leftFrontMotor;
    private static DcMotor rightFrontMotor;
    private static DcMotor leftBackMotor;
    private static DcMotor rightBackMotor;
    private static IMU imu;

    private static double leftFrontPower;
    private static double leftBackPower;
    private static double rightFrontPower;
    private static double rightBackPower;

    @Override
    public void init() {
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFront");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFront");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBack");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBack");

        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD)));
        imu.resetYaw();

        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        double angle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

//        Default Drivetrain Code
        leftFrontPower = 0;
        rightFrontPower = 0;
        leftBackPower = 0;
        rightBackPower = 0;

        double gamepad1_x2 = gamepad1.right_stick_x;
        double gamepad1_y = -gamepad1.left_stick_y;
        double gamepad1_x = -gamepad1.left_stick_x;

//        rotation
//        leftBackPower += gamepad1_x2;
//        leftFrontPower += gamepad1_x2;
//        rightFrontPower -= gamepad1_x2;
//        rightBackPower -= gamepad1_x2;

//        movement forward/backward
        leftFrontPower += gamepad1_y;
        leftBackPower += gamepad1_y;
        rightFrontPower += gamepad1_y;
        rightBackPower += gamepad1_y;

//        strafe left/right
        leftFrontPower -= gamepad1_x;
        leftBackPower += gamepad1_x;
        rightFrontPower += gamepad1_x;
        rightBackPower -= gamepad1_x;

//        Angle Adjustment

    }
}
