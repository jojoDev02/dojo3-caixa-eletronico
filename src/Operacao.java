import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class Operacao implements TransacaoEmConta{
    private TipoOperacao tipoOperacao;
    private double valor;
    private LocalDate data;
    private String descricao;

    public Operacao(TipoOperacao tipoOperacao, double valor, LocalDate data, String descricao) {
        this.tipoOperacao = tipoOperacao;
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
    }

    @Override
    public TipoOperacao getTipoOpercao() {
        return tipoOperacao;
    }

    @Override
    public double getValor() {
        return valor;
    }

    @Override
    public String getDataString() {
        return data.toString();
    }

    @Override
    public LocalDate getData(){
        return data;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return tipoOperacao +
                "\nvalor: " + valor +
                "\ndata: " + data +
                "\ndescricao" + descricao;
    }
}
