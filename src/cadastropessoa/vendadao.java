package cadastropessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class VendaDAO {

    public boolean inserir(Venda v){
        String sql = "INSERT INTO venda(data_venda, cliente_id) " +
                     "VALUES(?,?)";
        boolean resposta = false;
        try{
            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            LocalDateTime data = v.getDataVenda() != null ? v.getDataVenda() : LocalDateTime.now();
            ps.setTimestamp(1, Timestamp.valueOf(data));
            ps.setInt(2, v.getCliente().getId());
            ps.executeUpdate();
            ResultSet result = ps.getGeneratedKeys();
            result.next();
            v.setId(result.getInt(1));
            if(v.getItens() != null){
                sql = "INSERT INTO item_venda(venda_id, produto_id, quantidade, valor_unitario) " +
                      "VALUES(?,?,?,?)";
                for(ItemVenda item : v.getItens()){
                    ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, v.getId());
                    ps.setInt(2, item.getProduto().getId());
                    ps.setInt(3, item.getQuantidade());
                    ps.setDouble(4, item.getValorUnitario());
                    ps.executeUpdate();
                    ResultSet rsItem = ps.getGeneratedKeys();
                    rsItem.next();
                    item.setId(rsItem.getInt(1));
                }
            }
            resposta = true;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,
                    "Erro ao inserir: " + ex.getMessage());
            resposta = false;
        }
        return resposta;
    }

    public boolean excluir(Venda v){
        String sql = "DELETE FROM venda WHERE id = ?";
        boolean resposta = false;
        try{
            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, v.getId());
            ps.executeUpdate();
            resposta = true;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,
                    "Erro ao deletar: " + ex.getMessage());
            resposta = false;
        }
        return resposta;
    }

    public List<Venda> listar(){
        String sql = "SELECT " +
                     " v.id, v.data_venda, v.cliente_id, " +
                     " pe.nome, pe.endereco, pe.email, " +
                     " pf.cpf " +
                     "FROM venda v " +
                     " INNER JOIN pessoa_fisica pf " +
                     "  ON v.cliente_id = pf.pessoa_id " +
                     " INNER JOIN pessoa pe " +
                     "  ON pf.pessoa_id = pe.id " +
                     "ORDER BY v.id;";
        List<Venda> listaVenda = new ArrayList<>();
        try{
            Connection con = Conexao.getConexao();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                PessoaFisica cliente = new PessoaFisica(
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("email"),
                        rs.getString("cpf")
                );
                cliente.setId(rs.getInt("cliente_id"));
                List<ItemVenda> listaItens = listarItens(con, rs.getInt("id"));
                Venda venda = new Venda(cliente, listaItens);
                venda.setId(rs.getInt("id"));
                Timestamp ts = rs.getTimestamp("data_venda");
                if(ts != null){
                    venda.setDataVenda(ts.toLocalDateTime());
                }
                listaVenda.add(venda);
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,
                    "Erro ao buscar Lista de Vendas.\n" + ex.getMessage());
        }
        return listaVenda;
    }

    private List<ItemVenda> listarItens(Connection con, int vendaId) throws Exception{
        String sql = "SELECT " +
                     " iv.id, iv.quantidade, iv.valor_unitario, " +
                     " pr.id AS produto_id, pr.nome AS produto_nome, pr.preco, pr.estoque, pr.fornecedor_id, " +
                     " pe.nome AS fornecedor_nome, pe.endereco, pe.email, " +
                     " pj.cnpj, pj.nome_fantasia " +
                     "FROM item_venda iv " +
                     " INNER JOIN produto pr " +
                     "  ON iv.produto_id = pr.id " +
                     " INNER JOIN pessoa_juridica pj " +
                     "  ON pr.fornecedor_id = pj.pessoa_id " +
                     " INNER JOIN pessoa pe " +
                     "  ON pj.pessoa_id = pe.id " +
                     "WHERE iv.venda_id = ? " +
                     "ORDER BY iv.id;";
        List<ItemVenda> listaItens = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, vendaId);
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
                    rs.getString("produto_nome"),
                    rs.getDouble("preco"),
                    rs.getInt("estoque"),
                    fornecedor
            );
            produto.setId(rs.getInt("produto_id"));
            ItemVenda item = new ItemVenda(
                    produto,
                    rs.getInt("quantidade"),
                    rs.getDouble("valor_unitario")
            );
            item.setId(rs.getInt("id"));
            listaItens.add(item);
        }
        return listaItens;
    }

}