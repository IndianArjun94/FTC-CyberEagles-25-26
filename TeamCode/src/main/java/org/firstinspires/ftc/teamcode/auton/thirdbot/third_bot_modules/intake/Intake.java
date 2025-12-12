package org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.intake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;

public class Intake {
    private DcMotorEx intakeMotor;
    private DcMotor intakeMotor2;
    private MecanumDrive mecanumDrive;
    private Telemetry telemetry;

    public Intake(HardwareMap hardwareMap, MecanumDrive mecanumDrive, Telemetry telemetry) {
        this.intakeMotor = hardwareMap.get(DcMotorEx.class, "intake1");
        this.intakeMotor2 = hardwareMap.get(DcMotor.class, "intake2");
        this.intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.intakeMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.intakeMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
        this.mecanumDrive = mecanumDrive;
        this.telemetry = telemetry;
    }

    public class StartActiveIntakeAction implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeMotor.setPower(0.8);
            intakeMotor2.setPower(0.8);
            return false;
        }
    }

    public class StopActiveIntakeAction implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeMotor.setPower(0);
            intakeMotor2.setPower(0);
            return false;
        }
    }

    public class StopAllWhenBallDetected implements Action {

        private final ElapsedTime timer;
        private boolean initialized = false;

        // Assuming you pass the hardware in the constructor
        public StopAllWhenBallDetected() {
            this.timer = new ElapsedTime();
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            if (!initialized) {
                timer.reset();
                initialized = true;
            }
            double amps = intakeMotor.getCurrent(CurrentUnit.AMPS);
            telemetry.addData("Intake Current", amps);
            telemetry.update();


            if (timer.milliseconds() > 500 && amps > 9.5f) {
                mecanumDrive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
                intakeMotor.setPower(-0.5);
                return false; // Action finished
            }

            return true;
        }
    }

    public Action startActiveIntake() {
        return new StartActiveIntakeAction();
    }

    public Action stopActiveIntake() {
        return new StopActiveIntakeAction();
    }

    public Action stopAllWhenBallDetected() {
        return new StopAllWhenBallDetected();
    }
}
