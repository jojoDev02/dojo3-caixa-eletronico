import java.util.Random;

public class Gerador {


    public static String geraNumeroConta(){
        Random numero = new Random();
        return String.valueOf(1 + numero.nextLong(999999) +"-"+ numero.nextInt(9));
    }

    public static String geraChaveAleatoriaPix(){
        int tam = 32;
        String alfanumerico = "0123456789"+"abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(tam);

        for (int i = 0; i < tam; i++) {
            int indice = (int) (alfanumerico.length()*Math.random());
            sb.append(alfanumerico.charAt(indice));
        }

        return sb.toString();
    }


}
