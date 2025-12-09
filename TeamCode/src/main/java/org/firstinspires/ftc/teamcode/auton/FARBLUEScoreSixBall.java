package org.firstinspires.ftc.teamcode.auton;

import static org.firstinspires.ftc.teamcode.auton.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.auton.module.intake.Intake;
import org.firstinspires.ftc.teamcode.auton.module.launcher.Launcher;
import org.firstinspires.ftc.teamcode.auton.module.loader.SingleBallLoader;

import java.util.Arrays;

@Autonomous(name = "FAR BLUE Single Ball")
public class FARBLUEScoreSixBall extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startingPos = new Pose2d(63,-15,deg(180));
        Pose2d shootingPos = new Pose2d(-22.5,-16,deg(230));
        Pose2d intake1 = new Pose2d(-11.5,-37, deg(270));
        Pose2d intake2 = new Pose2d(-11.5,-39, deg(270));
        Pose2d intake3 = new Pose2d(-11.5,-41, deg(270));
        Pose2d intake4 = new Pose2d(11.5,-37, deg(270));
        Pose2d intake5 = new Pose2d(11.5,-39, deg(270));
        Pose2d intake6 = new Pose2d(11.5,-41, deg(270));

        MinVelConstraint velConstraintForBalls = new MinVelConstraint(
                Arrays.asList(
                        new TranslationalVelConstraint(25),
                        new AngularVelConstraint(deg(230))));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPos);
        Launcher launcher = new Launcher(hardwareMap);
        SingleBallLoader loader = new SingleBallLoader(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        Action goToGoalPreload = drive.actionBuilder(startingPos)
                .setTangent(deg(200))
                .splineToLinearHeading(
                        shootingPos, deg(170))
                .build();
// TODO update goal angle
        Action goAwayFromGoal = drive.actionBuilder(shootingPos)
                .setTangent(deg(150))
                .splineToLinearHeading(new Pose2d(-61, -10, deg(180)), deg(180))
                .build();

        Action goIntakeBalls = drive.actionBuilder(shootingPos)
                .setTangent(deg(0))
                .splineToLinearHeading(intake1, deg(270),velConstraintForBalls)
                .build();

        Action goToGoal1 = drive.actionBuilder(intake1)
                .splineToLinearHeading(shootingPos, deg(180),velConstraintForBalls)
                .build();

        Action goIntakeBalls2 = drive.actionBuilder(shootingPos)
                .setTangent(deg(0))
                .splineToLinearHeading(intake2, deg(270),velConstraintForBalls)
                .build();

        Action goToGoal2 = drive.actionBuilder(intake2)
                .splineToLinearHeading(shootingPos, deg(180), velConstraintForBalls)
                .build();

        Action goIntakeBalls3 = drive.actionBuilder(shootingPos)
                .setTangent(deg(0))
                .splineToLinearHeading(intake3, deg(270),velConstraintForBalls)
                .build();

        Action goToGoal3 = drive.actionBuilder(intake3)
                .splineToLinearHeading(shootingPos, deg(150), velConstraintForBalls)
                .build();

        Action goIntakeBalls4 = drive.actionBuilder(shootingPos)
                .setTangent(deg(0))
                .splineToLinearHeading(intake4, deg(270), velConstraintForBalls)
                .build();

        Action goToGoal4 = drive.actionBuilder(intake4)
                .setTangent(deg(90))
                .splineToLinearHeading(shootingPos, deg(180), velConstraintForBalls)
                .build();

        Action goIntakeBalls5 = drive.actionBuilder(shootingPos)
                .setTangent(deg(0))
                .splineToLinearHeading(intake5, deg(270),velConstraintForBalls)
                .build();

        Action goToGoal5 = drive.actionBuilder(intake5)
                .setTangent(deg(90))
                .splineToLinearHeading(shootingPos, deg(180), velConstraintForBalls)
                .build();

        Action goIntakeBalls6 = drive.actionBuilder(shootingPos)
                .setTangent(deg(0))
                .splineToLinearHeading(intake6, deg(270), velConstraintForBalls)
                .build();

        Action goToGoal6 = drive.actionBuilder(intake6)
                .setTangent(deg(90))
                .splineToLinearHeading(shootingPos, deg(180), velConstraintForBalls)
                .build();

        Action preload = new SequentialAction(
                launcher.startLauncher(0.65),
                goToGoalPreload,
                new SleepAction(0.25),
                loader.startSingleBallLoader(),
                new SleepAction(1.5),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );
        Action ball1 = new SequentialAction(
                intake.startActiveIntake(),
                goIntakeBalls,
                launcher.startLauncher(0.65),
                goToGoal1,
                loader.startSingleBallLoader(),
                new SleepAction(1.5),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );
        Action ball2 = new SequentialAction(
                intake.startActiveIntake(),
                goIntakeBalls2,
                launcher.startLauncher(0.65),
                goToGoal2,
                loader.startSingleBallLoader(),
                new SleepAction(1.5),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );
        Action ball3 = new SequentialAction(
                intake.startActiveIntake(),
                goIntakeBalls3,
                launcher.startLauncher(0.65),
                goToGoal3,
                loader.startSingleBallLoader(),
                new SleepAction(1.5),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );
        Action ball4 = new SequentialAction(
                intake.startActiveIntake(),
                goIntakeBalls4,
                launcher.startLauncher(0.65),
                goToGoal4,
                loader.startSingleBallLoader(),
                new SleepAction(1.5),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );
        Action ball5 = new SequentialAction(
                intake.startActiveIntake(),
                goIntakeBalls5,
                launcher.startLauncher(0.65),
                goToGoal5,
                loader.startSingleBallLoader(),
                new SleepAction(1.5),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );
        Action ball6 = new SequentialAction(
                intake.startActiveIntake(),
                goIntakeBalls6,
                launcher.startLauncher(0.65),
                goToGoal6,
                loader.startSingleBallLoader(),
                new SleepAction(1.5),
                loader.stopSingleBallLoader(),
                launcher.stopLauncher()
        );
        Action fullAction = new SequentialAction(
                preload,
                ball1,
                ball2,
                ball3,
                ball4,
                ball5,
                ball6
        );

        waitForStart();

        Actions.runBlocking(fullAction);
    }
}
