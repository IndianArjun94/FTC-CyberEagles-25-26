package org.firstinspires.ftc.teamcode.teleop.modules;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TripleBallQuadLoaderTeleOp {
    private final CRServo frontLeftLoadServo;
    private final CRServo frontRightLoadServo;
    private final CRServo backLeftLoadServo;
    private final CRServo backRightLoadServo;
    private final DcMotorEx topLoadMotor;

    public TripleBallQuadLoaderTeleOp(HardwareMap hardwareMap) {
        this.frontLeftLoadServo = hardwareMap.get(CRServo.class, "frontLeftLoad");
        this.frontRightLoadServo = hardwareMap.get(CRServo.class, "frontRightLoad");
        this.backLeftLoadServo = hardwareMap.get(CRServo.class, "backLeftLoad");
        this.backRightLoadServo = hardwareMap.get(CRServo.class, "backRightLoad");
        this.topLoadMotor = hardwareMap.get(DcMotorEx.class, "topLoad");

        this.frontRightLoadServo.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backRightLoadServo.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void start() {
        frontLeftLoadServo.setPower(1);
        frontRightLoadServo.setPower(1);
        backLeftLoadServo.setPower(1);
        backRightLoadServo.setPower(1);
        topLoadMotor.setPower(1);
    }

    public void stop() {
        frontLeftLoadServo.setPower(0);
        frontRightLoadServo.setPower(0);
        backLeftLoadServo.setPower(0);
        backRightLoadServo.setPower(0);
        topLoadMotor.setPower(0);
    }

    public void eject() {
        frontLeftLoadServo.setPower(-1);
        frontRightLoadServo.setPower(-1);
        backLeftLoadServo.setPower(-1);
        backRightLoadServo.setPower(-1);
        topLoadMotor.setPower(-1);
    }
}
