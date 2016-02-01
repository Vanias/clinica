package clinical.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class WebUtil {
	public static HttpSession getHttpSession() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return (HttpSession) fc.getExternalContext().getSession(true);
	}

	public static Date parseDate(String date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setLenient(false);

		return format.parse(date);
	}

	public static String formatDate(Date date) {
		if (date != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(date);
		} else {
			return "";
		}
	}
}
