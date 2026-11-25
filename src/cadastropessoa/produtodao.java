package cadastropessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProdutoDAO {

    public boolean inserir(Produto p){
        String sql = "INSERT INTO produto(nome, preco, estoque, fornecedor_id) " +
                     "VALUES(?,?,?,?)";
        boolean resposta = false;
        try{
            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getNome());
            ps.setDouble(2, p.getPreco());
            ps.setInt(3, p.getEstoque());
            ps.setInt(4, p.getFornecedor().getId());
            ps.executeUpdate();
            ResultSet result = ps.getGeneratedKeys();
            result.next();
            p.setId(result.getInt(1));
            resposta = true;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,
                    "Erro ao inserir: " + ex.getMessage());
            resposta = false;
        }
        return resposta;
    }

    public boolean atualizar(Produto p){
        String sql = "UPDATE produto SET " +
                     "nome = ?, " +
                     "preco = ?, " +
                     "estoque = ?, " +
                     "fornecedor_id = ? WHERE id = ?";
        boolean resposta = false;
        try{
            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setDouble(2, p.getPreco());
            ps.setInt(3, p.getEstoque());
            ps.setInt(4, p.getFornecedor().getId());
            ps.setInt(5, p.getId());
            ps.executeUpdate();
            resposta = true;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,
                    "Erro ao atualizar: " + ex.getMessage());
            resposta = false;
        }
        return resposta;
    }

    public boolean excluir(Produto p){
        String sql = "DELETE FROM produto WHERE id = ?";
        boolean resposta = false;
        try{
            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, p.getId());
            ps.executeUpdate();
            resposta = true;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,
                    "Erro ao deletar: " + ex.getMessage());
            resposta = false;
        }
        return resposta;
    }

    public List<Produto> listar(){
        String sql = "SELECT " +
                     " pr.id, pr.nome, pr.preco, pr.estoque, pr.fornecedor_id, " +
                     " pe.nome AS fornecedor_nome, pe.endereco, pe.email, " +
                     " pj.cnpj, pj.nome_fantasia " +
                     "FROM produto pr " +
                     " INNER JOIN pessoa_juridica pj " +
                     "  ON pr.fornecedor_id = pj.pessoa_id " +
                     " INNER JOIN pessoa pe " +
                     "  ON pj.pessoa_id = pe.id " +
                     "ORDER BY pr.id;";
        List<Produto> listaProduto = new ArrayList<>();
        try{
            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                PessoaJuridica fornecedor = new PessoaJuridica(
                        rs.getString("fornecedor_nome"),
                        rs.getString("endereco"),
                        rs.getString("email"),
                        rs.getString("cnpj"),
                        rs.getString("nome_fantasia")
                );
                fornecedor.setId(rs.getInt("fornecedor_id"));
                Produto produto = new Produto(
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("estoque"),
                        fornecedor
                );
                produto.setId(rs.getInt("id"));
                listaProduto.add(produto);
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,
                    "Erro ao buscar Lista de Produtos.\n" + ex.getMessage());
        }
        return listaProduto;
    }

}