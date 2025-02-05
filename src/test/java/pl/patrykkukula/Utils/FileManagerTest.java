package pl.patrykkukula.Utils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.TestInstallation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {
    private final Map<String, Integer> constructionMaterials = createTestConstructionMaterials();
    private final Map<String, Integer> electricMaterials = createTestElectricMaterials();
    private final InstallationList installationList = createTestInstallationList();
    private final String path = "C:/Users/Patryk/Desktop/foto1.xlsx";
    private final FileManager fileManager = new FileManager();
    private final Path createdPath = Paths.get(path);
    private File file = createdPath.toFile();

    @BeforeEach
    public void setUp() throws IOException {
        fileManager.saveFile(constructionMaterials, electricMaterials, installationList, path);
    }
    @AfterEach
    public void deleteFile(){
        file.delete();
    }
    @Test
    public void shouldCreateFileCorrectly() throws IOException {
        assertTrue(Files.exists(createdPath), "plik powinien zostać utworzony");
        assertTrue(file.length() > 0, "plik nie powinien być pusty");
    }
    @Test
    public void shouldWriteCorrectMaterialsToFile() throws FileNotFoundException {
        try (XSSFWorkbook  workBook = new XSSFWorkbook(new FileInputStream(file))){
        XSSFSheet sheet = workBook.getSheet("Materiały");
        XSSFRow secondRow = sheet.getRow(1);
        assertEquals("Materiał", secondRow.getCell(0).getStringCellValue());
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void shouldWriteCorrectInstallationAmountToFile() throws IOException {
        try(XSSFWorkbook  workBook = new XSSFWorkbook(new FileInputStream(file))) {
            XSSFSheet sheet = workBook.getSheet("Materiały");
            XSSFRow installationsRow = sheet.getRow(10);
            assertEquals("Liczba instalacji: 2", installationsRow.getCell(0).getStringCellValue());
        }
    }
    @Test
    public void shouldWriteCorrectInstallationLListToFile() throws IOException {
        try(XSSFWorkbook  workBook = new XSSFWorkbook(new FileInputStream(file))) {
            XSSFSheet sheet = workBook.getSheet("Materiały");
            XSSFRow installationsRow = sheet.getRow(11);
            assertEquals("Typ", installationsRow.getCell(0).getStringCellValue());
            assertEquals("Moc [kW]", installationsRow.getCell(1).getStringCellValue());
            assertEquals("Liczba paneli [szt]", installationsRow.getCell(2).getStringCellValue());
            installationsRow = sheet.getRow(12);
            assertEquals("Mostki trapezowe", installationsRow.getCell(0).getStringCellValue());
            assertEquals(8.64, installationsRow.getCell(1).getNumericCellValue());
            assertEquals(18, installationsRow.getCell(2).getNumericCellValue());
        }
    }
    @Test
    public void shouldWriteCorrectMaterials(){
        try (XSSFWorkbook  workBook = new XSSFWorkbook(new FileInputStream(file))){
            XSSFSheet sheet = workBook.getSheet("Materiały");
            XSSFRow thirdRow = sheet.getRow(2);
            assertEquals("Mostek trapezowy", thirdRow.getCell(0).getStringCellValue());
            assertEquals(50, thirdRow.getCell(1).getNumericCellValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Map<String, Integer> createTestConstructionMaterials(){
        Map<String, Integer> materials = new LinkedHashMap<>();
        materials.put("Mostek trapezowy",50);
        materials.put("Wkręt farmerski", 300);
        return materials;
    }
    private Map<String, Integer> createTestElectricMaterials(){
        Map<String, Integer> materials = new LinkedHashMap<>();
        materials.put("Falownik fotowoltaiczny  6 kW",1);
        materials.put("kabel DC 4 mm2", 30);
        return materials;
    }
    private InstallationList createTestInstallationList(){
        TestInstallation testInstallation = new TestInstallation();
        Installation installation = testInstallation.createTestInstallation();
        installation.setType(1);
        InstallationList installationList = new InstallationList();
        installationList.addInstallation(installation);
        installationList.addInstallation(installation);
        return installationList;
    }
}

