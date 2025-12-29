package org.firstinspires.ftc.teamcode.teleop.modules;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class StopperTeleOp{
    private Servo stopperServo;

    private final double CLOSED_POSITION = 0.2; // straight up
    private final double OPEN_POSITION = 0.6; // straight right
    private Telemetry telemetry;

    public StopperTeleOp(HardwareMap hardwareMap, Telemetry telemetry) {
        this.stopperServo = hardwareMap.get(Servo.class, "stopper");
        this.telemetry = telemetry;
        telemetry.update();
    }
    public void open() {
        stopperServo.setPosition(OPEN_POSITION);
    }
    public void close() {
        stopperServo.setPosition(CLOSED_POSITION);
    }

}
