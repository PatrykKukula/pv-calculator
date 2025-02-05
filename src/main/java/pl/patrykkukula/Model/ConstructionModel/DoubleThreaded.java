package pl.patrykkukula.Model.ConstructionModel;

import lombok.Getter;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractProfileConstructionModel;
import pl.patrykkukula.Model.Installation;

import static java.lang.Math.ceil;
import static pl.patrykkukula.Constants.ConstructionConstants.BETWEEN_RAFTER;

@Getter
public class DoubleThreaded extends AbstractProfileConstructionModel {
    private int doubleThreadedScrew;
    private int adapter;

    public DoubleThreaded(Installation installation) {
        super(installation);
    }

    @Override
    protected void setAdditionalDetails() {
        this.doubleThreadedScrew = (int) ceil(this.profile / BETWEEN_RAFTER);
        this.adapter = doubleThreadedScrew; // it is always equal
        this.hexagonScrew = doubleThreadedScrew; // it is always equal
        this.hexagonNut = doubleThreadedScrew; // it is always equal
    }
}
