package pl.patrykkukula.Model.ConstructionModel.Abstract;
import pl.patrykkukula.Model.Installation;

public abstract class AbstractNoProfileConstructionModel extends AbstractConstructionModel {

    protected AbstractNoProfileConstructionModel(Installation installation) {
        super(installation);
        setAdditionalDetails();
    }
}
