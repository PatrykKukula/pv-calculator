package com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception;

public class ExportToExcelException extends AppException{
    public ExportToExcelException( String logMessage) {
        super("Error exporting materials. Please try again or contact administrator", logMessage);
    }
}
