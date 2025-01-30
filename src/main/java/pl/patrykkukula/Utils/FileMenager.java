package pl.patrykkukula.Utils;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.patrykkukula.Model.Installation;
import pl.patrykkukula.Model.InstallationList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import static pl.patrykkukula.Constants.ConstructionConstants.CONVERT_UNIT_FROM_KILOS;

public class FileMenager {

    public void saveFile(Map<String, Integer> materials, InstallationList installationList, String path) throws IOException {
        try (FileOutputStream output = new FileOutputStream(path);
                XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet();
                int rowNum = 1;
                XSSFRow row = sheet.createRow(0);
                rowNum = saveMaterialList(materials, workbook, sheet, row, rowNum);
                sheet.createRow(rowNum++);
                row = sheet.createRow(rowNum++);
                saveInstallationList(installationList, workbook, sheet, row, rowNum);
                workbook.write(output);
            }
        }
        private int saveMaterialList(Map<String, Integer> materials, XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row, int rowNum){
            row.createCell(0).setCellValue("Materiał");
            row.createCell(1).setCellValue("Ilość");
            for (Map.Entry<String, Integer> entry : materials.entrySet()) {
                row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }
            return rowNum;
        }
        private void saveInstallationList(InstallationList installationList, XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row, int rowNum){
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue("Liczba instalacji: " + Installation.count);
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue("Typ");
            row.createCell(1).setCellValue("Moc [kW]");
            row.createCell(2).setCellValue("Liczba paneli [szt]");
            for (int i = 0; i<installationList.getInstallationList().size(); i++){
                row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(installationList.getInstallationList().get(i).getType());
                row.createCell(1).setCellValue(installationList.getInstallationList().get(i).getTotalPower() / CONVERT_UNIT_FROM_KILOS);
                row.createCell(2).setCellValue(installationList.getInstallationList().get(i).getModulesQty());
            }
        }

    }

