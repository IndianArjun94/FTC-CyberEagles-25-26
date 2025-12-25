package org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.loader;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class Lifter {
    private CRServo lifterServo;

    public Lifter(HardwareMap hardwareMap) {
        this.lifterServo = hardwareMap.get(CRServo.class, "frontLeftLoad");

    }
    public class Lift implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            lifterServo.setPower(1);
            return false;
        }
    }

    public class Return implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            lifterServo.setPower(-1);
            return false;
        }
    }
    public Action unlift(){return new Return();}
    public Action lift(){return new Lift();}
}
