package org.firstinspires.ftc.teamcode.auton.action;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OldIntake {
    private CRServo leftIntakeServo;
    private CRServo rightIntakeServo;

    public OldIntake(HardwareMap hardwareMap) {
        this.leftIntakeServo = hardwareMap.get(CRServo.class, "intakeLeft");
        this.rightIntakeServo = hardwareMap.get(CRServo.class, "intakeRight");
        this.rightIntakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public class StartActiveIntakeAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            leftIntakeServo.setPower(1);
            rightIntakeServo.setPower(1);
            return false;
        }
    }

    public class StopActiveIntakeAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            leftIntakeServo.setPower(0);
            rightIntakeServo.setPower(0);
            return false;
        }
    }

    public Action startActiveIntake() {
        return new StartActiveIntakeAction();
    }

    public Action stopActiveIntake() {
        return new StopActiveIntakeAction();
    }
}
