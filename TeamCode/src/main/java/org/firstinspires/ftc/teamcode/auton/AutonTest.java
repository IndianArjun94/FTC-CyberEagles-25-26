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

@Autonomous(name = "67 autonomous")
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
        Intake intake = new Intake(hardwareMap);

//        Action action = drive.actionBuilder(new Pose2d(0, 0, deg(90)))
//                .splineToLinearHeading(new Pose2d(20, 20, deg(180)), deg(0))
//                .build();

        Action moveForward = drive.actionBuilder(initialPose) // move forward 45 (move facing the y axis to y=45)
                .lineToY(45)
                .build();

        Action startMovingAndIntake = new ParallelAction(
                moveForward,
                intake.startActiveIntake()
        );

        Action fullAction = new SequentialAction(
                startMovingAndIntake,
                intake.stopActiveIntake()
        );

        waitForStart();

        Actions.runBlocking(fullAction);

    }
}
