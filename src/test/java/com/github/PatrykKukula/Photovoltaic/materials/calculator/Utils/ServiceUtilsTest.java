package com.github.PatrykKukula.Photovoltaic.materials.calculator.Utils;
import com.github.PatrykKukula.Photovoltaic.materials.calculator.Exception.InvalidIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceUtilsTest {
    @Test
    @DisplayName("Should validate sort direction correctly with DESC input")
    public void shouldValidateSortDirectionCorrectlyWithDescInput(){
        String sort = ServiceUtils.validateSortDirection("DESC");

        assertEquals("DESC", sort);
    }
    @Test
    @DisplayName("Should validate sort direction correctly with ASC input")
    public void shouldValidateSortDirectionCorrectlyWithAscInput(){
        String sort = ServiceUtils.validateSortDirection("ASC");

        assertEquals("ASC", sort);
    }
    @Test
    @DisplayName("Should validate sort direction correctly with other input")
    public void shouldValidateSortDirectionCorrectlyWithOtherInput(){
        String sort = ServiceUtils.validateSortDirection("XXX");

        assertEquals("ASC", sort);
    }
    @Test
    @DisplayName("Should throw InvalidIdException when validateId with null input")
    public void shouldThrowInvalidIdExceptionWhenValidateIdWithNullInput(){
        InvalidIdException ex = assertThrows(InvalidIdException.class, () -> ServiceUtils.validateId(null));
        assertEquals("ID cannot be less than 1", ex.getMessage());
    }
    @Test
    @DisplayName("Should throw InvalidIdException when validateId with null input")
    public void shouldNotThrowInvalidIdExceptionWhenValidateIdAndIdIsValid(){
        assertDoesNotThrow(() -> ServiceUtils.validateId(1L));
    }
}
