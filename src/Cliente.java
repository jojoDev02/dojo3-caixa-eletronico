import java.util.ArrayList;

public class Cliente {
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String email;
    private String telefone;
    private ArrayList<Conta> contaList = new ArrayList<>();

    public Cliente(String nome, String cpf, String dataNascimento, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public ArrayList<Conta> getContaList() {
        return contaList;
    }
}
