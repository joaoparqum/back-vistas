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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vistas")
public class DocumentoController {

    final DocumentoService documentoService;
    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Documento> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Documento document = documentoService.uploadFile(file);
        return new ResponseEntity<>(document, HttpStatus.CREATED);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable UUID id) throws IOException {
        byte[] fileData = documentoService.downloadFile(id);

        // Definir os cabeçalhos da resposta para o download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "document.pdf");

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable UUID id) throws IOException {
        documentoService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Documento>> getAllDocuments() {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentoService.getAllDocuments());
    }
}
