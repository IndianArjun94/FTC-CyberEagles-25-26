package org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.loader;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class  TripleBallQuadLoader {

    private CRServo frontLeftLoadServo;
    private CRServo frontRightLoadServo;
    private CRServo backLeftLoadServo;
    private CRServo backRightLoadServo;

    public TripleBallQuadLoader(HardwareMap hardwareMap) {
        this.frontLeftLoadServo = hardwareMap.get(CRServo.class, "frontLeftLoad");
        this.frontRightLoadServo = hardwareMap.get(CRServo.class, "frontRightLoad");
        this.backLeftLoadServo = hardwareMap.get(CRServo.class, "backLeftLoad");
        this.backRightLoadServo = hardwareMap.get(CRServo.class, "backRightLoad");

        this.frontRightLoadServo.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backRightLoadServo.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public class StartLoaderAction implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            frontLeftLoadServo.setPower(1);
            frontRightLoadServo.setPower(1);
            backLeftLoadServo.setPower(1);
            backRightLoadServo.setPower(1);
            return false;
        }
    }

    public class StopLoaderAction implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            frontLeftLoadServo.setPower(0);
            frontRightLoadServo.setPower(0);
            backLeftLoadServo.setPower(0);
            backRightLoadServo.setPower(0);
            return false;
        }
    }

    public class EjectLoader implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            frontLeftLoadServo.setPower(-1);
            frontRightLoadServo.setPower(-1);
            backLeftLoadServo.setPower(-1);
            backRightLoadServo.setPower(-1);
            return false;
        }
    }

    public Action start() {
        return new StartLoaderAction();
    }

    public Action stop() {
        return new StopLoaderAction();
    }

    public Action eject() {
        return new EjectLoader();
    }

}
