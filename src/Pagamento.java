import java.time.LocalDate;

public class Pagamento extends Operacao{

    private String codigoBarras;
    private double multa;

    public Pagamento(TipoOperacao tipoOperacao, double valor, LocalDate data, String descricao, String codigoBarras, double multa) {
        super(tipoOperacao, valor, data, descricao);
        this.codigoBarras = codigoBarras;
        this.multa = multa;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public double getMulta() {
        return multa;
    }

    @Override
    public String toString() {
        return "Pagamento\n" +
                "codigoBarras" + codigoBarras+
                "\nmulta" + multa +
                "Valor total:" + getValor();
    }
}
