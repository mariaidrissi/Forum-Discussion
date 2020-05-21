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
	
	/**
	 * Constructeur sans arguments : initialise uniquement la date de publication à maintenant.
	 * Créé un objet pas encore persistant.
	 */
	public Message() {
		java.util.Date utilDate = new java.util.Date();
		this.datePub = new java.sql.Date(utilDate.getTime());
	}
	
	/**
	 * Constructeur avec en arguments les propriétés de l'objet.
	 * Créé un objet pas encore persistant.
	 * 
	 * @param contenu
	 * @param editeur
	 * @param date
	 * @param forum
	 */
	public Message(String contenu, Utilisateur editeur, Date date, Forum forum) {
		this._builtFromDB = false;
        this.contenu = contenu;
        this.datePub = date;
        this.owner = editeur;
        this.forumPub=forum;
    }
	
	/**
	 * Constructeur à  partir du ResultSet résultat d'une requête à la base de données.
	 * Le message est donc persistant.
	 * @param res
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Message(ResultSet res) throws SQLException, IOException, ClassNotFoundException {

		this.id = res.getInt("id");
        this.datePub = res.getDate("datePub");
        this.owner = Utilisateur.FindByID(res.getInt("owner"));
        this.contenu = res.getString("contenu");
        this.forumPub = new Forum(res.getInt("forumPub"));
        _builtFromDB = true; 
	}

	/**
	 * Getter contenu
	 * @return String
	 */
	public String getContenu() {
		return contenu;
	}

	/**
	 * Setter contenu
	 * @param contenu
	 */
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	/**
	 * Getter date de publication
	 * @return Date
	 */
	public Date getDatePub() {
		return datePub;
	}

	/**
	 * Setter date de publication
	 * @param date
	 */
	public void setDatePub(Date date) {
		this.datePub = date;
	}

	/**
	 * Getter créateur du message
	 * @return Utilisateur
	 */
	public Utilisateur getOwner() {
		return owner;
	}

	/**
	 * Setter créateur du message
	 * @param owner
	 */
	public void setOwner(Utilisateur owner) {
		this.owner = owner;
	}

	/**
	 * Getter forum de publication
	 * @return Forum
	 */
	public Forum getForumPub() {
		return forumPub;
	}

	/**
	 * Setter forum de publication
	 * @param forumPub
	 */
	public void setForumPub(Forum forumPub) {
		this.forumPub = forumPub;
	}
	
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
    
    /**
     * Cette méthode retrouve un message dans la base de données à partir de son id qui est unique. 
	 * Elle retourne l'objet persistant correspondant, null si il n'existe pas.
     * @param id
     * @return Message
     */
    public static Message FindbyId(int id){
    	Connection conn = JDBCMysql.getConnection();
        String select_query = "select id, contenu, owner, datePub, forumPub from message where id = '" + id + "';";
        Statement sql = null;
        Message m = null;
        try {
        	sql = conn.createStatement();
            ResultSet res = sql.executeQuery(select_query);
            if(res.next())
            	m = new Message(res);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return m;
    }

    /**
     * Cette méthode retourne la liste des messages appartenant au forum dont l'identificateur est passé en paramètres.
     * Elle retourne la liste des messages sous la forme d'objets persistants.
     * @param idForum
     * @return ArrayList<Message>
     */
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
    
    /**
     * Cette méthode retourne la liste des messages écrit par un utilisateur dont l'identificateur est passé en paramètres.
     * Elle retourne la liste des messages sous la forme d'objets persistants.
     * @param idUser
     * @return ArrayList<Message>
     */
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