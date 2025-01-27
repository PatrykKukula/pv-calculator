package pl.patrykkukula.Model.ConstructionModel;
import lombok.Getter;
import pl.patrykkukula.DataPresentationFormatters.FlatThreadedRodFormatter;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractFlatConstructionModel;
import pl.patrykkukula.Model.Installation;
import static java.lang.Math.ceil;
import static pl.patrykkukula.Constants.ConstructionConstants.*;


@Getter
public class FlatThreaderRod extends AbstractFlatConstructionModel {
    private int threadedRod;
    private int epdm;
    private int chemicalAnchor;
    private int sleeve;

    public FlatThreaderRod(Installation installation){
        super(installation);
    }

    public String getDetails() {
        return FlatThreadedRodFormatter.format(this);
    }
    @Override
    protected void setAdditionalDetails() {
        int angleBarQty = installation.calculateAngleBarQty();
        this.threadedRod = angleBarQty * THREADED_ROD_PER_ANGLE_BAR;
        this.hexagonScrew = threadedRod + angleBarQty * PER_ANGLE_BAR ;
        this.hexagonNut = hexagonScrew + threadedRod * NUT_PER_THREADED_ROD;
        this.epdm = threadedRod; // it is always equal
        this.chemicalAnchor = (int) ceil(threadedRod / THREADED_ROD_PER_CHEMICAL_ANCHOR); // value of dividing these two values is very likely to be double type
        this.sleeve = threadedRod; // it is always equal
    }

}
