package pl.patrykkukula.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Trapeze {
    private Installation installation;
    private int trapeze;
    private int screw;
    private int endClamp;
    private int midClamp;
    private int allenScrew;
    private int slidingKey;

    public Trapeze(Installation installation) {
        this.installation = installation;
    }

    public void setTrapeze (Installation installation) {
        List<Integer> rowsAndModules = installation.getRowsAndModules();
        int qty = 0;
        for (Integer rowsAndModule : rowsAndModules) {
            qty += (rowsAndModule * 2 + 2);
        }
        this.trapeze = qty;
    }
    public void setAll(Installation installation, Trapeze trapeze){
        this.screw = trapeze.getTrapeze()*6;
        this.allenScrew = trapeze.getTrapeze();
        this.slidingKey = trapeze.getTrapeze();
        this.endClamp = installation.getRowsAndModules().size()*4;
        this.midClamp = allenScrew - endClamp;
    }
    @Override
    public String toString() {
        return  "Materiały konstrukcyjne: " + System.lineSeparator() +
                "Liczba paneli (jedna wartość oznacza jeden rząd): " + installation.getRowsAndModules() + System.lineSeparator() +
                "Mostki trapezowe: " + trapeze + System.lineSeparator() +
                "Wkręty farmerskie: " + screw + System.lineSeparator() +
                "Wpusty przesuwne: " + slidingKey + System.lineSeparator() +
                "Śruby imbusowe: " + allenScrew + System.lineSeparator() +
                "Klemy środkowe: " + endClamp + System.lineSeparator() +
                "Klemy końcowe " + installation.getModule().getFrame() + "mm: " + endClamp;

    }
}
