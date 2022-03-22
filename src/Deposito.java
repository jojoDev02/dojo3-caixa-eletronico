import java.time.LocalDate;
import java.util.Date;

public class Deposito extends Operacao{
    public Deposito(TipoOperacao tipoOperacao, double valor, LocalDate data, String descricao) {
        super(tipoOperacao, valor, data, descricao);
    }
}
