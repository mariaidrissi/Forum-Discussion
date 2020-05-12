package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Message extends ActiveRecordBase {

	private String contenu;
	private Date datePub;
	private Utilisateur owner;
	private Forum forumPub;
	
	public Message() {
		_builtFromDB = false;
		this.datePub = new Date();
	}
	
	public Message(String contenu, Utilisateur editeur) {
        this.contenu = contenu;
        this.datePub = new Date();
        this.owner = editeur;
    }


	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public Date getDatePub() {
		return datePub;
	}

	public void setDatePub(Date date) {
		this.datePub = date;
	}

	public Utilisateur getOwner() {
		return owner;
	}

	public void setOwner(Utilisateur owner) {
		this.owner = owner;
	}

	public Forum getForumPub() {
		return forumPub;
	}

	public void setForumPub(Forum forumPub) {
		this.forumPub = forumPub;
	}
	/*
	public static Message ecrireMessage(String contenu,Timestamp date, Utilisateur login, Forum forum) {
		
		counter = Message.getLastId();
		counter += 1;
		Message m = null;
		PreparedStatement st = null;
		try {
		
			Connection db = JDBCMysql.getConnection();

			st = db.prepareStatement("INSERT INTO message (id,contenu,date,userLogin,forum) VALUES (?,?,?,?,?);");
		
			st.setInt(1, counter);
			st.setString(2, contenu);
			st.setTimestamp(3, date);
			st.setString(4,login.getLogin());
			st.setString(5, forum.getTitre());
			int i = st.executeUpdate();
			if(i > 0) {
				m = new Message();
				m.setContenu(contenu);
				m.setDatePub(date);
				m.setForumPub(forum);
				m.setNumero(counter);
				m.setOwner(login);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return m;
	}
	*/
	
	/*
	public static int getLastId() {
		
		int compteur=0;
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
		
			Connection db = JDBCMysql.getConnection();

			st = db.prepareStatement("SELECT MAX(id) as max FROM message;");

			rs = st.executeQuery();
			if(rs.next()) {
				compteur = rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return compteur;
	}
	*/
	
	@Override
    protected String _delete() {
        return "DELETE FROM `message` WHERE (`id` = '"+id+"');"; 
    }

    @Override
    protected String _insert() {
        return "INSERT INTO `message` (`contenu`, `owner`, `forumPub`) "
                + "VALUES ('"+contenu+"', '"+owner.getId()+"', '"+forumPub.getId()+"');";
    }

    @Override
    protected String _update() {
        return "update `message` set  `contenu`='"+contenu+"', "
                + "`owner`='"+owner.getId()+"', `forumPub`='"+forumPub.getId()
                +"'  WHERE (`id` = '"+id+"');";
    }
    
    public static Message FindbyId(int id){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static ArrayList<Message> FindbyForum(int idForum){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static ArrayList<Message> FindbyUser(int idUser){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


	
}