package pl.patrykkukula.Model.ConstructionModel;

import lombok.Getter;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractConstructionModel;
import pl.patrykkukula.Model.Installation;

import static pl.patrykkukula.Constants.ConstructionConstants.SCREWS_PER_TRAPEZE;

@Getter
public class Trapeze extends AbstractConstructionModel {
    private int trapeze;
    private int selfDrillingScrew;

    public Trapeze(Installation installation){
     super(installation);
     setAdditionalDetails();
    }
    @Override
    protected void setAdditionalDetails() {
        this.trapeze = installation.calculateTotalEdge();
        this.selfDrillingScrew = this.trapeze * SCREWS_PER_TRAPEZE;
    }
}
