package pl.patrykkukula.Model;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;
import static java.lang.Math.sqrt;
import static pl.patrykkukula.Constants.ConstructionConstants.CONVERT_UNIT_FROM_KILOS;
import static pl.patrykkukula.Constants.ElectricConstants.*;

@Getter
public class Inverter {
    private int power;
    private double current;
    private static final NavigableMap<Double, Integer> powerMap = new TreeMap<>();
    static {
        powerMap.put(3.3, 3);
        powerMap.put(4.4, 4);
        powerMap.put(5.5, 5);
        powerMap.put(6.6,6);
        powerMap.put(8.8,8);
        powerMap.put(11.0,10);
        powerMap.put(13.2,12);
        powerMap.put(16.5,15);
        powerMap.put(18.7,17);
        powerMap.put(22.0,20);
        powerMap.put(27.5,25);
        powerMap.put(33.0,30);
        powerMap.put(55.0,50);
        powerMap.put(66.0,60);
    }

    public Inverter(Installation installation) {
        Objects.requireNonNull(installation, "Instalacja nie może być nullem");
        setPower(installation);
        setCurrent(installation);
    }
    private void setPower(Installation installation){
        Map.Entry<Double, Integer> doubleIntegerEntry = powerMap.ceilingEntry(installation.getTotalPower());
        this.power = doubleIntegerEntry.getValue();
    }
    private void setCurrent(Installation installation){
            BigDecimal calculatedCurrent = new BigDecimal(power * CONVERT_UNIT_FROM_KILOS / (sqrt(3) * COS_FI * THREE_PHASE_VOLTAGE));
            BigDecimal roundedCurrent = calculatedCurrent.setScale(2, RoundingMode.HALF_UP);
            this.current = roundedCurrent.doubleValue();
    }
}

