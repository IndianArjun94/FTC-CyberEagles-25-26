package org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Lifter {
    private Servo lifterServo;

    private final double UP_POSITION = 0.25; // straight up - used to be 0.15 old: 0.25
    private final double DOWN_POSITION = 0.65; // straight right old: 0.55
    private Telemetry telemetry;

    public Lifter(HardwareMap hardwareMap, Telemetry telemetry) {
        this.lifterServo = hardwareMap.get(Servo.class, "lifter");
        this.telemetry = telemetry;
        telemetry.update();
    }
    public class Lift implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            lifterServo.setPosition(UP_POSITION);
            return false;
        }
    }

    public class Return implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            lifterServo.setPosition(DOWN_POSITION);
            return false;
        }
    }
    public Action reset(){return new Return();}
    public Action lift(){return new Lift();}
}
