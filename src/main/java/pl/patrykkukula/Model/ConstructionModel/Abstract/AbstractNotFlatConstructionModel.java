package pl.patrykkukula.Model.ConstructionModel.Abstract;
import pl.patrykkukula.Model.Installation;

public abstract class AbstractNotFlatConstructionModel extends AbstractConstructionModel {

    protected AbstractNotFlatConstructionModel(Installation installation) {
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
