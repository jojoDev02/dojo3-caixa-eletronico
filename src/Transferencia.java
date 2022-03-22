import java.time.LocalDate;
import java.util.Date;

public class Transferencia extends Operacao{
    private Conta contaDestino;
    private Conta contaOrigem;

    public Transferencia(TipoOperacao tipoOperacao, double valor, LocalDate data, String descricao, Conta contaDestino, Conta contaOrigem) {
        super(tipoOperacao, valor, data, descricao);
        this.contaDestino = contaDestino;
        this.contaOrigem = contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public Conta getContaOrigem() {
        return contaOrigem;
    }


}
