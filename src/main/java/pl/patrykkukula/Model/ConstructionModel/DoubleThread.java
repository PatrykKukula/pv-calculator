package pl.patrykkukula.Model.ConstructionModel;
import lombok.Getter;
import pl.patrykkukula.DataPresentationFormatters.DoubleThreadFormatter;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractNotFlatConstructionModel;
import pl.patrykkukula.Model.Installation;
import static java.lang.Math.ceil;
import static pl.patrykkukula.Constants.ConstructionConstants.BETWEEN_RAFTER;
import static pl.patrykkukula.Constants.ConstructionConstants.PROFILE_LENGTH;

@Getter
public class DoubleThread extends AbstractNotFlatConstructionModel {
    private double profile;
    private int profileJoiner;
    private int doubleThreadScrew;
    private int adapter;
    private int hexagonScrew;
    private int hexagonNut;

    public DoubleThread(Installation installation) {
        super(installation);
    }

    @Override
    protected void setAdditionalDetails() {
        this.profile = installation.calculateProfile();
        this.profileJoiner = (int) ceil(this.profile / PROFILE_LENGTH);
        this.doubleThreadScrew = (int) ceil(this.profile / BETWEEN_RAFTER);
        this.adapter = doubleThreadScrew; // it is always equal
        this.hexagonScrew = doubleThreadScrew; // it is always equal
        this.hexagonNut = doubleThreadScrew; // it is always equal
    }
    public String getDetails() {
      return DoubleThreadFormatter.format(this);
    }

    public int getProfile(){
        return (int)ceil(this.profile);
    }
}
