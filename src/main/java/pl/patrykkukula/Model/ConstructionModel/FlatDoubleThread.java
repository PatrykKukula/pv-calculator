package pl.patrykkukula.Model.ConstructionModel;
import lombok.Getter;
import pl.patrykkukula.DataPresentationFormatters.FlatDoubleThreadFormatter;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractFlatConstructionModel;
import pl.patrykkukula.Model.Installation;
import static java.lang.Math.ceil;
import static pl.patrykkukula.Constants.ConstructionConstants.*;

@Getter
public class FlatDoubleThread extends AbstractFlatConstructionModel {
    private int doubleThreadScrew;

    public FlatDoubleThread(Installation installation) {
        super(installation);
    }

    public String getDetails() {
        return FlatDoubleThreadFormatter.format(this);
    }
    @Override
    protected void setAdditionalDetails() {
        this.doubleThreadScrew = (int) ceil(this.profile / BETWEEN_RAFTER);
        this.angleBar = installation.calculateAngleBarLength();
        this.hexagonScrew = doubleThreadScrew + installation.calculateAngleBarQty() * PER_ANGLE_BAR ;
        this.hexagonNut = hexagonScrew; // it is always equal
    }


}
