import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Agencia {
    private String codigoAgencia;
    private ArrayList<Cliente> clienteList = new ArrayList<>();
    private ArrayList<Conta> contaList = new ArrayList<>();
    private ArrayList<Conta> contaChaveList = new ArrayList<>();
    private LocalDate dataOriginal = LocalDate.now();
    private LocalDate dataAtual = LocalDate.now();

    public Agencia(String codigoAgencia) {
        this.codigoAgencia = codigoAgencia;
    }

    public void avancaDias(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Número de dias avançados: ");
        int days = sc.nextInt();
        this.dataAtual = this.dataAtual.plusDays(days);
        atualizaSaldoCp();
        atualizaSaldoSalario();


    }

    public  void atualizaSaldoCp(){
        for(Conta c: getContas()){
            if(c instanceof ContaPoupanca){
                ((ContaPoupanca) c).rendePoupanca();
            }
        }
    }

    public void atualizaSaldoSalario(){
        for(Conta c: getContas()){
            if(c.getDataPagamentoSalario() != null){
                c.recebeSalario();
            }
        }
    }

    public Conta localizaConta(String numeroConta){
      for (Conta c: contaList){
          if(c.getNumeroConta().equals(numeroConta)){
              return c;
          }
      }
      return null;
    }

    public Cliente localizaCliente(String cpf){
        for(Cliente c: clienteList){
            if(c.getCpf().equals(cpf)){
                return c;
            }
        }
        return null;
    }

    public void cadastraCliente(String nome, String cpf, String dataNascimento, String email, String telefone){
        Cliente cliente = new Cliente(nome, cpf, dataNascimento, email, telefone);
        clienteList.add(cliente);
    }

    public ContaCorrente criaContaCorrente(Cliente cliente, String senha) throws Exception {
       if(cliente.getContaList().size() == 0){
           ContaCorrente cc = new ContaCorrente(cliente, senha, this);
           contaList.add(cc);
           cliente.getContaList().add(cc);
           System.out.println("Número da Conta: " + cc.getNumeroConta());
           return cc;

       }else{
           for(Conta c : contaList){
               if(c instanceof ContaCorrente){
                   throw new Exception("Cliente já possui uma Conta Corrente");
               }
           }

           ContaCorrente cc = new ContaCorrente(cliente, senha, this);
           contaList.add(cc);
           cliente.getContaList().add(cc);
           System.out.println("Número da Conta: " + cc.getNumeroConta());
           return cc;
       }

    }

    public ContaPoupanca criaContaPoupanca(Cliente cliente, String senha) throws Exception {
       if(cliente.getContaList().size() == 0){
           ContaPoupanca cp = new ContaPoupanca(cliente, senha, this);
           contaList.add(cp);
           cliente.getContaList().add(cp);
           System.out.println("Número da Conta: " + cp.getNumeroConta());
           return cp;
       }else {
           for (Conta c : contaList) {
               if (c instanceof ContaPoupanca) {
                   throw new Exception("Cliente já possui uma Conta Poupança");
               }
           }

           ContaPoupanca cp = new ContaPoupanca(cliente, senha, this);
           contaList.add(cp);
           cliente.getContaList().add(cp);
           System.out.println("Número da Conta: " + cp.getNumeroConta());
           return cp;
       }
    }

    public String getCodigoAgencia() {
        return codigoAgencia;
    }

    public ArrayList<Cliente> getClienteList() {
        return clienteList;
    }

    public ArrayList<Conta> getContas() {
        return contaList;
    }

    public ArrayList<Conta> getContaChaveList() {
        return contaChaveList;
    }

    public LocalDate getDataAtual() {
        return dataAtual;
    }

    public LocalDate getDataOriginal() {
        return dataOriginal;
    }
}
