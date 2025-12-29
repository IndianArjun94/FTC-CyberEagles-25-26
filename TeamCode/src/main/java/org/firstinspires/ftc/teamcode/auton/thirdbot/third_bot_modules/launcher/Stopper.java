package org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Stopper {
    private Servo stopeprServo;

    private final double STOPPING_POS = 0.1; // straight up
    private final double OPEN_POSITION = 0.5; // straight right
    private Telemetry telemetry;

    public Stopper(HardwareMap hardwareMap, Telemetry telemetry) {
        this.stopeprServo = hardwareMap.get(Servo.class, "stopper");
        this.telemetry = telemetry;
        telemetry.update();
    }
    public class Stop implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            stopeprServo.setPosition(STOPPING_POS);
            return false;
        }
    }

    public class Return implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            stopeprServo.setPosition(OPEN_POSITION);
            return false;
        }
    }
    public Action open(){return new Return();}
    public Action initiate(){return new Stop();}
}
