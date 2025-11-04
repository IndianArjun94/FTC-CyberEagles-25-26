package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.auton.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.action.intake.Intake;

@Autonomous(name = "Auton Test")
public class AutonTest extends LinearOpMode {
//    UTILS -------------------------------------------------------

    @Override
    public void runOpMode()  {
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Action finalAction = drive.actionBuilder(initialPose)
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(40, 40, deg(180)), deg(90))
                .build();

        waitForStart();

        Actions.runBlocking(finalAction);

    }
}
