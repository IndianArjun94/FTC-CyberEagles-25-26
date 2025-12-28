package org.firstinspires.ftc.teamcode.auton.thirdbot;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.intake.Intake;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher.PIDFlyWheel;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.loading.Lifter;
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.loading.TripleBallQuadLoader;

@Autonomous(name = "Robot Test")
public class RobotTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
//        CRServo lifterServo = hardwareMap.get(CRServo.class, "lifter");
//        waitForStart();
//        //telemetry.addData("initial pos: ", lifterServo.get());
//        //telemetry.update();
//        lifterServo.setPower(0.5);
//        sleep(4000);
//        lifterServo.setPower(-0.5);
//        sleep(4000);
//        //telemetry.addData("new pos: ", lifterServo.getPosition());
//        //telemetry.update();

//        PIDFlyWheel launcher = new PIDFlyWheel(hardwareMap, telemetry);
        PIDFlyWheel launcher = new PIDFlyWheel(hardwareMap, telemetry);
        TripleBallQuadLoader loader = new TripleBallQuadLoader(hardwareMap);
        Intake intake = new Intake(hardwareMap, telemetry);
        Lifter lifter = new Lifter(hardwareMap, telemetry);

        Action runLauncher = launcher.revLauncher(200);

        Action stopLauncher = launcher.stopLauncher();

        Action runIntake = intake.startActiveIntake();

        Action stopIntake = intake.stopActiveIntake();

        Action runLoader = loader.start();

        Action stopLoader = loader.stop();

        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        launcher.revLauncher(180),

                        new SequentialAction(
//                                new ParallelAction(
//                                        launcher.revLauncher(200),
//                                ),
                                new SleepAction(2),
                                lifter.lift(),
                                new SleepAction(2),
                                lifter.reset(),
                                launcher.stopLauncher(),
                                new SleepAction(0.5)
                        )
                )
        );

    }
}
