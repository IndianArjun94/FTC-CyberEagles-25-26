package org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDFlyWheel {
    private DcMotorEx launcherMotor;
    private Telemetry telemetry;

    public boolean launcherRunning = false;

    public PIDFlyWheel(HardwareMap hw, Telemetry telemetry) {
        this.launcherMotor = hw.get(DcMotorEx.class, "launcher");
        this.telemetry = telemetry;
    }

    public class RevLauncherAction implements Action {
        public int targetRPM;
        public final int MAX_RPM = 312;
        public final double TICKS_PER_ROTATION = 537.6; // 28?
        public final double MAX_TPS = (targetRPM*TICKS_PER_ROTATION)/60;
        public final double P = 1.6; // TODO: Tune these values
        public final double I = 0.006; // TODO: Tune these values
        public final double D = 0.4;

        private double prevError;
        private double error;
        private double proportional;
        private double integral;
        private double derivative;
        private double prevTime;
        private long startTime;

        public RevLauncherAction(int targetRPM) {
            this.targetRPM = targetRPM;
            launcherRunning = true;
        }
        public RevLauncherAction() {launcherRunning = true;}

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (startTime == 0) startTime = System.currentTimeMillis();

            double time = System.nanoTime()/1_000_000d; // millis

            error = targetRPM * TICKS_PER_ROTATION /60 - launcherMotor.getVelocity(); // error = target - current
            proportional = error;
            integral += error;
            derivative = (error - prevError)/(time-prevTime); // difference in error (always - ) / downtime
            prevError = error;
            prevTime = time;

            double output = (P*proportional) + (I*integral) + (D*derivative);

            launcherMotor.setPower(Math.min(output/MAX_TPS, 1));

            telemetry.addData("elapsed: ", Long.toString((System.currentTimeMillis()-startTime)/1000));
            telemetry.addData("output (power %): ", output/MAX_TPS);
            telemetry.addData("err (rpm): ", error*60/ TICKS_PER_ROTATION);
            telemetry.addData("P: ", proportional*P);
            telemetry.addData("I: ", integral*I);
            telemetry.addData("D: ", derivative*D);
            telemetry.addData("dt: ", time-prevTime);
            telemetry.addData("vel (rpm): ", launcherMotor.getVelocity()*60/ TICKS_PER_ROTATION);


            telemetry.update();

            if (launcherRunning)
                return true;
            else {
                launcherMotor.setPower(0);
                return false;
            }
        }
    }

    public class StopLauncherAction implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            launcherRunning = false;
            launcherMotor.setPower(0);
            return false;
        }
    }

    public class FindRPMAction implements Action {
        public final double TICKS_PER_REVOLUTION = 537.6;

        public double prevTime;
        public double prevPos;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            launcherMotor.setPower(1);
            if (prevPos == 0) {
                prevPos = launcherMotor.getCurrentPosition()/TICKS_PER_REVOLUTION;
                prevTime = System.nanoTime()/1_000_000_000d;
                return true;
            } else {
                double currPos = launcherMotor.getCurrentPosition()/TICKS_PER_REVOLUTION;
                double currTime = System.nanoTime()/1_000_000_000d;

                double rpm = (currPos - prevPos) / ((currTime - prevTime)/60d);

                telemetry.addData("RPM", rpm);
                telemetry.addData("posDif", currPos-prevPos);
                telemetry.addData("timeDif", currTime-prevTime);

                prevPos = currPos;
                prevTime = currTime;
                telemetry.update();

                return true;
            }
        }
    }


    public RevLauncherAction revLauncher(int targetRPM) {
        return new RevLauncherAction(targetRPM);
    }

    public Action stopLauncher() {
        return new StopLauncherAction();
    }

    public Action getRPM() {
        return new FindRPMAction();
    }
}
