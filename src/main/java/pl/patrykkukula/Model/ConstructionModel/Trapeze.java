package pl.patrykkukula.Model.ConstructionModel;
import lombok.Getter;
import pl.patrykkukula.DataPresentationFormatters.TrapezeFormatter;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractNotFlatConstructionModel;
import pl.patrykkukula.Model.Installation;
import static pl.patrykkukula.Constants.ConstructionConstants.SCREWS_FOR_TRAPEZE;

@Getter
public class Trapeze extends AbstractNotFlatConstructionModel {
    private int trapeze;
    private int screw;

    public Trapeze(Installation installation){
     super(installation);
    }

    @Override
    protected void setAdditionalDetails() {
        this.trapeze = installation.getTotalEdge();
        this.screw = this.trapeze * SCREWS_FOR_TRAPEZE;
    }

    public String getDetails(){
        return TrapezeFormatter.format(this);
    }
}
