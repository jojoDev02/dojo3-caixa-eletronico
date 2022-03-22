import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class ContaPoupanca extends Conta{
    private LocalDate dataCriacao;

    public ContaPoupanca(Cliente cliente, String senha, Agencia agencia) {
        super(cliente, senha, agencia);
        dataCriacao = agencia.getDataAtual();

    }

    public void rendePoupanca(){

        int dias_passados = (int) ChronoUnit.DAYS.between(dataCriacao,agencia.getDataAtual());

        int mesP= 0;
        for (int i = 0; i < dias_passados; i++){
            if (i % 30 == 0){
                mesP++;
            }
        }

        saldo*=CalculadoraJuros.calculaRendimentoPoupanca(mesP);
    }


}
