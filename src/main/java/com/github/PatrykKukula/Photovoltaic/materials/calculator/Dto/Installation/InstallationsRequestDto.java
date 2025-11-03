package com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Installation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InstallationsRequestDto {
    private String sortDirection;
    private int pageNo;
    private int pageSize;
}
