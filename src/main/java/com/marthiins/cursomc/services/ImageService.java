package com.marthiins.cursomc.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.marthiins.cursomc.services.exception.FileException;


@Service
public class ImageService {
	
	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) { //converter MultipartFile uploadedFile para BufferedImage
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if (!"png".equals(ext) && !"jpg".equals(ext)) {
			throw new FileException("Somente imagens PNG e JPG são permitidas");
		}

		try {
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
			if ("png".equals(ext)) {
				img = pngToJpg(img);
			}
			return img;
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

	public BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		return jpgImage;
	}

	public InputStream getInputStream(BufferedImage img, String extension) {//metodo do S3 que recebe o InputStream
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}
		public BufferedImage cropSquare(BufferedImage sourceImg) {// incluir uma função para "cropar"(Recortar) uma imagem para que fique quadrada 

			int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth();
			return Scalr.crop(
				sourceImg, 
				(sourceImg.getWidth()/2) - (min/2), //aqui vou no meio da largura menos a metade do minimo
				(sourceImg.getHeight()/2) - (min/2), //aqui vou no meio da altura menos a metade do minimo
				min, //Quanto para recortar na altura e largura o minimo
				min);		
		}

		public BufferedImage resize(BufferedImage sourceImg, int size) {
			return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);//Biblioteca Scarl
		}
	}


