import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;

public class EntradaDados {

    public static void lerDadosConta(Agencia agencia, String tipoConta){
        Scanner sc = new Scanner(System.in);

        System.out.println("Cpf:");
        String cpf = sc.nextLine();
        if(agencia.localizaCliente(cpf) == null){
            System.out.println("Nome:");
            String nome = sc.nextLine();
            System.out.println("Data de Nascimento: ");
            String dataNasc = sc.nextLine();
            System.out.println("Email:");
            String email = sc.nextLine();
            System.out.println("Telefone:");
            String telefone = sc.nextLine();

            agencia.cadastraCliente(nome,cpf,dataNasc,email,telefone);
        }


        Conta conta = null;
        try{
            if(tipoConta.equals("cc")){
                conta = agencia.criaContaCorrente(agencia.localizaCliente(cpf),lerDadosSenha());
            }else{
                conta = agencia.criaContaPoupanca(agencia.localizaCliente(cpf),lerDadosSenha());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("É conta salário?(S/N)");
        String resp = sc.nextLine().toUpperCase();

        if(resp.equals("S")){
            System.out.println("Valor Salário:");
            double valor = sc.nextDouble();
            sc.nextLine();
            System.out.println("Dia do pagamento:");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataPag = LocalDate.parse(sc.nextLine(), dtf);

           conta.setDataPagamentoSalario(dataPag);
           conta.setValorSalario(valor);

        }




    }

    private static String lerDadosSenha(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Cadastre uma senha de 4 digítos:");
        String senha = sc.nextLine();
        if(senha.length() != 4){
            throw new IllegalArgumentException("senha com mais de 4 digítos");
        }else
            return senha;
    }

    public static void lerDadosCadastroPix(Conta conta){
        Scanner sc = new Scanner(System.in);
        System.out.println("Qual chave quer cadastrar?");
        System.out.println("(1)Cpf\n(2)Telefone\n(3)Email\n(4)Chave Aleatória");
        int op = sc.nextInt();

        Cliente cliente = conta.getCliente();

       try {
           switch (op){
               case 1: conta.cadastraChavePix(cliente.getCpf());
                   break;
               case 2: conta.cadastraChavePix(cliente.getTelefone());
                   break;
               case 3: conta.cadastraChavePix(cliente.getEmail());
                   break;
               default: conta.cadastraChavePix(Gerador.geraChaveAleatoriaPix());

                   //tratar erros e verificar dados no metodo que foi chamado
                   //verificar se a chave já está cadastrada em outra conta do cliente
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public static void lerDadosRemoverPix(Conta conta)  {
        Scanner sc = new Scanner(System.in);
        System.out.println("Qual chave quer remover?");
        System.out.println("(1)Cpf\n(2)Telefone\n(3)Email\n(4)Chave Aleatória");
        int op = sc.nextInt();

        Cliente cliente = conta.getCliente();

       try {
           switch (op){
               case 1: conta.removerChavePix(cliente.getCpf());
                   break;
               case 2:conta.removerChavePix(cliente.getTelefone());
                   break;
               case 3:conta.removerChavePix(cliente.getEmail());
                   break;
               default:  conta.removerChavePix(conta.chavePix);
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public static void lerDadosPix(Conta conta){
        Scanner sc = new Scanner(System.in);
        System.out.println("Valor: ");
        double valor = sc.nextDouble();
        sc.nextLine();
        System.out.println("Chave:");
        String chave = sc.nextLine();
        System.out.println("Descrição:");
        String descricao = sc.nextLine();

        conta.Pix(chave,valor,descricao);
        // ta bugado
    }

    public static void lerDadosSaque(Conta conta){
        Scanner sc = new Scanner(System.in);
        System.out.println("Valor: ");
        double valor = sc.nextDouble();
        System.out.println("Descriçao:");
        String descriçao = sc.nextLine();
        System.out.println("Senha:");
        String senha = sc.nextLine();
        try {
            conta.sacar(valor,senha,descriçao);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void lerDadosDeposito(Conta conta){
        Scanner sc = new Scanner(System.in);
        System.out.println("Valor:");
        double valor = sc.nextDouble();
        System.out.println("Descrição:");
        String descricao = sc.nextLine();
        System.out.println("Senha:");
        String senha = sc.nextLine();

        conta.depositar(senha,valor,descricao);


    }

    public static void lerDadosPagamento(Conta conta) throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Código de barras: ");
        String codigo = sc.nextLine();
        if(codigo.length() != 48){
            throw new IllegalArgumentException("O código de barras não tem 48 digítos");
        }
        System.out.println("Data Vencimento: ");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataVenc = LocalDate.parse(sc.nextLine(), dtf);

        System.out.println("Valor:");
        double valor = sc.nextDouble();
        System.out.println("Descrição: ");
        String descricao = sc.nextLine();

        conta.pagar(codigo,valor,dataVenc,descricao);
    }

    public static void lerDadosChequeEspecial(ContaCorrente conta){
        Scanner sc = new Scanner(System.in);
        System.out.println("Deseja usar o limite do cheque especial?(S/N)");
        String resp = sc.nextLine().toUpperCase();
        if(resp.equals("S")){
            conta.usaLimiteChequeEspecial();
        }
    }

    public static void lerDadosExtrato(Conta conta){
        Scanner sc = new Scanner(System.in);
        System.out.println("data min:");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataMin= LocalDate.parse(sc.nextLine(), dtf);
        System.out.println("Data max:");
        LocalDate dataMax= LocalDate.parse(sc.nextLine(), dtf);

        conta.geraExtrato(dataMin, dataMax);
    }

    public static void lerDadosTransferencia(Banco banco,Conta conta){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ag:");
        String numAg = sc.nextLine();
        System.out.println("Conta:");
        String numConta = sc.nextLine();
        System.out.println("Valor:");
        double valor = sc.nextDouble();

        conta.transferir(banco,numAg,numConta,valor,"Transferência");

    }


}
