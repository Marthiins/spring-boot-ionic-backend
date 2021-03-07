package com.marthiins.cursomc.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class HeaderExposureFilter implements Filter {

	//Metodo do implemento HeaderExposureFilter com os metodos init , doFilter e Destroy
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {// Não vai ter nada não vou querer que ele execute nada quando for criado
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException { //vou expor o cabeçalho personalizado fazendo igual esta do filter de autenticação

		HttpServletResponse res = (HttpServletResponse) response;//HttpServletResponse estou recebendoum casting do parametro response
		res.addHeader("access-control-expose-headers", "location");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {// tambem não vai ter nada nesse metodo
	}
}