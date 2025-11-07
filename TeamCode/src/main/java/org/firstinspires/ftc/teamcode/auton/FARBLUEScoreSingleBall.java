package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.auton.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.action.launcher.Launcher;
import org.firstinspires.ftc.teamcode.auton.action.loader.SingleBallLoader;

@Autonomous(name = "FAR BLUE Single Ball")
public class FARBLUEScoreSingleBall extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startingPos = new Pose2d(63,-15,deg(180));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPos);
        Launcher launcher = new Launcher(hardwareMap);
        SingleBallLoader loader = new SingleBallLoader(hardwareMap);

        Action goToGoal = drive.actionBuilder(startingPos)
                .setTangent(deg(200))
                .splineToLinearHeading(
                        new Pose2d(-26,-13,deg(230)), deg(170))
                .build();
// TODO update goal angle
        Action goAwayFromGoal = drive.actionBuilder(new Pose2d(-26,-13,deg(230)))
                .setTangent(deg(150))
                .splineToLinearHeading(new Pose2d(-61, -10, deg(180)), deg(180))
                .build();

        Action fullAction = new SequentialAction(
                launcher.startLauncher(0.68),
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
