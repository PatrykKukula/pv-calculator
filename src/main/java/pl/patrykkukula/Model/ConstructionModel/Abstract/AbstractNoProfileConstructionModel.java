package pl.patrykkukula.Model.ConstructionModel.Abstract;
import pl.patrykkukula.Model.Installation;

public abstract class AbstractNoProfileConstructionModel extends AbstractConstructionModel {

    protected AbstractNoProfileConstructionModel(Installation installation) {
        super(installation);
    }
    public void setDetails(){
        this.allenScrew = installation.getTotalEdge();
        this.slidingKey = allenScrew; // it is always equal
        this.endClamp = installation.calculateEndClamp();
        this.midClamp = allenScrew - endClamp;
        setAdditionalDetails();
    }
}
