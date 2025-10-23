package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.module.AprilTagModule;
import org.firstinspires.ftc.teamcode.module.ObeliskPattern;

@TeleOp(name = "TeleOpBeta")
public class TeleOpALPHAFirstLauncherBot extends OpMode {

    private static DcMotor leftFrontMotor;
    private static DcMotor rightFrontMotor;
    private static DcMotor leftBackMotor;
    private static DcMotor rightBackMotor;

    private static double leftFrontPower;
    private static double leftBackPower;
    private static double rightFrontPower;
    private static double rightBackPower;

    private static final double SPEED_CAP = 0.5f;
    private static final double LAUNCHER_MIN = 0.5f;
    private static double SHOOTING_WHEEL_MULTIPLIER = 0.05f;

    private static boolean loading = false;
    private static boolean intakeActive = false;
    private static CRServo leftLoadServo;
    private static CRServo rightLoadServo;
    private static DcMotor launcherMotor;
    private static DcMotor intakeMotor;
    private static double previousIntakeUpdateTime = System.currentTimeMillis();

    private static String MODE = "One";

    @Override
    public void init() {
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFront");
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFront");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBack");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBack");
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");

        leftLoadServo = hardwareMap.get(CRServo.class, "leftLoad");
        rightLoadServo = hardwareMap.get(CRServo.class, "rightLoad");

        launcherMotor = hardwareMap.get(DcMotor.class, "launcher");
        leftLoadServo.setDirection(DcMotorSimple.Direction.REVERSE);

        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        launcherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        AprilTagModule.init(hardwareMap, true);

    }


    //  Update Function that runs after every loop  //
    public static void updateDrivetrainPower() {
        leftFrontMotor.setPower(leftFrontPower * SPEED_CAP);
        leftBackMotor.setPower(leftBackPower * SPEED_CAP);
        rightBackMotor.setPower(rightBackPower * SPEED_CAP);
        rightFrontMotor.setPower(rightFrontPower * SPEED_CAP);
    }

    @Override
    public void loop() {

        leftFrontPower = 0;
        rightFrontPower = 0;
        leftBackPower = 0;
        rightBackPower = 0;

        double gamepad1_x2 = gamepad1.right_stick_x;
        double gamepad1_y = -gamepad1.left_stick_y;
        double gamepad1_x = -gamepad1.left_stick_x;

        //Rotation
        leftBackPower += gamepad1_x2;
        leftFrontPower += gamepad1_x2;
        rightFrontPower -= gamepad1_x2;
        rightBackPower -= gamepad1_x2;

        //Forward/Backward
        leftFrontPower += gamepad1_y;
        leftBackPower += gamepad1_y;
        rightFrontPower += gamepad1_y;
        rightBackPower += gamepad1_y;

        //Lateral
        leftFrontPower -= gamepad1_x;
        leftBackPower += gamepad1_x;
        rightFrontPower -= gamepad1_x;
        rightBackPower += gamepad1_x;


//        Loading
        if (gamepad1.a) {
            loading = true;
        } else if (gamepad1.b) {
            loading = false;
        }

        if (loading) {
            rightLoadServo.setPower(1);
            leftLoadServo.setPower(1);
        } else {
            rightLoadServo.setPower(0);
            leftLoadServo.setPower(0);
        }

//        Intake
        if (gamepad1.left_bumper && System.currentTimeMillis()-previousIntakeUpdateTime > 250) {
            if (intakeActive) {
                intakeActive = false;
                previousIntakeUpdateTime = System.currentTimeMillis();
            } else {
                intakeActive = true;
                previousIntakeUpdateTime = System.currentTimeMillis();
            }
        }

        if (intakeActive) {
            intakeMotor.setPower(1);
        } else {
            intakeMotor.setPower(0);
        }

        // Modes to control power
        if (gamepad1.dpadRightWasPressed()) {
            SHOOTING_WHEEL_MULTIPLIER = 0.3;
            MODE = "Three";
        }
        if (gamepad1.dpadDownWasPressed()) {
            SHOOTING_WHEEL_MULTIPLIER = 0.1;
            MODE = "Two";
        }
        if (gamepad1.dpadLeftWasPressed()) {
            SHOOTING_WHEEL_MULTIPLIER = 0.05;
            MODE = "One";
        }
        telemetry.addData("Launcher Mode: ", MODE);
        telemetry.addData("Launcher Speed Target: ", LAUNCHER_MIN + (SHOOTING_WHEEL_MULTIPLIER*gamepad1.right_trigger));


//        Launching
        if (gamepad1.right_trigger > 0.05) {
            launcherMotor.setPower(LAUNCHER_MIN + (SHOOTING_WHEEL_MULTIPLIER*gamepad1.right_trigger));
        } else {
            launcherMotor.setPower(0);
        }

        ObeliskPattern obeliskPattern = AprilTagModule.instance.getObeliskPattern();

        if (obeliskPattern == ObeliskPattern.GPP) {
            telemetry.addData("Pattern: ", "GPP");
        } else if (obeliskPattern == ObeliskPattern.PGP) {
            telemetry.addData("Pattern: ", "PGP");
        } else if (obeliskPattern == ObeliskPattern.PPG) {
            telemetry.addData("Pattern: ", "PPG");
        } else if (obeliskPattern == ObeliskPattern.NONE_DETECTED) {
            telemetry.addData("Pattern: ", "");
        } else if (obeliskPattern == ObeliskPattern.MULTIPLE_DETECTED) {
            telemetry.addData("Pattern: ", "multiple");
        }

        telemetry.update();

//        Update Drivetrain Power
        updateDrivetrainPower();
    }
}
