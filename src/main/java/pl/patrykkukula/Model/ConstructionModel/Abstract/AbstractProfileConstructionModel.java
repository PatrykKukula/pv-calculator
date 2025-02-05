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
        this.profile = installation.calculateProfileLength();
        this.profileJoiner = (int) ceil(this.profile / PROFILE_LENGTH);
        setAdditionalDetails();
    }
    public int getProfile(){
        return (int)profile;
    }
    public int getAngleBar(){
        return (int)ceil(angleBar);
    }
}
