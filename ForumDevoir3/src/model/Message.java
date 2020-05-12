package model;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Message extends ActiveRecordBase {

	private String contenu;
	private java.sql.Date datePub;
	private Utilisateur owner;
	private Forum forumPub;
	
	public Message() {
		_builtFromDB = false;
		java.util.Date utilDate = new java.util.Date();
	    datePub = new java.sql.Date(utilDate.getTime());
	}

	public Message(String contenu, Utilisateur editeur, Date date, Forum forum) {
		this._builtFromDB = false;
        this.contenu = contenu;
        this.datePub = date;
        this.owner = editeur;
        this.forumPub=forum;
    }
	
	public Message(ResultSet res) throws SQLException, IOException, ClassNotFoundException {

		this.id = res.getInt("id");
        this.datePub = res.getDate("datePub");
        this.owner = new Utilisateur(res.getInt("createur"));
        this.contenu = res.getString("contenu");
        this.forumPub = new Forum(res.getInt("forumPub"));
        _builtFromDB = true; 
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
	
	@Override
    protected String _delete() {
        return "DELETE FROM `message` WHERE (`id` = '"+id+"');"; 
    }

    @Override
    protected String _insert() {
        return "INSERT INTO `message` (`contenu`, `owner`, `forumPub`, `datePub`) "
                + "VALUES ('"+contenu+"', '"+owner.getId()+"', '"+forumPub.getId()+"', '"+datePub+"');";
    }

    @Override
    protected String _update() {
        return "update `message` set  `contenu`='"+contenu+"', "
                + "`owner`='"+owner.getId()+"', `forumPub`='"+forumPub.getId() +"', `datePub`='"+datePub
                +"'  WHERE (`id` = '"+id+"');";
    }
    
    public static Message FindbyId(int id){
    	Connection conn = JDBCMysql.getConnection();
        String select_query = "select * from `message` where `id` = '" + id + "';";
        Statement sql = null;
        Message m = null;
        try {
        	sql = conn.createStatement();
            ResultSet res = sql.executeQuery(select_query);
            m = new Message(res);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return m;
    }
    
    public static ArrayList<Message> FindbyForum(int idForum){
    	ArrayList<Message> mess = new ArrayList<Message>();
    	
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
		
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("SELECT * FROM message where forumPub=?;");
			st.setInt(1, idForum);
			rs = st.executeQuery();
			while(rs.next()) {
				mess.add(new Message(rs));
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return mess;
    }
    
    public static ArrayList<Message> FindbyUser(int idUser){
    	ArrayList<Message> mess = new ArrayList<Message>();
    	
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
		
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("SELECT * FROM message where owner=?;");
			st.setInt(1, idUser);
			rs = st.executeQuery();
			while(rs.next()) {
				mess.add(new Message(rs));
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return mess;
    }
}