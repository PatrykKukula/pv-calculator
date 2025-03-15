package pl.patrykkukula.Model;
import lombok.Getter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NavigableMap;
import java.util.TreeMap;
import static java.lang.Math.sqrt;
import static java.util.Objects.*;
import static pl.patrykkukula.Constants.ConstructionConstants.CONVERT_UNIT_FROM_KILOS;
import static pl.patrykkukula.Constants.ElectricConstants.*;

@Getter
public class Inverter {
    private int inverterPower;
    private double current;
    private static final NavigableMap<Double, Integer> inverterPowerMap = new TreeMap<>();
    static {
        inverterPowerMap.put(3.3, 3);
        inverterPowerMap.put(4.4, 4);
        inverterPowerMap.put(5.5, 5);
        inverterPowerMap.put(6.6,6);
        inverterPowerMap.put(8.8,8);
        inverterPowerMap.put(11.0,10);
        inverterPowerMap.put(13.2,12);
        inverterPowerMap.put(16.5,15);
        inverterPowerMap.put(18.7,17);
        inverterPowerMap.put(22.0,20);
        inverterPowerMap.put(27.5,25);
        inverterPowerMap.put(33.0,30);
        inverterPowerMap.put(44.0,40);
        inverterPowerMap.put(55.0,50);
        inverterPowerMap.put(66.0,60);
    }
    public Inverter(Installation installation) {
        requireNonNull(installation, "Instalacja nie może być nullem");
        setInverterPower(installation);
        setCurrent();
    }
    private void setInverterPower(Installation installation){
        this.inverterPower = inverterPowerMap.ceilingEntry(installation.getTotalPower()).getValue();
    }
    private void setCurrent(){
            BigDecimal calculatedCurrent = new BigDecimal(inverterPower * CONVERT_UNIT_FROM_KILOS / (sqrt(3) * COS_FI * THREE_PHASE_VOLTAGE));
            BigDecimal roundedCurrent = calculatedCurrent.setScale(2, RoundingMode.HALF_UP);
            this.current = roundedCurrent.doubleValue();
    }
}

