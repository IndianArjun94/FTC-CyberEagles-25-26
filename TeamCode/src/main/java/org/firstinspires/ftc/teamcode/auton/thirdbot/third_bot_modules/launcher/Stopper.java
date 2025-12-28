package org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Stopper {
    private Servo stopeprServo;

    private final double UP_POSITION = 0.27; // straight up
    private final double DOWN_POSITION = 0.5; // straight right
    private Telemetry telemetry;

    public Stopper(HardwareMap hardwareMap, Telemetry telemetry) {
        this.stopeprServo = hardwareMap.get(Servo.class, "stopper");
        this.telemetry = telemetry;
        telemetry.update();
    }
    public class Stop implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            stopeprServo.setPosition(UP_POSITION);
            return false;
        }
    }

    public class Return implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            stopeprServo.setPosition(DOWN_POSITION);
            return false;
        }
    }
    public Action reset(){return new Return();}
    public Action stop(){return new Stop();}
}
