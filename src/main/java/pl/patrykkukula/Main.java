package pl.patrykkukula;
import pl.patrykkukula.Builders.ConstructionBuilder;
import pl.patrykkukula.Model.ConstructionModel.FlatDoubleThread;
import pl.patrykkukula.Model.ConstructionModel.Trapeze;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.Model.PvModule;
import pl.patrykkukula.Utils.ConstructionMapper;
import java.util.List;
import java.util.Map;

// All the formulas for calculating materials that are present in this application
// are based on my work experience, laws or physics and good engineering practice

public class Main {
    public static void main(String[] args) {
//        MenuHandler menuHandler = new MenuHandler();
//        menuHandler.start();
        PvModule pvModule = new PvModule(500, 35, 1134,2050);
        List<Integer> rowsAndModules = List.of(12,10);
        Installation installation = new Installation(50,25,rowsAndModules, pvModule, "pionowo");
        installation.setType("Mostki trapezowe");
        installation.setTotalPower();
        Trapeze trapeze = new Trapeze(installation);
        trapeze.setDetails();
        installation.setModel(trapeze);
//        System.out.println(trapeze.getDetails());
        ConstructionMapper constructionMapper = new ConstructionMapper();
        InstallationList list = new InstallationList();
        Installation installation1 = new Installation(25,50,rowsAndModules,pvModule,"poziomo");
        installation1.setType("Dach płaski - śruba dwugwintowa");
        installation1.setTotalPower();

        FlatDoubleThread flatDoubleThread = new FlatDoubleThread(installation1);
        flatDoubleThread.setDetails();
        installation1.setModel(flatDoubleThread);

        list.addInstallation(installation);
        list.addInstallation(installation);
        list.addInstallation(installation);
        list.addInstallation(installation1);
        list.getAllInstallations();
        ConstructionBuilder constructionBuilder = new ConstructionBuilder(list);
        Map<String, Integer> materials = constructionBuilder.materials();
        System.out.println(materials);
//






//
//      Map<String, Integer> map = constructionMapper.map(installation, trapeze, installation.getType());
//      Map<String, Integer> map1 = constructionMapper.map(installation, trapeze, installation.getType());
//     Map<String, Integer> maps = new LinkedHashMap<>();
//        Set<String> keys = map.keySet();
//        Set<String> keys1 = map1.keySet();
//        for (String key : keys){
//            maps.put(key, maps.getOrDefault(key,0) + map.get(key));
//        }
//        for (String key : keys1){
//            maps.put(key, maps.getOrDefault(key,0) + map.get(key));
//        }
//
//
//        System.out.println(maps);
    }
}
