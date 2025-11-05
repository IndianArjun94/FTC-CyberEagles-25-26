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

@Autonomous(name = "BLUE Single Ball")
public class BLUEScoreSingleBall extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startingPos = new Pose2d(-35,-22,deg(225));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPos);
        Launcher launcher = new Launcher(hardwareMap);
        SingleBallLoader loader = new SingleBallLoader(hardwareMap);

        Action moveAwayFromGoal = drive.actionBuilder(startingPos)
                .setTangent(deg(90))
                .splineToLinearHeading(new Pose2d(20, 0, deg(180)), deg(0))
                .build();

        Action fullAction = new SequentialAction(
                launcher.startLauncher(0.62),
                new SleepAction(2.5),
                loader.startSingleBallLoader(),
                new SleepAction(1.5),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher(),
                moveAwayFromGoal
        );

        waitForStart();

        Actions.runBlocking(fullAction);

    }
}
