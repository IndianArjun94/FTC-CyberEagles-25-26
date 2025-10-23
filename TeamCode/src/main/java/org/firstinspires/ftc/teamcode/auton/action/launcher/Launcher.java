package org.firstinspires.ftc.teamcode.auton.action.launcher;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.auton.action.loader.LauncherPower;

public class Launcher {
    private DcMotor launcherMotor;

    public Launcher(HardwareMap hardwareMap) {
        this.launcherMotor = hardwareMap.get(DcMotor.class, "launcher");
    }

    public class StartLauncherAction implements Action {

        private LauncherPower power;

        public StartLauncherAction(LauncherPower power) {
            this.power = power;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            switch (power) {
                case LOW:
                    launcherMotor.setPower(0.55);
                    break;
                case MEDIUM:
                    launcherMotor.setPower(0.6);
                    break;
                case HIGH:
                    launcherMotor.setPower(0.8);
            }
            return false;
        }
    }

    public class StopLauncherAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            launcherMotor.setPower(0);
            return false;
        }
    }

    public Action startLauncher(LauncherPower power) {
        return new StartLauncherAction(power);
    }

    public Action stopLauncher() {
        return new StopLauncherAction();
    }
}

