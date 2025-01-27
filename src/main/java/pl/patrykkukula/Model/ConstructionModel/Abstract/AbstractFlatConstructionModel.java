package pl.patrykkukula.Model.ConstructionModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Model.Installation;

@Getter
@NoArgsConstructor
public abstract class AbstractFlatConstructionModel {
    protected Installation installation;
    protected double profile;
    protected double angleBar;
    protected int profileJoiner;
    protected int hexagonScrew;
    protected int hexagonNut;
    protected int endClamp;
    protected int midClamp;
    protected int allenScrew;
    protected int slidingKey;

    protected AbstractFlatConstructionModel(Installation installation) {
        this.installation = installation;
    }
}
