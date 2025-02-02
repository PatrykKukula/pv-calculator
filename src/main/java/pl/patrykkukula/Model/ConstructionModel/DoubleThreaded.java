package pl.patrykkukula.Model.ConstructionModel;
import lombok.Getter;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractProfileConstructionModel;
import pl.patrykkukula.Model.Installation;
import static java.lang.Math.ceil;
import static pl.patrykkukula.Constants.ConstructionConstants.BETWEEN_RAFTER;
import static pl.patrykkukula.Constants.ConstructionConstants.PROFILE_LENGTH;

@Getter
public class DoubleThreaded extends AbstractProfileConstructionModel {
    private int doubleThreadScrew;
    private int adapter;

    public DoubleThreaded(Installation installation) {
        super(installation);
    }

    @Override
    protected void setAdditionalDetails() {
        this.profile = installation.calculateProfile();
        this.profileJoiner = (int)ceil(this.profile / PROFILE_LENGTH);
        this.doubleThreadScrew = (int) ceil(this.profile / BETWEEN_RAFTER);
        this.adapter = doubleThreadScrew; // it is always equal
        this.hexagonScrew = doubleThreadScrew; // it is always equal
        this.hexagonNut = doubleThreadScrew; // it is always equal
    }
}
