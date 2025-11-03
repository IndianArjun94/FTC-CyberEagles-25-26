package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.action.intake.Intake;

@Autonomous(name = "Auton Test")
public class AutonTest extends LinearOpMode {
//    UTILS -------------------------------------------------------
    public static final double DEGREES_OFFSET = 1.3/90;

    public static double calculateOffsetDegrees(double degrees) {
         return degrees+(degrees*DEGREES_OFFSET);
    }

    @Override
    public void runOpMode()  {
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Action moveForward = drive.actionBuilder(initialPose)
                .lineToY(20)
                .build();

        waitForStart();

        Actions.runBlocking(moveForward);

    }
}
