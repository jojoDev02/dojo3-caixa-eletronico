import java.time.LocalDate;

public class Saque extends Operacao{
    public Saque(TipoOperacao tipoOperacao, double valor, LocalDate data, String descricao) {
        super(tipoOperacao, valor, data, descricao);
    }
}
