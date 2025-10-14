package com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project;

public interface ProjectInterface {
    String getTitle();
    String getInvestor();
    String getCountry();
    String getVoivodeship();
    String getCity();
    Integer getModulePower();
    Integer getModuleLength();
    Integer getModuleWidth();
    Integer getModuleFrame();
    void setTitle(String title);
    void setInvestor(String investor);
    void setCountry(String country);
    void setVoivodeship(String voivodeship);
    void setCity(String city);
    void setModulePower(Integer modulePower);
    void setModuleLength(Integer moduleLength);
    void setModuleWidth(Integer moduleWidth);
    void setModuleFrame(Integer moduleFrame);
}
