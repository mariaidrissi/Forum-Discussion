package model;
import model.Message;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
public class Forum extends ActiveRecordBase {

	private String titre;
	private String description;
	private ArrayList<Message> messages;
	private Utilisateur createur;
	
	public Forum(String titre, String description, Utilisateur u) {
        this.messages = new ArrayList<Message>();
        this.titre = titre;
        this.description = description;
        this.createur = u;
    }

    public Forum() {
        this.messages = new ArrayList<Message>();
    }

	
	public ArrayList<Message> getMessages() {
	    return messages;
	}
	public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
	
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Utilisateur getCreateur() {
		return createur;
	}


	public void setCreateur(Utilisateur createur) {
		this.createur = createur;
	}
	
	public Forum(int id) throws SQLException, IOException, ClassNotFoundException {
        Connection conn = JDBCMysql.getConnection();
        String select_query = "select * from `forum` where `id` = '" + id + "';";
        Statement sql = null;
        sql = conn.createStatement();
        ResultSet res = sql.executeQuery(select_query);
        if (res.next()) {
            this.id = res.getInt("id");
            this.titre = res.getString(2);
            this.createur = new Utilisateur(res.getInt(3));
            this.description = res.getString(4);
            _builtFromDB = true;
        }
    }

	public ArrayList<Message> getFilDiscussion(String choix) {
        if ("all".equalsIgnoreCase(choix)) {
            return this.messages;
        }
        //ToDo il faut traiter d'autres choix.
        return null;
    }


	@Override
    protected String _delete() {
        return "DELETE FROM `forum` WHERE (`id` = '" + id + "');";
    }

    @Override
    protected String _insert() {
        return "INSERT INTO `forum` (`titre`, `createur`,`description`) "
                + "VALUES ('" + titre + "', '" + createur.getId() + "',`description` = '"+ description +"');";
    }

    @Override
    protected String _update() {
        return "UPDATE `forum` SET `titre` = '" + titre + "', "
                + "`createur`='" + createur.getId() + "', `description` = '"+description+"'   WHERE (`id` = '" + id + "');";
    }

    public void LoadMessages() {

    }

    public void addMessage() {

    }
    public static List<Forum> FindAll() {
        return null;
    }

	
	/*
	public static Forum findForum(String name) {
		
		Forum f = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("SELECT titre, description, createur FROM forum WHERE titre = ?;");
		
			st.setString(1, name);
			rs = st.executeQuery();
			if(rs.next()) {
				f = new Forum();
				f.setTitre(rs.getString(1));
				f.setDescription(rs.getString(2));
				f.setCreateur(rs.getString(3));
			}
		}catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return f;
	}
	
	public String afficherMessages() {
		String contenu= "<dl>";
		
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
		
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("SELECT contenu, date, userLogin FROM message WHERE forum = ?;");
			st.setString(1, titre);
			rs = st.executeQuery();
			while(rs.next()) {
				contenu += "<dt> By "+rs.getString(3) +" on "+ rs.getTimestamp(2) +"</dt>";
				contenu += "<dd>"+rs.getString(1)+"</dd>";
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		contenu += "</dl>";
		return contenu;
	}
	
	public static Forum ajouterForum(String titre, String description, Utilisateur createur) {
		
		Forum f = null;
		PreparedStatement st = null;
		try {
			
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("INSERT INTO forum (titre,description,createur) VALUES (?,?,?);");
		
			st.setString(1, titre);
			st.setString(2, description);
			st.setString(3, createur.getLogin());
			int i = st.executeUpdate();
			if(i > 0) {
				f = new Forum();
				f.setTitre(titre);
				f.setDescription(description);
				f.setCreateur(createur.getLogin());
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return f;
	}
	*/
	/*
	public static String listerForums() {
		String contenu= "<dl>";
		
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
		
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("SELECT titre, description, createur FROM forum;");
		
			rs = st.executeQuery();
			while(rs.next()) {
				contenu += "<a href=\"/Forum/AfficherForum?name="+rs.getString(1)+"\"> ";
				contenu += "<dt>"+rs.getString(1) +" by "+ rs.getString(3) +"</dt></a>";
				contenu += "<dd>"+rs.getString(2)+"</dd>";
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		contenu += "</dl>";
		return contenu;
		
	}
	
	
	@Override
	public String toString() {
		String contenu = "<div style=\"border: 1px solid black\"> <dt><h1>Forum "+ titre + " (par "+createur+")</h1></dt><dd>"+description+"</dd></div>";
		contenu +="<br>";
		contenu += afficherMessages();
		return contenu;
	}
	*/
}