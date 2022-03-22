public class TesteMain {
    public static void main(String[] args) {
        Banco banco = new Banco();
        Agencia agencia = new Agencia("0001");
        banco.adicionaAgencia(agencia);
        System.out.println(agencia.getDataOriginal());
        System.out.println(agencia.getDataAtual());
        Menu.menuPrincipal(banco,agencia);
    }
}
