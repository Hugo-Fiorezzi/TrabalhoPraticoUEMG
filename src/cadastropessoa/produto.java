package cadastropessoa;

public class Produto {
    private Integer id;
    private String nome;
    private double preco;
    private int estoque;
    private PessoaJuridica fornecedor;

    public Produto(String nome, double preco, int estoque, PessoaJuridica fornecedor) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.fornecedor = fornecedor;
        this.id = 0;
    }

    public Produto(){}

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public PessoaJuridica getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(PessoaJuridica fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "Nome: "       + this.nome    + "\n" +
               "Preço: "      + this.preco   + "\n" +
               "Estoque: "    + this.estoque + "\n" +
               "Fornecedor: " + (this.fornecedor != null ? this.fornecedor.getNome() : "");
    }
}