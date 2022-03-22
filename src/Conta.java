import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public abstract class Conta{
    protected Cliente cliente;
    protected Agencia agencia;
    protected String numeroConta;
    protected String senha;
    protected double saldo;
    protected String chavePix;
    protected ArrayList<Operacao> historicoTransacoes = new ArrayList<>();
    protected LocalDate dataPagamentoSalario;
    protected double valorSalario;

    public Conta(Cliente cliente, String senha, Agencia agencia) {
        this.cliente = cliente;
        this.agencia = agencia;
        this.numeroConta = Gerador.geraNumeroConta();
        this.senha = senha;
    }


    public void sacar(Double valor, String senha, String descricao) throws Exception {
        if(saldo < valor) {
            System.out.println("Saldo insuficiente. Operação cancelada.");
            if (this instanceof ContaCorrente) {
                EntradaDados.lerDadosChequeEspecial((ContaCorrente) this);
            }
        }else {
            saldo-= valor;
            Saque saque = new Saque(TipoOperacao.SAQUE,valor,agencia.getDataAtual(),descricao);
            historicoTransacoes.add(saque);
        }

    }

    public void depositar(String senha,double valor, String descricao){
            saldo+= valor;
            Deposito deposito = new Deposito(TipoOperacao.DEPOSITO_RECEBIDO, valor, agencia.getDataAtual(), descricao);
            historicoTransacoes.add(deposito);

    }

    public void transferir(Banco banco,String codigoAg,String numeroContaDestino, Double valor, String descricao){
        Agencia ag = banco.localizaAgencia(codigoAg);
        Conta contaDestino = ag.localizaConta(numeroContaDestino);
        if(ag != null && contaDestino != null){
            contaDestino.saldo += valor;
            this.saldo-= valor;
            Transferencia transferenciaEnviada= new Transferencia(TipoOperacao.TRANSFERENCIA_ENVIADA, valor, agencia.getDataAtual(), descricao,contaDestino,this);
            this.historicoTransacoes.add(transferenciaEnviada);
            Transferencia transferenciaRecebida = new Transferencia(TipoOperacao.TRANSFERENCIA_RECEBIDA,valor,agencia.getDataAtual(),descricao,contaDestino,this);
            contaDestino.historicoTransacoes.add(transferenciaRecebida);
        }else
            throw new IllegalArgumentException("Conta não encontrada.");
    }

    public void pagar(String codigoDeBarras, double valor, LocalDate dataVenc, String descricao){
        LocalDate dataPagamento = agencia.getDataAtual();
        double multaAtraso;
        if(dataPagamento.isAfter(dataVenc)){
            multaAtraso = CalculadoraJuros.calculaMultaAtraso(dataPagamento,dataVenc,valor);
            valor += multaAtraso;
        }else {
            multaAtraso = 0;
        }
        if(saldo < valor){
            System.out.println("Saldo insuficiente. Operação cancelada.");
            if(this instanceof ContaCorrente){
                EntradaDados.lerDadosChequeEspecial((ContaCorrente) this);
            }

        }else {
            saldo -= valor;
            Pagamento pg = new Pagamento(TipoOperacao.PAGAMENTO_EFETUADO,valor,dataPagamento,descricao,codigoDeBarras,multaAtraso);
            historicoTransacoes.add(pg);
        }


    }

    public void cadastraChavePix(String chave) throws Exception {

        for(Conta c : cliente.getContaList()){
            if(c.getChavePix() != null){
                if(c.getChavePix().equals(chave)){
                    throw new Exception("Chave já cadastrada em outra conta");
                }
            }
        }
        chavePix = chave;
        agencia.getContaChaveList().add(this);
        System.out.println("Chave cadastrada!");
    }

    public void removerChavePix(String chave) throws Exception {
        if(chavePix.equals(chave)){
            chavePix = null;
        }else
            throw new Exception("Chave não localizada.");
    }

    public void Pix(String chave, double valor, String descricao) {
        for(Conta c : agencia.getContaChaveList()){
            if(c.getChavePix().equals(chave)){
                saldo -= valor;
                c.saldo += valor;

                Transferencia transferenciaPix = new Transferencia(TipoOperacao.TRANSFERENCIA_ENVIADA,valor,agencia.getDataAtual(),descricao,c,this);
                Transferencia transferenciaPix2 = new Transferencia(TipoOperacao.TRANSFERENCIA_RECEBIDA,valor,agencia.getDataAtual(),descricao,c,this);
                historicoTransacoes.add(transferenciaPix);
                c.historicoTransacoes.add(transferenciaPix2);
                return;
            }
        }

        throw new IllegalArgumentException("Chave não encontrada.");
    }

    public void geraExtrato(LocalDate dataMin, LocalDate dataMax){
        System.out.println("\t\tEXTRADO BANCÁRIO");
        System.out.println("----------------------");
        System.out.printf("Cliente: %s\nAg: %s\nConta: %s\n",cliente.getNome(),getAgencia().getCodigoAgencia(),getNumeroConta());
        System.out.println("----------------------");
        System.out.print("\t\tData\t\t\tTipo\t\t\t\t\t\tValor\n");


        for (Operacao op: historicoTransacoes){
            LocalDate dataOp = op.getData();
            if(dataOp.equals(dataMin) || dataOp.isAfter(dataMin) && dataOp.equals(dataMax) || dataOp.isBefore(dataMax)){
                System.out.printf("%d\t\t%s\t\t%s\t\t%.2f\n%s\n",historicoTransacoes.indexOf(op),op.getDataString(),op.getTipoOpercao(),op.getValor(),op.getDescricao());
            }
        }
        System.out.println("Saldo atual:"+ getSaldo());
    }

    public void recebeSalario(){
        int dias_passados = (int) ChronoUnit.DAYS.between(getDataPagamentoSalario(),agencia.getDataAtual());
        for (int i = 0; i < dias_passados; i++){
            if (i % 30 == 0){
                saldo+= getValorSalario();
                Deposito salario = new Deposito(TipoOperacao.DEPOSITO_RECEBIDO,getValorSalario(),agencia.getDataAtual(),"Salário");
                historicoTransacoes.add(salario);
            }
        }
    }

    public boolean isSenha(String senha){
        return senha.equals(getSenha());
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public String getSenha() {
        return senha;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getChavePix() {
        return chavePix;
    }

    public LocalDate getDataPagamentoSalario() {
        return dataPagamentoSalario;
    }

    public double getValorSalario() {
        return valorSalario;
    }

    public void setDataPagamentoSalario(LocalDate dataPagamentoSalario) {
        this.dataPagamentoSalario = dataPagamentoSalario;
    }

    public void setValorSalario(double valorSalario) {
        this.valorSalario = valorSalario;
    }
}
