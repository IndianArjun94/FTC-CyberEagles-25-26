package org.firstinspires.ftc.teamcode.auton.action.module.loader;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SingleBallLoader {

    private CRServo leftLoadServo;
    private CRServo rightLoadServo;

    public SingleBallLoader(HardwareMap hardwareMap) {
        this.leftLoadServo = hardwareMap.get(CRServo.class, "leftLoad");
        this.rightLoadServo = hardwareMap.get(CRServo.class, "rightLoad");

        this.leftLoadServo.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public class StartSingleBallLoaderAction implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            leftLoadServo.setPower(1);
            rightLoadServo.setPower(1);
            return false;
        }
    }

    public class StopSingleBallLoaderAction implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            leftLoadServo.setPower(0);
            rightLoadServo.setPower(0);
            return false;
        }
    }

    public Action startSingleBallLoader() {
        return new StartSingleBallLoaderAction();
    }

    public Action stopSingleBallLoader() {
        return new StopSingleBallLoaderAction();
    }

}
