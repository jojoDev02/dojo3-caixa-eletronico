import java.util.ArrayList;

public class Banco {
    private ArrayList<Agencia> agencias = new ArrayList<>();

    public void adicionaAgencia(Agencia agencia){
        agencias.add(agencia);
    }

    public Agencia localizaAgencia(String codigoAgencia){
        for(Agencia a : agencias){
            if(a.getCodigoAgencia().equals(codigoAgencia)){
                return a;
            }
        }
        return null;
    }


}
