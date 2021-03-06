package clinical.security.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import clinical.bean.Usuario;

@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD }, urlPatterns = { "/medico/*" })
public class FilterMedico implements Filter {

	public FilterMedico() {
		System.out.println("LoginFilter.LoginFilter()");
	}

	@Override
	public void destroy() {
		System.out.println("LoginFilter.destroy()");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("LoginFilter.doFilter()");

		/*
		 * TODO descomentar o c�digo abaixo para utilizar valida��o de usu�rio e
		 * senha
		 */
		/* */
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

		if (usuarioLogado == null) {
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect("/ClinicalManager/pub/Login.jsf");
		} else {
			if (usuarioLogado.getNivel().equals(Usuario.NIVEL_GERENTE)) {
				HttpServletResponse res = (HttpServletResponse) response;
				res.sendRedirect("/ClinicalManager/gerente/Principal.jsf");
			} else if (usuarioLogado.getNivel().equals(Usuario.NIVEL_ATENDENTE)) {
				HttpServletResponse res = (HttpServletResponse) response;
				res.sendRedirect("/ClinicalManager/atendente/Principal.jsf");
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("LoginFilter.init()");
	}
}