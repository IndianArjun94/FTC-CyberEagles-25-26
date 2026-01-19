package org.firstinspires.ftc.teamcode.teleop.modules;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PIDFlyWheelTeleOp {
    public final int TARGET_RPM = 170;
    public int rpmOffset = 0;
    public final int MAX_RPM = 312;
    public final double TICKS_PER_REVOLUTION = 537.6; // 28?
    public double MAX_TPS = ((TARGET_RPM+rpmOffset)*TICKS_PER_REVOLUTION) / 60;
    public final double P = 0.95; // TODO: Tune these values
    public final double I = 0.006; // TODO: Tune these values
    public final double D = 0.4;
    private DcMotorEx launcherMotor;
    private double prevError;
    private double error;
    private double proportional;
    private double integral;
    private double derivative;
    private double prevTime;

    private Telemetry telemetry;

    public PIDFlyWheelTeleOp(HardwareMap hardwareMap, Telemetry telemetry) {
        this.launcherMotor = hardwareMap.get(DcMotorEx.class, "launcher");
        this.telemetry = telemetry;
    }

    public void run() {
        double time = System.nanoTime() / 1_000_000d; // millis

        error = (TARGET_RPM+rpmOffset) * TICKS_PER_REVOLUTION / 60 - launcherMotor.getVelocity(); // error = target - current
        proportional = error;
        integral += error;
        derivative = (error - prevError) / (time - prevTime); // difference in error (always - ) / downtime
        prevError = error;
        prevTime = time;

        double output = (P * proportional) + (I * integral) + (D * derivative);
        launcherMotor.setPower(Math.min(output / MAX_TPS, 1));

        telemetry.addData("output (power %): ", output / MAX_TPS);
        telemetry.addData("err (rpm): ", error * 60 / TICKS_PER_REVOLUTION);
        telemetry.addData("P: ", proportional * P);
        telemetry.addData("I: ", integral * I);
        telemetry.addData("D: ", derivative * D);
        telemetry.addData("dt: ", time - prevTime);
        telemetry.addData("vel (rpm): ", launcherMotor.getVelocity() * 60 / TICKS_PER_REVOLUTION);

        telemetry.update();
    }

    public void increaseRPM() {
        rpmOffset += 5;
        MAX_TPS = ((TARGET_RPM+rpmOffset)*TICKS_PER_REVOLUTION) / 60;
    }

    public void decreaseRPM() {
        rpmOffset -= 5;
        MAX_TPS = ((TARGET_RPM+rpmOffset)*TICKS_PER_REVOLUTION) / 60;
    }

    public void resetRPM() {
        rpmOffset = 0;
        MAX_TPS = ((TARGET_RPM+rpmOffset)*TICKS_PER_REVOLUTION) / 60;
    }
}