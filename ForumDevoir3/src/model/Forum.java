package model;
import model.Message;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
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
		this._builtFromDB = false;
        this.messages = new ArrayList<Message>();
        this.titre = titre;
        this.description = description;
        this.createur = u;
    }

    public Forum() {
    	this._builtFromDB = false;
        this.messages = new ArrayList<Message>();
    }
    
    public Forum(ResultSet rs) {
    	this._builtFromDB = true;
    	try {
    		this.id = rs.getInt(1);
			this.titre = rs.getString(2);
	        this.description = rs.getString(3);
	        this.createur = Utilisateur.FindByID(rs.getInt(4));
    	} catch (SQLException e) {
			e.printStackTrace();
		}
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

	public ArrayList<Message> getFilDiscussion(String choix, String argument, Date argument2) {
        if ("all".equalsIgnoreCase(choix)) {
            return this.messages;
        } else if ("nom".equalsIgnoreCase(choix)) {
        	return findMessagesByNom(argument);
        } else if ("date".equalsIgnoreCase(choix)) {
        	return findMessagesByDate(argument2);
        }
        return null;
    }

	@Override
    protected String _delete() {
        return "DELETE FROM `forum` WHERE (`id` = '" + this.id + "');";
    }

    @Override
    protected String _insert() {
        return "INSERT INTO `forum` (`titre`, `createur`,`description`) "
                + "VALUES ('" + this.titre + "', '" + this.createur.id + "', '"+this.description +"');";
    }

    @Override
    protected String _update() {
        return "UPDATE `forum` SET `titre` = '" + this.titre + "', "
                + "`createur`='" + this.createur.id + "', `description` = '"+this.description+"'   WHERE (`id` = '" + id + "');";
    }

    public ArrayList<Message> LoadMessages() {
    	ArrayList<Message> messages = new ArrayList<Message>();
    	
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
		
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("SELECT id, contenu, datePub, owner, forumPub FROM message WHERE forumPub = ?;");
			st.setInt(1, id);
			rs = st.executeQuery();
			while(rs.next()) {
				messages.add(new Message(rs));
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return messages;
    }

    public void addMessage(String contenu, Utilisateur u) {
    	Message m = new Message();
    	m.setContenu(contenu);
    	m.setOwner(u);
    	m.setForumPub(this);
    	try {
			m.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static ArrayList<Forum> FindAll() {
    	ArrayList<Forum> forums = new ArrayList<Forum>();
    	
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
		
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("SELECT titre, description, createur FROM forum;");
		
			rs = st.executeQuery();
			while(rs.next()) {
				forums.add(new Forum(rs));
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return forums;
    }
    
    public ArrayList<Message> findMessagesByNom(String nom) {
    	ArrayList<Message> messages = new ArrayList<Message>();
    	
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("SELECT m.id, m.contenu, m.owner, m.datePub, m.forumPub FROM message m, utilisateur u WHERE  u.id=m.owner and u.lastname=? and m.forumPub=?;");
			st.setString(1, nom);
			st.setInt(2, this.id);
			rs = st.executeQuery();
			while(rs.next()) {
				messages.add(new Message(rs));
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return messages;
    }
    
    public ArrayList<Message> findMessagesByDate(Date date) {
    	ArrayList<Message> messages = new ArrayList<Message>();
    	
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("SELECT id, contenu, owner, datePub, forumPub FROM message WHERE datePub=? and forumPub=?;");
			st.setDate(1, date);
			st.setInt(2, this.id);
			rs = st.executeQuery();
			while(rs.next()) {
				messages.add(new Message(rs));
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return messages;
    }
    
	public String afficherMessages(ArrayList<Message> messagesFil, int idU) {
		String contenu= "<dl><ul>";
		for (int index = 0; index <messagesFil.size(); index++) {
			contenu += "<li>";
			contenu += "<dt> By "+messagesFil.get(index).getOwner().getLastName() +" "+ messagesFil.get(index).getOwner().getFirstName()+" on "+ messagesFil.get(index).getDatePub() +"</dt>";
			contenu += "<dd>"+messagesFil.get(index).getContenu()+"</dd>";
			if(messagesFil.get(index).getOwner().getId() == idU)
				contenu += "<button onclick=\"editerMessage('"+messagesFil.get(index).getId()+"')\">Editer</button>";
			contenu += "</li>";
		}
		contenu += "</ul></dl>";
		return contenu;
	}
	
	public static void supprimerForum(int idF) {
	    	
	    	try {
	    		Forum f = new Forum(idF);
		    	ArrayList<Message> m = f.LoadMessages();
		    	
		    	for (int index = 0; index < m.size(); index++) {
					m.get(index).delete();
		        }
		    	f.delete();
	    	} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	
    public String toStringListe() {
		return "<div> <dt>Forum "+ titre + " (par "+createur.getLastName()+" "+createur.getFirstName()+")</dt><dd>"+description+"</dd></div>";

    }

	public String afficherForum(int idU, String option, String nom, Date date) {
		String contenu = "<div> <dt><h1>Forum "+ titre + " (par "+createur.getLastName()+" "+createur.getFirstName()+")</h1></dt><dd><h3>"+description+"</h3></dd></div>";
		contenu +="<br>";
		this.messages = LoadMessages();
		ArrayList<Message> messagesFil = getFilDiscussion(option,null,null);
		contenu += afficherMessages(messagesFil, idU);
		return contenu;
	}
	
}