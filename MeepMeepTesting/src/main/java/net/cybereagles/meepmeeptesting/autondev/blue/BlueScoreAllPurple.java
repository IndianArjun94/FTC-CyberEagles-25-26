package net.cybereagles.meepmeeptesting.autondev.blue;

import static net.cybereagles.meepmeeptesting.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BlueScoreAllPurple {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(18,18)
                .build();

//        Actions:

//        Pose2d startingPos = new Pose2d(-26.5,-9,deg(270));
        Pose2d startingPos = new Pose2d(-70,0,deg(0));

        Action collectFirstBall = myBot.getDrive().actionBuilder(startingPos)
                .splineTo(new Vector2d(-11.5, -40), deg(270))
                .build();

        Action moveToScoreLocation = myBot.getDrive().actionBuilder(new Pose2d(-11.5,-40,deg(270)))
                .setTangent(deg(180))
                .splineToLinearHeading(new Pose2d(-40, -40, deg(220)), deg(270))
                .build();

        Action moveAwayFromScoreLocation = myBot.getDrive().actionBuilder(new Pose2d(-40,-40,deg(220)))
                .strafeTo(new Vector2d(-40,0))
                .turnTo(0)
                .build();

        Action collectSecondBall = myBot.getDrive().actionBuilder(new Pose2d(-40,0,deg(0)))
                .splineToLinearHeading(new Pose2d(11.8, -40, deg(270)), deg(270))
                .build();
        Action moveToScoreLocation2 = myBot.getDrive().actionBuilder(new Pose2d(11.8, -40, deg(270)))
                .setTangent(deg(90))
                .splineToLinearHeading(new Pose2d(-40,-40,deg(220)),deg(270))
                .build();

        Action collectThirdBall = myBot.getDrive().actionBuilder(new Pose2d(-40,-40,deg(220)))
                .splineTo(new Vector2d(35,-40),deg(270))
                .build();


        Action fullShoot = new SequentialAction(
//                startIntake(),
                collectFirstBall,
                new SleepAction(2),
//                stopIntake(),
//                startLauncher(),
                moveToScoreLocation,
//                startSingleBallLoader(),
                new SleepAction(4),
//                stopSingleBallLoader(),
//                stopLauncher(),
                moveAwayFromScoreLocation,
//                startIntake(),
                collectSecondBall,
                new SleepAction(2),
//                stopIntake(),
//                startLauncher(),
                moveToScoreLocation2,
                new SleepAction(4),
//                stopLauncher(),
//                startIntake(),
                collectThirdBall,
                new SleepAction(2)
        );

        myBot.runAction(fullShoot);

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(false)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
