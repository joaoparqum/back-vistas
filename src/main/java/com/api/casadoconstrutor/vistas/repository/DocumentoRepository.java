package com.api.casadoconstrutor.vistas.repository;

import com.api.casadoconstrutor.vistas.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findByNomeArquivoContaining(String nomeArquivo);

}
