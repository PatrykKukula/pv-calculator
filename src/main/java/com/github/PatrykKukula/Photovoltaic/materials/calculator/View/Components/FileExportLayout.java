package com.github.PatrykKukula.Photovoltaic.materials.calculator.View.Components;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.InstallationService;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Service.ProjectService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.streams.DownloadEvent;

import java.io.OutputStream;

public class FileExportLayout extends Div {
    private InstallationService installationService;
    private ProjectService projectService;
    private final Dialog dialog = new Dialog();
    private final TextField fileName = new TextField("File name");

    public FileExportLayout(Long installationId, InstallationService installationService){
        this.installationService = installationService;
        setupInstallationDialog(installationId);
        fileName.setHelperText("Insert file name");
        getStyle().set("margin", "auto");
        add(exportToExcelButton());
    }
    public FileExportLayout(Long projectId, ProjectService projectService){
        this.projectService = projectService;
        setupProjectDialog(projectId);
        setupProjectDialog(projectId);
        fileName.setHelperText("Insert file name");
        add(exportToExcelButton());
    }
    private Button exportToExcelButton() {
        Button button = new Button("Export materials to excel");
        button.getStyle().set("align-self", "center");
        button.addClickListener(e -> {
            dialog.open();
        });
        return button;
    }
    private VerticalLayout exportInstallationLayout(Long installationId){
        VerticalLayout layout = new VerticalLayout();
        layout.getStyle().set("border", "1px solid blue").set("border-radius", "10px").set("width", "200px").set("height", "40px")
                .set("background-color", "#5B80F0").set("align-items", "center").set("justify-content", "center");
        Anchor exportAnchor = exportInstallationAnchor(installationId, fileName);
        layout.add(exportAnchor);
        return layout;
    }
    private VerticalLayout exportProjectLayout(Long projectId){
        VerticalLayout layout = new VerticalLayout();
        layout.getStyle().set("border", "1px solid blue").set("border-radius", "10px").set("width", "200px").set("height", "40px")
                .set("background-color", "#5B80F0").set("align-items", "center").set("justify-content", "center");
        Anchor exportAnchor = exportProjectAnchor(projectId, fileName);
        layout.add(exportAnchor);
        return layout;
    }
    private Anchor exportProjectAnchor(Long projectId, TextField filename) {
        Anchor anchor = new Anchor((DownloadEvent event) -> {
            String sanitizeFileName = sanitizeFileName(filename.getValue());
            event.setFileName(sanitizeFileName + ".xlsx");
            try (OutputStream outputStream = event.getOutputStream()) {
                outputStream.write(projectService.exportProjectMaterialsToExcel(projectId).toByteArray());
            }
            event.getUI().access(() -> {});

        }, "Export to excel");
        anchor.getStyle().set("align-self", "center").set("color", "white").set("margin", "auto").set("justify-self", "center");
        return anchor;
    }
    private Anchor exportInstallationAnchor(Long installationId, TextField filename) {
        Anchor anchor = new Anchor((DownloadEvent event) -> {
            String sanitizeFileName = sanitizeFileName(filename.getValue());
            event.setFileName(sanitizeFileName + ".xlsx");
            try (OutputStream outputStream = event.getOutputStream()) {
                outputStream.write(installationService.exportInstallationMaterialsToExcel(installationId).toByteArray());
            }
            event.getUI().access(() -> dialog.close());
        }, "Export to excel");
        anchor.getStyle().set("align-self", "center").set("color", "white").set("margin", "auto").set("justify-self", "center");
        return anchor;
    }
    private void setupInstallationDialog(Long installationId){
        setUpBasicDialog();
        VerticalLayout layout = exportInstallationLayout(installationId);
        dialog.add(layout);
    }
    private void setupProjectDialog(Long projectId){
        setUpBasicDialog();
        VerticalLayout layout = exportProjectLayout(projectId);
        dialog.add(layout);
    }
    private void setUpBasicDialog(){
        dialog.add(fileName);
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
    }
    private String sanitizeFileName(String input) {
        if (input == null || input.isBlank()) return "export.xlsx";
        return input.replaceAll("[\\\\/.,0]", "_");
    }
}
