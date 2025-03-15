package pl.patrykkukula.Model.ConstructionModel.Abstract;
import lombok.Getter;
import pl.patrykkukula.Model.Installation;
import static java.util.Objects.requireNonNull;

@Getter
public abstract class AbstractConstructionModel {
    protected Installation installation;
    protected int endClamp;
    protected int midClamp;
    protected int allenScrew;
    protected int slidingKey;

    public AbstractConstructionModel(Installation installation) {
        requireNonNull(installation, "Instalacja nie może być nullem");
        this.installation = installation;
        setDetails();
    }
    protected void setDetails(){
        this.endClamp = installation.calculateEndClamp();
        this.allenScrew = installation.calculateTotalEdge();
        this.slidingKey = allenScrew; // it is always equal
        this.midClamp = allenScrew - endClamp;

    }
    protected abstract void setAdditionalDetails();
}
