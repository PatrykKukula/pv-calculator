package pl.patrykkukula.Utils.GeneralUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.patrykkukula.Model.InstallationList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
public class FileManager {

    public void saveFile(Map<String, Integer> constructionMaterials, Map<String,Integer> electricMaterials,
                         InstallationList installationList, String path) throws IOException {
        try (FileOutputStream output = new FileOutputStream(path);
                XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("Materiały");
                int rowNum = 1;
                XSSFRow row = sheet.createRow(0);
                rowNum = saveMaterialList(constructionMaterials, sheet, rowNum);
                rowNum = saveMaterialList(electricMaterials, sheet, rowNum);
                row = sheet.createRow(rowNum++);
                saveInstallationList(installationList, sheet, rowNum);
                for (int i = 0; i<3; i++){
                    sheet.autoSizeColumn(i);
                }
                workbook.write(output);
            }
        }
        private int saveMaterialList(Map<String, Integer> materials, XSSFSheet sheet, int rowNum){
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue("Materiał");
            row.createCell(1).setCellValue("Ilość");
            for (Map.Entry<String, Integer> entry : materials.entrySet()) {
                row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }
            rowNum++;
            return rowNum;
        }
        private void saveInstallationList(InstallationList installationList, XSSFSheet sheet, int rowNum){
            XSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue("Liczba instalacji: " + installationList.getInstallationList().size());
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue("Typ");
            row.createCell(1).setCellValue("Moc [kW]");
            row.createCell(2).setCellValue("Liczba paneli [szt]");
            for (int i = 0; i<installationList.getInstallationList().size(); i++){
                row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(installationList.getInstallationList().get(i).getType());
                row.createCell(1).setCellValue(installationList.getInstallationList().get(i).getTotalPower());
                row.createCell(2).setCellValue(installationList.getInstallationList().get(i).getModulesQty());
            }
        }
    }

