package pl.patrykkukula.Model.ConstructionModel;
import lombok.Getter;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractProfileConstructionModel;
import pl.patrykkukula.Model.Installation;
import static java.lang.Math.ceil;
import static pl.patrykkukula.Constants.ConstructionConstants.*;

@Getter
public class FlatDoubleThreaded extends AbstractProfileConstructionModel {
    private int angleBar;
    private int doubleThreadedScrew;

    public FlatDoubleThreaded(Installation installation) {
        super(installation);
    }

    @Override
    protected void setAdditionalDetails() {
        this.doubleThreadedScrew = (int) ceil(this.profile / BETWEEN_RAFTER);
        this.angleBar = installation.calculateAngleBarLength();
        this.hexagonScrew = installation.calculateAngleBarQty() * 2 + installation.calculateAngleBarQty() * PER_ANGLE_BAR ;
        this.hexagonNut = hexagonScrew; // it is always equal
    }
}
