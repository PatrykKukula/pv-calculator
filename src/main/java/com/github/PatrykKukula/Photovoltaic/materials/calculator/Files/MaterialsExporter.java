package com.github.PatrykKukula.Photovoltaic.materials.calculator.Files;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Installation;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Model.Project;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MaterialsExporter {
    private static final String MATERIALS_SHEET_NAME = "materials";
    private static final String SUMMARY_SHEET_NAME = "summary";
    int rowNum = 0;
    int summaryRowNum = 0;
    int lp = 0;

    public ByteArrayOutputStream exportMaterialsToExcelForProject(List<Installation> installations, Project project, Map<String, Long> electricalMaterialsMap,
                                                 Map<String, Long> constructionMaterialsMap, int installationCount, Double power) throws IOException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try(XSSFWorkbook workbook = new XSSFWorkbook()){
            XSSFSheet sheet = workbook.createSheet(MATERIALS_SHEET_NAME);
            createSummary(workbook, project, electricalMaterialsMap, constructionMaterialsMap, installationCount, power);
            for (Installation installation : installations) {
                setInstallationDetails(installation, workbook, sheet, true);
                createConstructionMaterials(workbook, sheet, constructionMaterialsMap);
                rowNum++;
                createElectricalMaterials(workbook, sheet, electricalMaterialsMap);
                rowNum++;
            }
            for (int i = 1; i <= 4; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(outputStream);
        }
        rowNum = 0;
        summaryRowNum = 0;
        lp = 0;
        return outputStream;
    }
    public ByteArrayOutputStream exportMaterialsToExcelForInstallation(Installation installation, Map<String, Long> constructionMaterialsMap,
                                                                       Map<String, Long> electricalMaterialsMap) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try(XSSFWorkbook workbook = new XSSFWorkbook()){
            XSSFSheet sheet = workbook.createSheet(MATERIALS_SHEET_NAME);
            setInstallationDetails(installation, workbook, sheet, false);
            createConstructionMaterials(workbook, sheet, constructionMaterialsMap);
            createElectricalMaterials(workbook, sheet, electricalMaterialsMap);

            for (int i = 1; i<=4; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(outputStream);
        }
        rowNum = 0;
        return outputStream;
    }
    private void setInstallationDetails(Installation installation, XSSFWorkbook workbook,  XSSFSheet sheet, boolean multiple){
        XSSFRow headersRow = createRow(sheet);
        int cellNo = 0;
        if (multiple) createHeaderCell(headersRow, workbook, "Lp.", cellNo);
        createHeaderCell(headersRow, workbook, "Address", ++cellNo);
        createHeaderCell(headersRow, workbook, "Construction type", ++cellNo);
        createHeaderCell(headersRow, workbook, "Phase number", ++cellNo);
        createHeaderCell(headersRow, workbook, "Module orientation", ++cellNo);

        XSSFRow valuesRow = createRow(sheet);
        cellNo = 0;
        if (multiple) {
            XSSFCell lpCell = valuesRow.createCell(cellNo, CellType.NUMERIC);
            lpCell.setCellValue(++lp);
        }
        XSSFCell addressCell = valuesRow.createCell(++cellNo, CellType.STRING);
        XSSFCell constructionCell = valuesRow.createCell(++cellNo, CellType.STRING);
        XSSFCell phaseCell = valuesRow.createCell(++cellNo, CellType.STRING);
        XSSFCell orientationCell = valuesRow.createCell(++cellNo, CellType.STRING);

        addressCell.setCellValue(installation.getAddress());
        constructionCell.setCellValue(installation.getInstallationType().toString());
        phaseCell.setCellValue(installation.getPhaseNumber().toString());
        orientationCell.setCellValue(installation.getModuleOrientation().toString());

        rowNum += 1;
    }
    private void createConstructionMaterials(XSSFWorkbook workbook, XSSFSheet sheet, Map<String, Long> materials) {
        int cellIndex = 0;
        XSSFRow row = createRow(sheet);

        createHeaderCell(row, workbook, "Construction material", ++cellIndex);
        createHeaderCell(row, workbook, "Quantity", ++cellIndex);

        setMaterialAndQuantityCell(sheet, materials);
    }
    private void createElectricalMaterials(XSSFWorkbook workbook, XSSFSheet sheet, Map<String, Long> materials){
        int cellIndex = 0;
        XSSFRow row = createRow(sheet);

        createHeaderCell(row, workbook, "Electrical material", ++cellIndex);
        createHeaderCell(row, workbook, "Quantity", ++cellIndex);

        setMaterialAndQuantityCell(sheet, materials);
    }
    private void createSummary(XSSFWorkbook workbook, Project project, Map<String, Long> electricalmaterialsMap,
                               Map<String, Long> constructionMaterialsMap, int installationCount, Double totalPower){
        XSSFSheet sheet = workbook.createSheet(SUMMARY_SHEET_NAME);
        createSummaryHeaders(sheet, workbook);
        createSummaryValues(sheet, project, installationCount, totalPower);
        summaryRowNum++;

        createElectricalMaterialsSummary(sheet, workbook, electricalmaterialsMap);
        createConstructionMaterialsSummary(sheet, workbook, constructionMaterialsMap);

        for (int i = 1; i<=6; i++){
            sheet.autoSizeColumn(i);
        }
    }
    private void createElectricalMaterialsSummary(XSSFSheet sheet, XSSFWorkbook workbook, Map<String, Long> materialsMap){
        XSSFRow materialHeader = sheet.createRow(++summaryRowNum);
        createHeaderCell(materialHeader, workbook, "Electrical materials", 1);
        createHeaderCell(materialHeader, workbook, "Quantity", 2);

        for (String material : materialsMap.keySet()){
            XSSFRow materialRow = sheet.createRow(++summaryRowNum);
            XSSFCell materialCell = materialRow.createCell(1);
            XSSFCell quantityCell = materialRow.createCell(2);
            materialCell.setCellValue(material);
            quantityCell.setCellValue(materialsMap.get(material));
        }
        summaryRowNum++;
    }
    private void createConstructionMaterialsSummary(XSSFSheet sheet, XSSFWorkbook workbook, Map<String, Long> materialsMap){
        XSSFRow materialHeader = sheet.createRow(++summaryRowNum);
        createHeaderCell(materialHeader, workbook, "Construction materials", 1);
        createHeaderCell(materialHeader, workbook, "Quantity", 2);

        for (String material : materialsMap.keySet()){
            XSSFRow materialRow = sheet.createRow(++summaryRowNum);
            XSSFCell materialCell = materialRow.createCell(1);
            XSSFCell quantityCell = materialRow.createCell(2);
            materialCell.setCellValue(material);
            quantityCell.setCellValue(materialsMap.get(material));
        }
    }
    private void createSummaryHeaders(XSSFSheet sheet, XSSFWorkbook workbook){
        int cellIndex = 0;

        XSSFRow headersRow = sheet.createRow(++summaryRowNum);
        createHeaderCell(headersRow, workbook, "Project title", ++cellIndex);
        createHeaderCell(headersRow, workbook, "Investor", ++cellIndex);
        createHeaderCell(headersRow, workbook, "Module power", ++cellIndex);
        createHeaderCell(headersRow, workbook, "Total installations", ++cellIndex);
        createHeaderCell(headersRow, workbook, "Total power", ++cellIndex);
    }
    private void createSummaryValues(XSSFSheet sheet, Project project, int installationCount, Double totalPower){
        int cellIndex = 0;

        XSSFRow valuesRow = sheet.createRow(++summaryRowNum);
        XSSFCell projectCell = valuesRow.createCell(++cellIndex);
        XSSFCell investorCell = valuesRow.createCell(++cellIndex);
        XSSFCell moduleCell = valuesRow.createCell(++cellIndex);
        XSSFCell installationsCell = valuesRow.createCell(++cellIndex);
        XSSFCell totalPowerCell = valuesRow.createCell(++cellIndex);
        projectCell.setCellValue(project.getTitle());
        investorCell.setCellValue(project.getInvestor());
        moduleCell.setCellValue(project.getModulePower());
        installationsCell.setCellValue(installationCount);
        totalPowerCell.setCellValue(totalPower);
    }
    private void createHeaderCell(XSSFRow row, XSSFWorkbook workbook, String header, int cellIndex){
        XSSFCellStyle headerStyle = createHeaderStyle(workbook);
        XSSFCell cell = row.createCell(cellIndex);
        cell.setCellStyle(headerStyle);
        cell.setCellValue(header);
    }
    private XSSFRow createRow(XSSFSheet sheet){
        XSSFRow row = sheet.createRow(rowNum);
        rowNum++;
        return row;
    }
    private void setMaterialAndQuantityCell(XSSFSheet sheet, Map<String, Long> materials){
        int cellNo = 0;

            for(String material : materials.keySet()) {
                XSSFRow materialRow = createRow(sheet);
                XSSFCell materialCell = materialRow.createCell(++cellNo, CellType.STRING);
                XSSFCell quantityCell = materialRow.createCell(++cellNo, CellType.NUMERIC);
                materialCell.setCellValue(material);
                quantityCell.setCellValue(materials.get(material));
                cellNo = 0;
            }
    }
    private XSSFCellStyle createHeaderStyle(XSSFWorkbook workbook){
        XSSFCellStyle boldStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        boldStyle.setFont(font);
        return boldStyle;
    }
}
