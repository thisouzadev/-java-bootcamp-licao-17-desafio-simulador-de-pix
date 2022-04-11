package com.trybe.simuladordepix;

import java.io.IOException;

public class ProcessadorDePix {

  private final Servidor servidor;

  public ProcessadorDePix(Servidor servidor) {
    this.servidor = servidor;
  }

  /**
   * Executa a operação do pix. Aqui é implementada a lógica de negócio sem envolver as interações
   * do aplicativo com a pessoa usuária.
   *
   * @param valor Valor em centavos a ser transferido.
   * @param chave Chave Pix do beneficiário da transação.
   *
   * @throws ErroDePix Erro de aplicação, caso ocorra qualquer inconformidade.
   * @throws IOException Caso aconteça algum problema relacionado à comunicação entre o aplicativo e
   *         o servidor na nuvem.
   */
  public void executarPix(int valor, String chave) throws ErroDePix, IOException {
    // TODO: Implementar.
    Conexao conexao = servidor.abrirConexao();
    try {
      verificarErros(valor, chave);
      String mensagemDeRetorno = conexao.enviarPix(valor, chave);
      if (mensagemDeRetorno == "sucesso") {
        return;
      }
      if (mensagemDeRetorno == "saldo_insuficiente") {
        throw new ErroSaldoInsuficiente();
      } else if (mensagemDeRetorno == "chave_pix_nao_encontrada") {
        throw new ErroChaveNaoEncontrada();
      } else {
        throw new ErroInterno();
      }
    } finally {
      conexao.close();
    }
  }

  /**
   * Método do desafio.
   * 
   */
  public void verificarErros(int valorEmCentavos, String chave) throws ErroDePix {
    if (valorEmCentavos <= 0) {
      throw new ErroValorNaoPositivo();
    }
    if (chave.isBlank()) {
      throw new ErroChaveEmBranco();
    }
  }
}
