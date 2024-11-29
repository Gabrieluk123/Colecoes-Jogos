/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Colecao;

/**
 *
 * @author lefoly
 */
public class ColecaoDaoJDBC implements InterfaceDao<Colecao> {

    private final Connection conn;

    public ColecaoDaoJDBC() throws Exception {
        this.conn = ConnFactory.getConnection();
    }

    @Override
     public void incluir(Colecao entidade) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO jogos (nome, genero, plataforma, lancamento, desenvolvedora, localImagem) VALUES(?, ?, ?, ?, ?,?)");
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getGenero());
            ps.setString(3, entidade.getPlataforma());
            ps.setString(4, entidade.getLancamento());
            ps.setString(5, entidade.getDesenvolvedora());
            ps.setString(6, entidade.getLocalImagem());
            ps.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

   @Override
    public void editar(Colecao entidade) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE jogos SET nome=?, genero=?, plataforma=?, lancamento=?, desenvolvedora=?,localImagem=?, possuir=? WHERE id=?");
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getGenero());
            ps.setString(3, entidade.getPlataforma());
            ps.setString(4, entidade.getLancamento());
            ps.setString(5, entidade.getDesenvolvedora());
            ps.setString(6, entidade.getLocalImagem());
            ps.setBoolean(7, entidade.isPossuir());
            ps.setInt(8, entidade.getId());
            ps.executeUpdate();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void excluir(Colecao entidade) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM jogos WHERE id=?");
            ps.setInt(1, entidade.getId());
            ps.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public Colecao pesquisarPorId(int id) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM jogos");
            ResultSet rs = ps.executeQuery();
            ArrayList<Colecao> lista = new ArrayList<>();

            while (rs.next()) {
                Colecao j = new Colecao();
                j.setId(rs.getInt("id"));
                j.setNome(rs.getString("nome"));
                j.setGenero(rs.getString("genero"));
                j.setPlataforma(rs.getString("plataforma"));
                j.setLancamento(rs.getString("lancamento"));
                j.setDesenvolvedora(rs.getString("desenvolvedora"));
                j.setLocalImagem(rs.getString("localImagem"));
                j.setPossuir(true);
                lista.add(j);
            }

            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId() == id) {
                    return lista.get(i);
                }
            }

            return null;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

   @Override
public List<Colecao> listar(String param) throws Exception {
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
        if (param.equals("")) {
            ps = conn.prepareStatement("SELECT * FROM jogos");
        } else {
            ps = conn.prepareStatement("SELECT * FROM jogos WHERE nome LIKE ?");
            String likeParam = "%" + param + "%";
            ps.setString(1, likeParam);
        }
        rs = ps.executeQuery();
        List<Colecao> lista = new ArrayList<>();
        while (rs.next()) {
            Colecao j = new Colecao();
            j.setId(rs.getInt("id"));
            j.setNome(rs.getString("nome"));
            j.setGenero(rs.getString("genero"));
            j.setPlataforma(rs.getString("plataforma"));
            j.setLancamento(rs.getString("lancamento"));
            j.setDesenvolvedora(rs.getString("desenvolvedora"));
            j.setLocalImagem(rs.getString("localImagem"));
            j.setPossuir(rs.getBoolean("possuir"));
            lista.add(j);
        }
        return lista;
    } finally {
        if (rs != null) {
            rs.close();
        }
    }
}

}

 
