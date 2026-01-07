package org.firstinspires.ftc.teamcode.teleop.modules;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LifterTeleOp {
    private Servo lifterServo;

    private final double UP_POSITION = 0.23; // straight up - used to be 0.15
    private final double DOWN_POSITION = 0.55; // straight right
    private Telemetry telemetry;

    public LifterTeleOp(HardwareMap hardwareMap, Telemetry telemetry) {
        this.lifterServo = hardwareMap.get(Servo.class, "lifter");
        this.telemetry = telemetry;
        telemetry.update();
    }
    public void lift() {
        lifterServo.setPosition(UP_POSITION);
    }
    public void reset() {
        lifterServo.setPosition(DOWN_POSITION);
    }
}
