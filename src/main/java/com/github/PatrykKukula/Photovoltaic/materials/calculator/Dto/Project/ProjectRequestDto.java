package com.github.PatrykKukula.Photovoltaic.materials.calculator.Dto.Project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProjectRequestDto {
    private String title;
    private String sortDirection;
    private int pageNo;
    private int pageSize;
}
