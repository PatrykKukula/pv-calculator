package pl.patrykkukula.Model.ConstructionModel.Abstract;
import lombok.Getter;
import pl.patrykkukula.Model.Installation;
import static java.lang.Math.ceil;
import static pl.patrykkukula.Constants.ConstructionConstants.PROFILE_LENGTH;

@Getter
public abstract class AbstractFlatConstructionModel extends AbstractConstructionModel {
    protected double profile;
    protected double angleBar;
    protected int profileJoiner;
    protected int hexagonScrew;
    protected int hexagonNut;

    protected AbstractFlatConstructionModel(Installation installation) {
       super(installation);
    }

    public void setDetails(){
        this.profile = installation.calculateProfile();
        this.profileJoiner = (int) ceil(this.profile / PROFILE_LENGTH);
        this.angleBar = installation.calculateAngleBarLength();
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
