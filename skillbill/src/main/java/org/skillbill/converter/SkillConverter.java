package org.skillbill.converter;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.skillbill.common.Skill;

/**
 * Dieses ist eine Converterklasse, um die Kommunikation zwischen Controller und View zu vereinfachen.
 * Von der Oberflächen können nur einfache Datentypen zurückgegeben werden, nicht aber selbstgebaute Objekt(Entitäten)
 * Dieser Converter sorgt dafür, dass der Controller doch mit den Objekten arbeiten kann
 * @author Matthias van Dijk
 *
 */
@FacesConverter("skillConverter")
public class SkillConverter implements Converter, Serializable {

	
	private static final long serialVersionUID = -611510298538182721L;

	public SkillConverter(List<Skill> skills) {
		super();
		this.skills = skills;
	}

	private static final String STRING = "";

	public List<Skill> skills;

	public Object getAsObject(FacesContext fc, UIComponent uic,
			String submittedvalue) {
		if (submittedvalue.trim().equals(STRING)) {
			return null;
		} else {
			try {
				long number = Long.parseLong(submittedvalue);

				for (Skill s : skills) {
					if (s.getId().intValue() == number) {
						return s;
					}
				}
			} catch (NumberFormatException exception) {
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Conversion Error",
						"Not a valid service"));
			}
		}
		return null;

	}

	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals(STRING)) {
			return STRING;
		} else {
			return String.valueOf(((Skill) value).getId());
		}
	}
}
