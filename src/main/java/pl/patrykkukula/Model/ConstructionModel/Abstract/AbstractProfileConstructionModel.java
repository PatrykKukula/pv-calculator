package pl.patrykkukula.Model.ConstructionModel.Abstract;
import lombok.Getter;
import pl.patrykkukula.Model.Installation;
import static java.lang.Math.ceil;
import static pl.patrykkukula.Constants.ConstructionConstants.PROFILE_LENGTH;

@Getter
public abstract class AbstractProfileConstructionModel extends AbstractConstructionModel {
    protected int hexagonScrew;
    protected int hexagonNut;
    protected double profile;
    protected int profileJoiner;
    protected int doubleThreadScrew;
    protected double angleBar;

    protected AbstractProfileConstructionModel(Installation installation) {
       super(installation);
    }
    public void setDetails(){
        this.profile = installation.calculateProfile();
        this.profileJoiner = (int) ceil(this.profile / PROFILE_LENGTH);
        this.endClamp = installation.calculateEndClamp();
        this.allenScrew = installation.getTotalEdge();
        this.slidingKey = allenScrew; // it is always equal
        this.midClamp = allenScrew - endClamp;
        setAdditionalDetails();
    }
    public int getProfile(){
        return (int)profile;
    }
    public int getAngleBar(){
        return (int)ceil(angleBar);
    }
}
