CREATE TABLE tb_documento (
      id BIGSERIAL PRIMARY KEY,
      nomeArquivo VARCHAR(255) NOT NULL,
      tipoArquivo VARCHAR(50),
      tamanhoArquivo BIGINT
);
