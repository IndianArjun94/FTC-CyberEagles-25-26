package org.firstinspires.ftc.teamcode.auton.firstbot;

import static org.firstinspires.ftc.teamcode.auton.firstbot.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.firstbot.module.launcher.Launcher;
import org.firstinspires.ftc.teamcode.auton.firstbot.module.loader.SingleBallLoader;

@Autonomous(name = "CLOSE RED Single Ball")
public class CLOSEREDScoreSingleBall extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startingPos = new Pose2d(-52.5,46.5,deg(125));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPos);
        Launcher launcher = new Launcher(hardwareMap);
        SingleBallLoader loader = new SingleBallLoader(hardwareMap);

        Action goToGoal = drive.actionBuilder(startingPos)
                .setTangent(deg(235))
                .splineToConstantHeading(
                        new Vector2d(-22.5,14), deg(235))
                .build();
// TODO update goal angle
        Action goAwayFromGoal = drive.actionBuilder(new Pose2d(-22.5,14,deg(125)))
                .setTangent(deg(90))
                .splineToLinearHeading(new Pose2d(-24, 53, deg(90)), deg(80))
                .build();

        Action fullAction = new SequentialAction(
                launcher.startLauncher(0.63),
                goToGoal,
                new SleepAction(0.25),
                loader.startSingleBallLoader(),
                new SleepAction(1.5),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher(),
                goAwayFromGoal
        );

        waitForStart();

        Actions.runBlocking(fullAction);

    }
}
