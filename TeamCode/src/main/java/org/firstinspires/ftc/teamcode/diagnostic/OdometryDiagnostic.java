package org.firstinspires.ftc.teamcode.diagnostic;

import static org.firstinspires.ftc.teamcode.PinpointLocalizer.PARAMS;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;

import java.util.Objects;

@TeleOp(name = "Odometry Diagnostic", group = "Diagnostic")
public class OdometryDiagnostic extends OpMode {

    public GoBildaPinpointDriver driver;

    @Override
    public void init() {
        driver = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        double mmPerTick = 1 * 25.4;
        driver.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);

        driver.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        driver.resetPosAndIMU();
    }

    @Override
    public void loop() {
        driver.update();

        if (Objects.requireNonNull(driver.getDeviceStatus()) == GoBildaPinpointDriver.DeviceStatus.READY) {
            Pose2d pos = new Pose2d(driver.getPosX(DistanceUnit.INCH), driver.getPosY(DistanceUnit.INCH), driver.getHeading(UnnormalizedAngleUnit.DEGREES));
            telemetry.addData("x", pos.position.x);
            telemetry.addData("y", pos.position.y);
            telemetry.addData("heading", pos.heading.toDouble());
            telemetry.update();
        }

    }
}
