package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(
            c -> {
                c.frontLeftName.set("front_left_drive");
                c.backLeftName.set("back_left_drive");
                c.frontRightName.set("front_right_drive");
                c.backRightName.set("back_right_drive");

                c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
                c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
                c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
                c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);

                c.manualBrakeMode.set(true);
            }
    );

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        c.xPodOffset.set(-2.2553529514102486);
        c.yPodOffset.set(1.4926458343746154);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.19087761747541768);
                Controller secondaryTranslationalForward = Controller.proportional(0.07052417931329351);
                Controller primaryTranslationalLateral = Controller.proportional(0.19916310248685);
                Controller secondaryTranslationalLateral = Controller.proportional(0.07358544463278083);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.017459327264646742));
                c.brake.set(Controller.proportionalFeedforward(0.014840428174949731));

                c.headingFeedback.set(Controller.proportional(3.013130733671371));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.041714278693242374, 0.004725923876250784));

                c.linearBrakeCoefficients.set(Matrix.diag(0.07315528017501169, 0.041896031578080924));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.001209010285841823, 0.00164062073649641));

                c.maxAchievableForwardVelocity.set(60.51692457290269);
                c.maxAchievableStrafeVelocity.set(51.05265404775206);
                c.naturalForwardDeceleration.set(34.01177384954168);
                c.naturalStrafeDeceleration.set(53.71047253685641);
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }


}