package pl.patrykkukula.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.patrykkukula.Utils.Constants;

import java.util.List;

@Getter
@NoArgsConstructor
public class DoubleThread {
    private Installation installation;
    private double profile;
    private int profileJoiner;
    private int doubleThreadScrew;
    private int adapter;
    private int hexagonScrew;
    private int hexagonNut;
    private int endClamp;
    private int midClamp;
    private int allenScrew;
    private int slidingKey;

    public DoubleThread(Installation installation) {
        this.installation = installation;
    }

    public void setProfile(Installation installation, String orientation){
            this.profile = calculateProfile(installation, orientation);
    }
    public void setAll(Installation installation, DoubleThread doubleThread){
        this.profileJoiner = (int)(doubleThread.getProfile()/6.65);
        this.doubleThreadScrew = (int)(doubleThread.getProfile()/Constants.BETWEEN_RAFTER);
    }

    private double calculateProfile(Installation installation, String orientation){
        int modulesQty = installation.getModulesQty();
        double calculationLenght = 0.0;
        if (orientation.equals("vertical")){
          calculationLenght = installation.getModule().getWidth();
        } else if (orientation.equals("horizontal")) {
           calculationLenght = installation.getModule().getLength();
        }
        return modulesQty*calculationLenght*2*1.05;
    }


}
