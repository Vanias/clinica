package clinical.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import clinical.bean.Especialidade;
import clinical.db.Buscar;

@FacesConverter(value = "converterEspecialidade", forClass = Especialidade.class)
public class ConverterEspecialidade implements Converter {
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		System.out.println("ConverterEspecialidade.getAsObject()");
		if (arg2 == null || arg2.equals("")) {
			return null;
		}

		try {
			Integer.parseInt(arg2);
		} catch (Exception e) {
			return null;
		}

		try {
			return (Especialidade) Buscar.buscarEspecialidadePorId(Integer
					.valueOf(arg2));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		System.out.println("ConverterEspecialidade.getAsString()");
		if (arg2 == null) {
			return null;
		}

		return String.valueOf(((Especialidade) arg2).getId());
	}
}