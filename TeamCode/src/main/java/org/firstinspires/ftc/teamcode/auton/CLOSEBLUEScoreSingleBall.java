package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.auton.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.action.launcher.Launcher;
import org.firstinspires.ftc.teamcode.auton.action.loader.SingleBallLoader;

@Autonomous(name = "CLOSE BLUE Single Ball")
public class CLOSEBLUEScoreSingleBall extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startingPos = new Pose2d(-52.5,-46.5,deg(235));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPos);
        Launcher launcher = new Launcher(hardwareMap);
        SingleBallLoader loader = new SingleBallLoader(hardwareMap);

        Action goToGoal = drive.actionBuilder(startingPos)
                .setTangent(deg(55))
                .splineToConstantHeading(
                        new Vector2d(-22.5,-14), deg(55))
                .build();
// TODO update goal angle
        Action goAwayFromGoal = drive.actionBuilder(new Pose2d(-22.5,-14,deg(235)))
                .setTangent(deg(270))
                .splineToLinearHeading(new Pose2d(-24, -53, deg(270)), deg(280))
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
