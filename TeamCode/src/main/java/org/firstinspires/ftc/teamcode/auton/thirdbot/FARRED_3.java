package org.firstinspires.ftc.teamcode.auton.thirdbot;

import static org.firstinspires.ftc.teamcode.auton.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher.Lifter;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.loading.TripleBallQuadLoader;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher.PIDFlyWheel;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher.Stopper;

@Autonomous(name = "FAR RED ;)")
public class FARRED_3 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startingPos = new Pose2d(62, 12, deg(180));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPos);
        PIDFlyWheel launcher = new PIDFlyWheel(hardwareMap, telemetry);
        TripleBallQuadLoader loader = new TripleBallQuadLoader(hardwareMap);
        Stopper stopper = new Stopper(hardwareMap, telemetry);
        Lifter lifter = new Lifter(hardwareMap, telemetry);

        Action goToGoal1 = drive.actionBuilder(startingPos)
                .lineToX(52)
                .turn(deg(-33))
                .build();

        Action leaveStartZone = drive.actionBuilder(new Pose2d(52, -12, deg(180+26)))
                .strafeTo(new Vector2d(62, 40))
                .build();

        Action shoot = new SequentialAction(
                goToGoal1,
                stopper.open(),
                lifter.lift(), // shoot
                new SleepAction(0.4),
                lifter.reset(), // lower
                loader.start(), // load ball
                new SleepAction(1),
                loader.stop(),
                lifter.lift(), // shoot
                new SleepAction(0.4),
                lifter.reset(),
                loader.start(),
                new SleepAction(1),
                loader.stop(),
                lifter.lift(), // shoot
                new SleepAction(0.4),
                lifter.reset(),
                stopper.initiate(),
                leaveStartZone
        );

        waitForStart();

        Actions.runBlocking(new ParallelAction(launcher.revLauncher(190), shoot));

    }
}