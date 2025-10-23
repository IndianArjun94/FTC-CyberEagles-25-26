package org.firstinspires.ftc.teamcode.auton.action.intake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private DcMotor intakeMotor;

    public Intake(HardwareMap hardwareMap) {
        this.intakeMotor = hardwareMap.get(DcMotor.class, "intake");
    }

    public class StartActiveIntakeAction implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeMotor.setPower(1);
            return false;
        }
    }

    public class StopActiveIntakeAction implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeMotor.setPower(0);
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
