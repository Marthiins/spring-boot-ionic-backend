package com.marthiins.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.marthiins.cursomc.domain.Cidade;
import com.marthiins.cursomc.domain.Cliente;
import com.marthiins.cursomc.domain.Endereco;
import com.marthiins.cursomc.domain.enums.Perfil;
import com.marthiins.cursomc.domain.enums.TipoCliente;
import com.marthiins.cursomc.dto.ClienteDTO;
import com.marthiins.cursomc.dto.ClienteNewDTO;
import com.marthiins.cursomc.repositories.ClienteRepository;
import com.marthiins.cursomc.repositories.EnderecoRepository;
import com.marthiins.cursomc.security.UserSS;
import com.marthiins.cursomc.services.exception.AuthorizationException;
import com.marthiins.cursomc.services.exception.DataIntegrityException;
import com.marthiins.cursomc.services.exception.ObjectNotFoundException;


//OK REVISADO

@Service
public class ClienteService { //Classe responsavel por fazer a consulta nos repositorios
	
	@Autowired //para instanciar um repositorio no spring utilizamos a anotação @Autowired
	private ClienteRepository repo; //declarar uma dependencia de um objeto do tipo Repository
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")//chave application.properties
	private String prefix;
	
	public Cliente find(Integer id) { //Operação capaz de buscar a categoria pelo codigo
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Cliente> obj = repo.findById(id); //findOne faz a busca no banco de Dados com base no Id
		/* Esse modelo é para o Sprint a partir da 2.0 */
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	
	     }
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos()); // Para esse metodo funcionar tive que colocar no cliente DTO cli.getEnderecos().add(end);
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId()); //Chamei esse find aqui porque ele já busca o objeto no banco e caso esse Id não exista ele me da uma excessão
		updateData(newObj, obj);
		return repo.save(newObj); //mesmo metodo de inserir, porem quando o id esta nulo ele insere, porem quando não esta ele atualiza
	}
	 
	public void delete (Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
	      throw new DataIntegrityException("Não é possivel excluir porque há pedidos relacionados.");// tenho que receber essa Cliente aqui na camada do ClienteResource
		}

	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest); //Depois Cliente Resource para o metodo end point para pegar a requisição e chamar o metodo do service
	}
	
	//Metodo auxiliar que instancia um cliente atraves do DTO
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), passwordEncoder.encode(objDto.getSenha())); //Converter o numero inteiro para o tipo de cliente usei o TipoCliente.toEnum(objDto.getTipo
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();//Usuario Logado
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
		
	}

	}


