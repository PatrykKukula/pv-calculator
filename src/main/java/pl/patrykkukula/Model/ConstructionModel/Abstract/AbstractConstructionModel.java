package pl.patrykkukula.Model.ConstructionModel.Abstract;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Model.Installation;

@Getter
@NoArgsConstructor
public abstract class AbstracConstructionModel {
    protected Installation installation;
    protected int endClamp;
    protected int midClamp;
    protected int allenScrew;
    protected int slidingKey;

    public AbstracConstructionModel(Installation installation) {
        if (installation == null || installation.getRowsAndModules().isEmpty())
            throw new IllegalArgumentException("Instalacja nie istnieje lub nie dodano do niej żadnych modułów");
        this.installation = installation;
    }
}
