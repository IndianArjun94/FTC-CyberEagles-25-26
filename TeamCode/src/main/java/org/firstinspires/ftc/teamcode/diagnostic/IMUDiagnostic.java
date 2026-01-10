package org.firstinspires.ftc.teamcode.diagnostic;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "IMU Diagnostic", group = "Diagnostic")
public class IMUDiagnostic extends OpMode {

    public static IMU imu;

    @Override
    public void init() {
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        );
        imu.initialize(new IMU.Parameters(orientation));
        imu.resetYaw();
    }

    @Override
    public void loop() {
        telemetry.addData("Yaw", Double.toString(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)));
        telemetry.update();
    }
}
