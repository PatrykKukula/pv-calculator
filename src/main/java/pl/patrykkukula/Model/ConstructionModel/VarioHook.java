package pl.patrykkukula.Model.ConstructionModel;
import lombok.Getter;
import pl.patrykkukula.Model.ConstructionModel.Abstract.AbstractProfileConstructionModel;
import pl.patrykkukula.Model.Installation;
import static java.lang.Math.ceil;
import static pl.patrykkukula.Constants.ConstructionConstants.BETWEEN_RAFTER;
import static pl.patrykkukula.Constants.ConstructionConstants.SCREWS_PER_VARIO;

@Getter
public class VarioHook extends AbstractProfileConstructionModel {
    private int varioHook;
    private int woodScrew;

    public VarioHook(Installation installation) {
        super(installation);
    }
    @Override
    protected void setAdditionalDetails() {
        this.varioHook = (int)ceil(this.profile / BETWEEN_RAFTER);
        this.hexagonScrew = varioHook; // this is always equal
        this.hexagonNut = varioHook; // this is always equal
        this.woodScrew = varioHook * SCREWS_PER_VARIO;
    }
}
