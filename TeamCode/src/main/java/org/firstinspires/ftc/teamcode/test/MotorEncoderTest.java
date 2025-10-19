package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Motor Encoder Test")
public class MotorEncoderTest extends OpMode {
    private static DcMotor leftFrontMotor;
    private static DcMotor rightFrontMotor;
    private static DcMotor leftBackMotor;
    private static DcMotor rightBackMotor;

    @Override
    public void init() {
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFront");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFront");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBack");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBack");

        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        double startTime = System.nanoTime();

        double startPosLeftBack = leftBackMotor.getCurrentPosition();
        double startPosRightBack = rightBackMotor.getCurrentPosition();
        double startPosLeftFront = leftFrontMotor.getCurrentPosition();
        double startPosRightFront = rightFrontMotor.getCurrentPosition();

//        if (gamepad1.x) {
            leftBackMotor.setPower(gamepad1.left_trigger/3);
//        } if (gamepad1.b) {
            rightBackMotor.setPower(gamepad1.left_trigger/3);
//        } if (gamepad1.y) {
            leftFrontMotor.setPower(gamepad1.left_trigger/3);
//        } if (gamepad1.a) {
            rightFrontMotor.setPower(gamepad1.left_trigger/3);
//        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        double endTime = System.nanoTime();

        double endPosLeftBack = leftBackMotor.getCurrentPosition();
        double endPosRightBack = rightBackMotor.getCurrentPosition();
        double endPosLeftFront = leftFrontMotor.getCurrentPosition();
        double endPosRightFront = rightFrontMotor.getCurrentPosition();

//        --------

        double timeDiff = endTime-startTime;

        double diffPosLeftBack = endPosLeftBack-startPosLeftBack;
        double diffPosRightBack = endPosRightBack-startPosRightBack;
        double diffPosLeftFront = endPosLeftFront-startPosLeftFront;
        double diffPosRightFront = endPosRightFront-startPosRightFront;

        telemetry.addData("Front Left: ", Double.toString(diffPosLeftFront/timeDiff));
        telemetry.addData("Front Right: ", Double.toString(diffPosRightFront/timeDiff));
        telemetry.addData("Back Left: ", Double.toString(diffPosLeftBack/timeDiff));
        telemetry.addData("Back Right: ", Double.toString(diffPosRightBack/timeDiff));
    }
}
