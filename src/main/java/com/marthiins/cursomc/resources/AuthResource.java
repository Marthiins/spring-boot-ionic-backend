package com.marthiins.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marthiins.cursomc.dto.EmailDTO;
import com.marthiins.cursomc.security.JWTUtil;
import com.marthiins.cursomc.security.UserSS;
import com.marthiins.cursomc.services.AuthService;
import com.marthiins.cursomc.services.UserService;

@RestController
@RequestMapping(value = "/auth")// AndPoint /"auth e /refresh_Token
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)//refresh_Token é um andpoint protegido por autenticação precisa estar logado 
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)//"/forgot" esqueci minha senha
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDTO) {
		service.sendNewPassword(objDTO.getEmail());
		return ResponseEntity.noContent().build();
	}
}
