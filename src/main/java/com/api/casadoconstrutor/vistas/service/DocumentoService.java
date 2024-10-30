package com.api.casadoconstrutor.vistas.service;

import com.api.casadoconstrutor.vistas.model.Documento;
import com.api.casadoconstrutor.vistas.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentoService {

    private final String uploadDir = "uploads/";

    @Autowired
    DocumentoRepository documentoRepository;

    public Documento uploadFile(MultipartFile file) throws IOException {

        //cria o diretório de upload se não existir
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        //salva o arquivo no diretório
        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        //arquiva no banco
        Documento documento = new Documento();
        documento.setNomeArquivo(fileName);
        documento.setTipoArquivo(file.getContentType());
        documento.setTamanhoArquivo(file.getSize());

        return documentoRepository.save(documento);
    }

    public byte[] downloadFile(UUID id) throws IOException {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado!!"));

        Path filePath = Paths.get(uploadDir, documento.getNomeArquivo());
        return Files.readAllBytes(filePath);
    }

    public void deleteFile(UUID id) throws IOException {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado!!"));

        Path filePath = Paths.get(uploadDir, documento.getNomeArquivo());
        Files.deleteIfExists(filePath);

        documentoRepository.delete(documento);
    }

    public List<Documento> getAllDocuments() {
        return documentoRepository.findAll();
    }

    public List<Documento> findByNomeArquivo(String nomeArquivo) {
        return documentoRepository.findByNomeArquivoContaining(nomeArquivo);
    }

}
