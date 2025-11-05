package net.cybereagles.meepmeeptesting.autondev.blue;

import static net.cybereagles.meepmeeptesting.Util.deg;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BlueScoreSingleBall {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(18, 18)
                .build();

//        Actions:

        Pose2d startingPos = new Pose2d(-35,-22,deg(225));

        Action moveAwayFromScoreLocation = myBot.getDrive().actionBuilder(startingPos)
                .setTangent(deg(90))
                .splineToLinearHeading(new Pose2d(20, 0, deg(180)), deg(0))
                .build();

        Action fullShoot = new SequentialAction(
                new SleepAction(4),
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
