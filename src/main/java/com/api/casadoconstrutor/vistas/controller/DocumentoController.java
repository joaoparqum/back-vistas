package com.api.casadoconstrutor.vistas.controller;

import com.api.casadoconstrutor.vistas.model.Documento;
import com.api.casadoconstrutor.vistas.service.DocumentoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/vistas")
public class DocumentoController {

    final DocumentoService documentoService;
    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<List<Documento>> uploadFiles(@RequestParam("files[]") MultipartFile[] files) throws IOException {
        List<Documento> documentos = new ArrayList<>();

        if (files.length == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Caso nenhum arquivo seja enviado
        }

        // Processa cada arquivo enviado
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                Documento document = documentoService.uploadFile(file);
                documentos.add(document);
            } else {
                System.out.println("Arquivo vazio recebido: " + file.getOriginalFilename());
            }
        }

        return new ResponseEntity<>(documentos, HttpStatus.CREATED);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException {
        byte[] fileData = documentoService.downloadFile(id);

        // Definir os cabeçalhos da resposta para o download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "document.pdf");

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> viewFile(@PathVariable Long id) throws IOException {

        byte[] fileData = documentoService.downloadFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "document.pdf");

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) throws IOException {
        documentoService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Documento>> getAllDocuments() {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentoService.getAllDocuments());
    }

    @GetMapping("/nomeArquivo/{nomeArquivo}")
    public ResponseEntity<Object> getDocumentoByNome(@PathVariable(value = "nomeArquivo") String nomeArquivo) {
        List<Documento> documento = documentoService.findByNomeArquivo(nomeArquivo);
        if(documento.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado!!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(documento);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getOneDocumento(@PathVariable(value = "id") Long id){
        Optional<Documento> documento = documentoService.findById(id);
        if (!documento.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado!.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(documento.get());
    }


}
