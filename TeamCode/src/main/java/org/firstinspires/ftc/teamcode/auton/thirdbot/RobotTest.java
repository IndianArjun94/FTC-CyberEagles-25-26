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
import org.firstinspires.ftc.teamcode.auton.thirdbot.third_bot_modules.launcher.Stopper;
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
        Stopper stopper = new Stopper(hardwareMap, telemetry);

        waitForStart();

        Actions.runBlocking(new ParallelAction(
                new ParallelAction(
                        launcher.revLauncher(165),
                        stopper.initiate(),
                        loader.start(),
                        intake.start(),
                        lifter.reset()
                ),

                new SequentialAction(
                        new SleepAction(6),

                        stopper.open(),

                        new SleepAction(0.8),
                        lifter.lift(),
                        new SleepAction(0.8),
                        lifter.reset(),

                        new SleepAction(0.8),
                        lifter.lift(),
                        new SleepAction(0.8),
                        lifter.reset(),

                        new SleepAction(0.8),
                        lifter.lift(),
                        new SleepAction(0.8),
                        lifter.reset(),

                        new SleepAction(0.8 ),

                        new ParallelAction(
                                launcher.stopLauncher(),
                                stopper.initiate(),
                                loader.stop(),
                                intake.stop()
                        )
                )
        ));

    }
}
