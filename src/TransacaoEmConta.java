import java.time.LocalDate;
import java.util.Date;

public interface TransacaoEmConta {

    public TipoOperacao getTipoOpercao();
    public double getValor();
    public String getDataString();
    public LocalDate getData();
    public String getDescricao();


}
