import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.DAYS;

public class CalculadoraJuros {
    private static double taxaJurosAtraso = 0.1/100;
    private static double taxaJurosRendimento = 0.3/100;

    public static double calculaMultaAtraso(LocalDate dataPagamento, LocalDate dataVenc, double valor){
        int qtdDiaAtraso = calculaDiasAtraso(dataPagamento,dataVenc);
        return  (valor *taxaJurosAtraso) * qtdDiaAtraso;
    }

    private static int calculaDiasAtraso(LocalDate dataPagamento,LocalDate dataVenc){

        return (int) ChronoUnit.DAYS.between(dataVenc, dataPagamento);

    }

    public static double calculaRendimentoPoupanca(int mesP){
        return Math.pow(1.003, mesP);
    }
}
