public final class ContaCorrente extends Conta {
    private double limiteChequeEspecial;
    private boolean isChequeUsado;

    public ContaCorrente(Cliente cliente, String senha, Agencia agencia) {
        super(cliente, senha, agencia);
        this.limiteChequeEspecial = 3000;
        this.isChequeUsado = false;
    }

    public void usaLimiteChequeEspecial(){
        isChequeUsado = true;
        saldo+=limiteChequeEspecial;
        Deposito chequeEspecial = new Deposito(TipoOperacao.DEPOSITO_RECEBIDO,limiteChequeEspecial,agencia.getDataAtual(),"Cheque Especial");
        historicoTransacoes.add(chequeEspecial);
    }

}
