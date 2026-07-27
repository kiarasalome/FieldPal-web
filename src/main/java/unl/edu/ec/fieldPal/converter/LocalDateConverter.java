package unl.edu.ec.fieldPal.converter;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/** Convierte el valor ISO de un campo HTML {@code type="date"} a LocalDate. */
@FacesConverter("localDateConverter")
public class LocalDateConverter implements Converter<LocalDate> {

    @Override
    public LocalDate getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException exception) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Fecha inválida.", "Selecciona una fecha válida."), exception);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, LocalDate value) {
        return value == null ? "" : value.toString();
    }
}
