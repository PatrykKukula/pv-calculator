package com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils;

import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.InvalidIdException;

public class ServiceUtils {
    public static String validateSortDirection(String sortDirection){
        return (sortDirection.equals("DESC")) ? "DESC" : "ASC";
    }
    public static void validateId(Long id){
        if (id == null || id<1) throw new InvalidIdException(id);
    }
}
