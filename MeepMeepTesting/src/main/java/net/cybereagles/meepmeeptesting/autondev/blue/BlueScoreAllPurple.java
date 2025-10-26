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
                .build();

//        Actions:

        Pose2d startingPos = new Pose2d(-9,-9,deg(270));

        Action moveToScoreLocation = myBot.getDrive().actionBuilder(startingPos)
                .setTangent(deg(180))
                .splineToLinearHeading(new Pose2d(-40, -40, deg(220)), deg(270))
                .build();

        Action moveAwayFromScoreLocation = myBot.getDrive().actionBuilder(new Pose2d(-40, -40, deg(220)))
                .strafeTo(new Vector2d(-40,0))
                .build();

        Action fullShoot = new SequentialAction(
//                startLauncher(),
                moveToScoreLocation,
//                startSingleBallLoader(),
                new SleepAction(4),
//                stopSingleBallLoader(),
//                stopLauncher(),
                moveAwayFromScoreLocation
        );

        myBot.runAction(fullShoot);

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(false)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
