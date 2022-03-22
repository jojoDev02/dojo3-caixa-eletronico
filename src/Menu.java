import java.text.ParseException;
import java.util.Scanner;

public class Menu {

    public static void menuPrincipal(Banco banco,Agencia agencia) {
        while (true){
            Scanner sc = new Scanner(System.in);
            System.out.println("---- Menu Principal -----");
            System.out.println("(1)Menu Cliente\n(2)Nova Conta\n(3)Avançar dias\n(4)Encerrar");
            int op = sc.nextInt();
           try {
               switch (op){
                   case 1: Login(agencia,banco);
                       break;
                   case 2: menuNovaConta(agencia);
                       break;
                   case 3: agencia.avancaDias();
                        break;
                   default: return;
               }
           }catch (Exception e){
               e.printStackTrace();
           }
        }
    }

    private static void menuNovaConta(Agencia agencia){
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("--- Abrir Conta ----");
            System.out.println("(1)Abrir Conta Corrente\n(2)Abrir Conta Poupança\n(3)Voltar");
            int op = sc.nextInt();

            switch (op){
                case 1: EntradaDados.lerDadosConta(agencia,"cc");
                    break;
                case 2: EntradaDados.lerDadosConta(agencia,"cp");
                    break;
                default:
                    return;
            }
        }

    }

    private static void Login(Agencia agencia, Banco banco) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("---- Login ----");
        System.out.println("Digite o número da conta:");
        String numeroConta = sc.nextLine();
        System.out.println("Digite sua senha de 4 digítos:");
        String senha = sc.nextLine();

        Conta conta = agencia.localizaConta(numeroConta);
        if(conta != null){
            if(conta.isSenha(senha)){
                Cliente cliente = agencia.localizaCliente(conta.cliente.getCpf());
                System.out.println("Acesso Liberado!\n");
                menuCliente(agencia,conta,banco);
            }else
                throw new Exception("Senha inválida.");
        }else
            throw new Exception("Conta não encontrada.");
    }

    private static void menuCliente(Agencia agencia, Conta conta, Banco banco){
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("----- Menu Cliente -----");
            System.out.println("(1)Sacar\n(2)Depositar\n(3)Pagar\n(4)Transferir\n(5)Área Pix\n(6)Extrato\n(7)Voltar");

            int op = sc.nextInt();

           try {
               switch (op){
                   case 1: EntradaDados.lerDadosSaque(conta);
                       break;
                   case 2: EntradaDados.lerDadosDeposito(conta);
                       break;
                   case 3: EntradaDados.lerDadosPagamento(conta);
                       break;
                   case 4:EntradaDados.lerDadosTransferencia(banco,conta);
                       break;
                   case 5: menuPix(conta);
                       break;
                   case 6: EntradaDados.lerDadosExtrato(conta);
                   default: return;
               }
           }catch (ParseException e){
               e.printStackTrace();
           }
        }

    }

    private static void menuPix(Conta conta){
        Scanner sc = new Scanner(System.in);
       while (true){

           System.out.println("---- Área Pix ----");
           System.out.println("(1)Cadastrar Chave\n(2)Remover Chave\n(3)Transferir\n(4)Voltar");
           int op = sc.nextInt();

           switch (op){
               case 1: EntradaDados.lerDadosCadastroPix(conta);
                   break;
               case 2: EntradaDados.lerDadosRemoverPix(conta);
                   break;
               case 3: EntradaDados.lerDadosPix(conta);
                   break;
               default:
                   return;
           }
       }
    }


}
