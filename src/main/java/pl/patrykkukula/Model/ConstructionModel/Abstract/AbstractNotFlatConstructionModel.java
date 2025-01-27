package pl.patrykkukula.Model.ConstructionModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Model.Installation;

@Getter
@NoArgsConstructor
public abstract class AbstractConstructionModel {
    protected Installation installation;
    protected int endClamp;
    protected int midClamp;
    protected int allenScrew;
    protected int slidingKey;

    protected AbstractConstructionModel(Installation installation) {
        this.installation = installation;
    }
}
