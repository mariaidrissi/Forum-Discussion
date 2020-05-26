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
	
	/**
	 * Constructeur avec en arguments les propriétés de l'objet.
	 * Créé un objet pas encore persistant.
	 * @param titre
	 * @param description
	 * @param u
	 */
	public Forum(String titre, String description, Utilisateur u) {
		this._builtFromDB = false;
        this.messages = new ArrayList<Message>();
        this.titre = titre;
        this.description = description;
        this.createur = u;
    }

	/**
	 * Constructeur sans arguments : initialise la liste des messages du forum. Objet non persistant.
	 */
    public Forum() {
    	this._builtFromDB = false;
        this.messages = new ArrayList<Message>();
    }
    
    /**
     * Constructeur à  partir du ResultSet résultat d'une requête à la base de données.
	 * Le forum est donc persistant.
     * @param rs
     */
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

    /**
     * Constructeur à partir de l'id d'un forum existant.
	 * Forum persistant.
     * @param id
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
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
	
    /**
     * Getter messages du forum
     * @return ArrayList<Message>
     */
	public ArrayList<Message> getMessages() {
	    return messages;
	}
	
	/**
     * Setter messages du forum
     * @param messages
     */
	public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

	/**
     * Getter titre du forum
     * @return String
     */
	public String getTitre() {
		return titre;
	}

	/**
     * Setter titre du forum
     * @param titre
     */
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	/**
     * Getter description du forum
     * @return String
     */
	public String getDescription() {
		return description;
	}

	/**
     * Setter description du forum
     * @param description
     */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
     * Getter createur du forum
     * @return Utilisateur
     */
	public Utilisateur getCreateur() {
		return createur;
	}

	/**
     * Setter createur du forum
     * @param createur
     */
	public void setCreateur(Utilisateur createur) {
		this.createur = createur;
	}
	
	/**
	 * Méthode pour récupérer le fil de discussion du forum courant.
	 * On peut récupérer tous les messages, seulement certains correspondants à un nom de famille donné, ou seulement certains à une certaine date donnée.
	 * Le fil de discussion sera ensuite affiché par {@link #afficherMessages()}.
	 * @param choix : "all", "nom", "date"
	 * @param argument : le nom en question ; utilisé uniquement si choix="nom"
	 * @param argument2 : la date en question ; utilisé uniquement si choix="date"
	 * @return ArrayList<Message>
	 */
	private ArrayList<Message> getFilDiscussion(String choix, String argument, Date argument2) {
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

    /**
     * Méthode qui charge les messages depuis la base de données pour le forum courant.
     * Elle renvoie une liste des messages sous la forme d'objets persistants.
     * @return ArrayList<Message>
     */
    private void LoadMessages() {
    	this.messages = new ArrayList<Message>();
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("SELECT id, contenu, datePub, owner, forumPub FROM message WHERE forumPub = ?;");
			st.setInt(1, id);
			rs = st.executeQuery();
			while(rs.next()) {
				this.messages.add(new Message(rs));
			}
			st.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Méthode qui ajoute un message pour un utilisateur sur le forum courant. Le contenu est donné en paramètres.
     * Elle rend l'objet persistant dans la base de données.
     * @param contenu : contenu du message
     * @param u : utilisateur qui ecrit le message
     */
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
    
    /**
     * Méthode qui trouve et retourne tous les forums existants. Elle retourne la liste des forums sous la forme d'objets persistants.
     * Cette méthode est utilisée pour les administrateurs.
     * @return ArrayList<Forum>
     */
    public static ArrayList<Forum> FindAll() {
    	ArrayList<Forum> forums = new ArrayList<Forum>();
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("SELECT id, titre, description, createur FROM forum;");
			rs = st.executeQuery();
			while(rs.next()) {
				forums.add(new Forum(rs));
			}
			st.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return forums;
    }
    
    /**
     * Cette méthode trouve pour le forum courant les messages de la personne ayant pour nom celui passé en paramètres.
     * Elle est appelée par {@link #getFilDiscussion()}.
     * @param nom
     * @return ArrayList<Message>
     */
    private ArrayList<Message> findMessagesByNom(String nom) {
    	ArrayList<Message> m = new ArrayList<Message>();
    	
    	/*
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
			st.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		*/
    	for (int index = 0; index <this.messages.size(); index++) {
    		if(this.messages.get(index).getOwner().getLastName().equalsIgnoreCase(nom)) {
    			m.add(this.messages.get(index));
    		}
    	}
    	
		return m;
    }
    
    /**
     * Cette méthode trouve pour le forum courant les messages publiés à la date passée en paramètres.
     * Elle est appelée par {@link #getFilDiscussion()}.
     * @param date
     * @return ArrayList<Message>
     */
    private ArrayList<Message> findMessagesByDate(Date date) {
    	ArrayList<Message> m = new ArrayList<Message>();
    	/*
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
			st.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		*/
    	for (int index = 0; index <this.messages.size(); index++) {
    		if(this.messages.get(index).getDatePub().equals(date))
    			m.add(this.messages.get(index));
    	}
		return m;
    }
    
    
    /**
     * Cette méthode retourne une chaîne imprimable des messages donnés en paramètres. Elle est appelée pour faire l'affichage du fil de discussion.
     * On ajoute un bouton cliquable pour éditer le message si il a été écrit par l'utilisateur passé en paramètres.
     * @param messagesFil : messages que l'on veut afficher
     * @param idU :  identificateur de l'utilisateur pour qui on affiche les message pour l'édition
     * @return String
     */
	public String afficherMessages(ArrayList<Message> messagesFil, int idU) {
		String contenu= "<dl><ul>";
		for (int index = 0; index <messagesFil.size(); index++) {
			contenu += "<li>";
			contenu += "<dt> Par "+messagesFil.get(index).getOwner().getLastName() +" "+ messagesFil.get(index).getOwner().getFirstName()+", le "+ messagesFil.get(index).getDatePub() +"</dt>";
			contenu += "<dd>"+messagesFil.get(index).getContenu()+"</dd>";
			if(messagesFil.get(index).getOwner().getId() == idU)
				contenu += "<button class='buttonStyle' onclick=\"editerMessage('"+messagesFil.get(index).getId()+"')\">Editer</button>";
			contenu += "</li>";
		}
		contenu += "</ul></dl>";
		return contenu;
	}
	
	/**
	 * Méthode pour supprimer un forum. On supprime d'abord tous les messages du forum ; puis on supprime les abonnements liés à ce forum.
	 * Au final, on supprime le forum lui-même de la base de données.
	 * @param idF : id du forum
	 */
	public static void supprimerForum(int idF) {
    	try {
    		Forum f = new Forum(idF);
	    	f.LoadMessages();
	    	
	    	for (int index = 0; index < f.messages.size(); index++) {
				f.messages.get(index).delete();
	        }
	    	f.deleteSubscriptions();
	    	f.delete();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * Cette méthode supprime les abonnements pour le forum courant de la base de données. Elle est appelée par {@link #supprimerForum()}.
	 */
	private void deleteSubscriptions() {
		
		PreparedStatement st = null;
		try {
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("DELETE FROM subscriptions WHERE id_forum=?;");
			st.setInt(1, this.id);
			st.execute();
			st.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * Cette méthode trouve les forums qui ont été créés par l'utilisateur courant. Elle renvoie directement la liste sous forme de chaîne pour
     * être affichée par la suite. Elle utilise la méthode {@link #findForumCree()} pour trouver la liste des forums créés par l'utilisateur courant.
     * On ajoute deux formulaires : un pour supprimer le forum, et un pour accéder aux messages de celui-ci. Cette méthode est utilisée pour les 
     * administrateurs.
     * @return String
     */
    public static String getForumsForAdmin() {
    	String contenu = "<ul>";
    	ArrayList<Forum> forums = Forum.FindAll();//findForumCree();
    	for (int index = 0; index < forums.size(); index++) {
            contenu += "<li>";
            contenu += forums.get(index).toStringListe();
            //bouton pour supprimer le forum
            contenu += "<form action='SupprimerForum' method='post'><input type='hidden' name='forum' value='"+forums.get(index).getId()+"'><input class='buttonStyle' type='submit' value='Supprimer'></form>";
            //bouton pour afficher les messages du forum
            contenu += "<form action='AfficherForum' method='post'><input type='hidden' name='forum' value='"+forums.get(index).getId()+"'><input class='buttonStyle' type='submit' value='Accéder au forum'></form>";
            contenu += "</li>";
        }
    	contenu += "</ul>";
    	return contenu;
    }
	
	/**
	 * Variante de la méthode toString qui va afficher tous les forums sous forme de liste.
	 * @return String
	 */
	
    public String toStringListe() {
		return "<div> <dt>Forum "+ titre + " (par "+createur.getLastName()+" "+createur.getFirstName()+")</dt><dd>"+description+"</dd></div>";

    }

    /**
     * Cette méthode affiche le forum courant en entier : il affiche les informations propres à ce forum, puis affiche le fil de discussion souhaité.
     * @param idU : identificateur de l'utilisateur demandant l'affichage.
	 * @param choix : "all", "nom", "date".
	 * @param nom : le nom en question ; utilisé uniquement si choix="nom".
	 * @param date : la date en question ; utilisé uniquement si choix="date".
     * @return String
     */
	public String afficherForum(int idU, String option, String nom, Date date) {
		String contenu = "<div> <dt><h1>Forum "+ titre + " (par "+createur.getLastName()+" "+createur.getFirstName()+")</h1></dt><dd><h3>"+description+"</h3></dd></div>";
		contenu +="<br>";
		LoadMessages();
		ArrayList<Message> messagesFil = getFilDiscussion(option,nom,date);
		contenu += afficherMessages(messagesFil, idU);
		return contenu;
	}
	
}