package model;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

import javax.management.relation.Role;

public class Utilisateur extends ActiveRecordBase {

	private String lastName;
	private String firstName;
	private String login;
	private String gender;
	private String pwd;
	private Role role = Role.Other;
	private static String _query = "select * from utilisateur"; // for findAll static Method
	private ArrayList<Forum> forumSubscriptions;

    private enum Role {
        Other, Admin
    };

    public Utilisateur() {
    	this.forumSubscriptions = new ArrayList<Forum>();
        _builtFromDB = false;
    }

    /**
     * Constructeur avec en arguments les propriétés de l'objet.
     * Créé un objet pas encore persistant.
     * 
     * @param lastName
     * @param firstName
     * @param login
     * @param gender (man, woman, undefined for anonymous)
     * @param pwd
     */
    public Utilisateur(String lastName, String firstName, String login, String gender, String pwd) {

        this.lastName = lastName;
        this.firstName = firstName;
        this.login = login;
        this.gender = gender;
        this.pwd = pwd;
        this.forumSubscriptions = new ArrayList<Forum>();
        _builtFromDB = false;
    }

    /**
     * Constructeur à  partir du ResultSet résultat d'une requête à la base de données.
     * Cet utilisateur est donc persistant.
     * 
     * @param res
     * @throws SQLException
     */
    public Utilisateur(ResultSet res) throws SQLException {
        this.id = res.getInt("id");
        this.firstName = res.getString("firstname");
        this.lastName = res.getString("lastname");
        this.login = res.getString("login");
        this.pwd=res.getString("pwd");
        this.gender = res.getString("gender");
        this.role = Role.values()[res.getBoolean("admin") ? 1 : 0];
        _builtFromDB = true;
    }

    /**
     * Constructeur à partir de l'id d'un utilisateur existant.
     * Utilisateur persistant.
     * 
     * @param id
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Utilisateur(int id) throws IOException, ClassNotFoundException, SQLException {
        Connection conn = JDBCMysql.getConnection();
        String select_query = "select * from `utilisateur` where `id` = '" + id + "';";
        Statement sql = null;
        sql = conn.createStatement();
        ResultSet res = sql.executeQuery(select_query);
        if (res.next()) {
            this.id = res.getInt("id");
            this.firstName = res.getString("firstname");
            this.lastName = res.getString("lastname");
            this.login = res.getString("login");
            this.pwd=res.getString("pwd");
            this.gender = res.getString("gender");
            this.role = Role.values()[res.getBoolean("admin") ? 1 : 0];
            _builtFromDB = true;
        }
    }

    /**
     * Setter login
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Setter gender
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Getter gender
     * @return String
     */
    public String getGender() {
        return gender;
    }

    /**
     * Setter mot de passe
     * @param pwd
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * Getter login
     * @return String
     */
    public String getLogin() {
        return login;
    }

    /**
     * Getter mot de passe
     * @return String
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Getter nom de famille
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter prénom
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter nom de famille
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Setter prénom
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter role
     * @param role
     */
    public void setRole(String role) {
        this.role = Role.valueOf(role);
    }

    /**
     * Getter role
     * @return String
     */
    public String getRole() {
        return role.toString();
    }
    
    /**
     * Getter liste des forums auxquels l'utilisateur courant est abonné.
     * @return ArrayList<Forum>
     */
    public ArrayList<Forum> getForumSubscriptions() {
        return forumSubscriptions;
    }

    //active record
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.lastName);
        hash = 97 * hash + Objects.hashCode(this.firstName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Utilisateur other = (Utilisateur) obj;
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ""
                + ", login=" + login + ", gender=" + gender + ","
                + " pwd=" + pwd + '}';
    }

    //methodes pour implémenter Active record
    @Override
    protected String _update() {
        return "UPDATE `utilisateur` SET `firstname` = '" + firstName + "', `lastname` = '" + lastName + "',"
                + " `login` = '" + login + "', `admin` = '" + (role == Role.Admin ? "1" : "0") + "', `gender` = '" + gender + "', `pwd` = '" + pwd + "' WHERE `id` = " + id + ";";
    }

    @Override
    protected String _insert() {
        return "INSERT INTO `utilisateur` (`firstname`, `lastname`, `login`, `gender`, `admin`,`pwd`) "
                + "VALUES ('" + firstName + "', '" + lastName + "', '" + login + "', '" + gender + "', '" + (role == Role.Admin ? "1" : "0") + "','" + pwd + "');";
    }

    @Override
    protected String _delete() {
        return "DELETE FROM `utilisateur` WHERE (`id` = '" + id + "');";
    }

    /**
     * Cette méthode retrouve un utilisateur dans la base de données à partir de son identificateur qui est unique. 
     * Elle renvoie l'objet persistant correspondant, null si il n'existe pas.
     * @param id
     * @return Utilisateur
     */
    public static Utilisateur FindByID(int id) {
    	Connection conn = JDBCMysql.getConnection();
    	Utilisateur u = null;
    	ResultSet res = null;
        String select_query = "select * from `utilisateur` where `id` = ?;";
        try { 
        	PreparedStatement sql = null;
	        sql = conn.prepareStatement(select_query);
	        sql.setInt(1, id);
	        res = sql.executeQuery();
			if (res.next()) {
				u = new Utilisateur(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return u;
    }

    /**
     * Cette méthode cherche et retourne un utilisateur à partir de son login et de son mot de passe pour l'authentifier à la connexion.
     * Elle renvoie l'objet persistant correspondant, null si il n'existe pas.
     * @param login
     * @param pwd
     * @throws IOException, ClassNotFoundException, SQLException
     * @return Utilisateur
     */
    public static Utilisateur FindByloginAndPwd(String login, String pwd) throws IOException, ClassNotFoundException, SQLException {
        Connection conn = JDBCMysql.getConnection();
        String select_query = "select * from `utilisateur` where `login` = ? and `pwd` = ? ;";
        Utilisateur u = null;
        PreparedStatement sql = null;
        sql = conn.prepareStatement(select_query);
        sql.setString(1, login);
        sql.setString(2, pwd);
        ResultSet res = sql.executeQuery();
        if (res.next())
        	u = new Utilisateur(res);
        return u;
    }
	    
    /**
     * Cette méthode trouve un utilisateur par son nom et son prénom.
     * Elle renvoie l'objet persistant correspondant, null si il n'existe pas.
     * @return Utilisateur
     * @throws IOException, ClassNotFoundException, SQLException
     */
    public static Utilisateur FindByLastAndFirstName(String fname, String lname) throws IOException, ClassNotFoundException, SQLException {
        Connection conn = JDBCMysql.getConnection();
        String select_query = "select * from `utilisateur` where `firstname` = ? and `lastname` = ? ;";
        PreparedStatement sql = null;
        Utilisateur u = null;
        sql = conn.prepareStatement(select_query);
        sql.setString(1, fname);
        sql.setString(2, lname);
        ResultSet res = sql.executeQuery();
        if (res.next())
        	u = new Utilisateur(res);
        return u;
    }

    /**
     * Cette méthode trouve tous les utilisateurs existants dans la base de données. 
     * Elle retourne une liste de ces utilisateurs sous forme d'objets persistants.
     * @return ArrayList<Utilisateur>
     */
    private static ArrayList<Utilisateur> FindAll() throws IOException, ClassNotFoundException, SQLException {
        ArrayList<Utilisateur>  listUser = new ArrayList<Utilisateur>();
        Connection conn = JDBCMysql.getConnection();
        Statement sql = conn.createStatement();
        ResultSet res = sql.executeQuery(_query);
        while (res.next()) {
        	Utilisateur newUser = new Utilisateur(res);
            listUser.add(newUser);
        }
        return listUser;
    }
	    
    /**
     * Cette méthode fait d'abord appel à la méthode {@link #FindAll()} pour récupérer tous les utilisateurs.
     * Ensuite, on créé une chaîne avec des balises HTML pour l'affichage de ces utilisateurs.
     * On fait appel à la méthode {@link #toString()} pour afficher chaque utilisateur.
     * @return String
     */
    public static String FindAlltoString() throws IOException, ClassNotFoundException, SQLException {
        String contenu = "<ul>";	      
        ArrayList<Utilisateur> users = Utilisateur.FindAll();
        for (int index = 0; index < users.size(); index++) {
            contenu += "<li>";
            contenu += users.get(index).toString();
            contenu += "</li>";
        }
        contenu += "</ul>";
        return contenu;
    }
	    
    /**
     * Cette méthode trouve les forums pour lesquels l'utilisateur appelant est abonné. Elle récupère un objet persistant
     * par forum et renvoie la liste de ces derniers.
     * @return ArrayList<Forum>
     */
    private void LoadForumSubscriptions() {
    	this.forumSubscriptions = new ArrayList<Forum>();
    	ResultSet rs = null;
		PreparedStatement st = null;
		try {
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("SELECT id_forum FROM subscriptions WHERE id_user = ?;");
			st.setInt(1, id);
			rs = st.executeQuery();
			while(rs.next()) {
				this.forumSubscriptions.add(new Forum(rs.getInt(1)));
			}
			st.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Cette méthode ajoute une subscription à un forum pour l'utilisateur courant. On ajoute une entrée dans la table 'subscriptions'.
     * Elle prend en paramètre l'identifiant du forum auquel on s'abonne. On ajoute également l'objet persistant correspondant au forum voulu dans 
     * la liste locale des forums auxquels ont est abonné.
     * @param id
     */
    public void addForumSubscription(int id) {
    	try {
			this.forumSubscriptions.add(new Forum(id));
			Connection db = JDBCMysql.getConnection();
			String select_query = "insert into subscriptions (id_user, id_forum) values (?,?);";
	        PreparedStatement sql = null;
	        sql = db.prepareStatement(select_query);
	        sql.setInt(1, this.id);
	        sql.setInt(2, id);
	        sql.execute();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}	    	
    }

    /**
     * Cette méthode supprime une subscription à un forum pour l'utilisateur courant. On enlève une entrée dans la table 'subscriptions'.
     * Elle prend en paramètre l'identifiant du forum auquel on souhaite se désabonner.
     * On enlève également l'objet forum de la liste locale des forums auxquels on est abonné.
     * @param id
     */
    public void deleteForumSubscription(int id) {
    	try {
			this.forumSubscriptions.removeIf(f -> f.id == id);
			Connection db = JDBCMysql.getConnection();
			String select_query = "delete from subscriptions where id_forum=? and id_user=?;";
	        PreparedStatement sql = null;
	        sql = db.prepareStatement(select_query);
	        sql.setInt(1, id);
	        sql.setInt(2, this.id);
	        sql.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	    
    /**
     * Cette méthode est utilisée par {@link #getForumsByAdmin()}. Elle renvoie la liste des forums qui ont été
     * créés par l'utilisateur courant.
     * @return ArrayList<Forum>
     */
    private ArrayList<Forum> findForumCree() {
    	ArrayList<Forum> forumCree = new ArrayList<Forum>();
    	ResultSet rs = null;
		PreparedStatement st = null;
		try {
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("SELECT id, titre, description, createur FROM forum WHERE createur=?;");
			st.setInt(1, this.id);
			rs = st.executeQuery();
			while(rs.next()) {
				forumCree.add(new Forum(rs));
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return forumCree;
    }

    /**
     * Cette méthode supprime un utilisateur. Elle commence par récupérer l'utilisateur persistant correspondant à l'id passé en paramètres.
     * Ensuite, elle remplace pour tous les messages venant de cet utilisateur le créateur par un "anonyme" présent dans la base de données.
     * Ensuite, elle supprime tous les abonnements de cet utilisateur.
     * Finalement, on supprime l'utilisateur de la base de données.
     * @param id
     */
    public static void supprimerUtilisateur(int id) {
    	Utilisateur u = Utilisateur.FindByID(id);
		//id utilisateur anonyme = "nom=anonymous, prenom=anonymous, login=anonymous, password=vide"
    	//on remplace le créateur dans ses messages par l'utilisateur 'anonyme'
		PreparedStatement st2 = null;
		try {
			Connection db = JDBCMysql.getConnection();
			st2 = db.prepareStatement("UPDATE message SET owner=(SELECT id FROM utilisateur WHERE lastname='anonymous') WHERE owner=?;");
			st2.setInt(1, id);
			st2.execute();
		}catch(Exception e) {
			System.out.println(e);
		}
		//on supprime les abonnements de cet utilisateur
		PreparedStatement st = null;
		try {
			Connection db = JDBCMysql.getConnection();
			st = db.prepareStatement("DELETE FROM subscriptions WHERE id_user=?;");
			st.setInt(1, id);
			st.execute();
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			st.close();
			st2.close();
			//on supprime l'utilisateur de la base
			u.delete();
		} catch (Exception e) {
			System.out.println(e);
		}
    }
	    
    /**
     * Cette méthode liste les forums auxquels l'utilisateur courant est abonné sous forme d'une chaîne imprimable.
     * On ajoute deux formulaires à chaque forum : un pour accéder aux messages, et un pour supprimer son abonnement à ce forum.
     * Cette méthode est utilisée pour les utilisateurs classiques.
     * @return String
     */
    public String listerForumsAbonne() {
    	String contenu="";
    	contenu += "<ul>";
    	LoadForumSubscriptions();
    	for (int index = 0; index < this.forumSubscriptions.size(); index++) {
    		contenu += "<li>";
    		contenu += forumSubscriptions.get(index).toStringListe();
    		//bouton pour afficher les messages du forum
    		contenu += "<form action='AfficherForum' method='post'>";
    		contenu += "<input type='hidden' name='forum' value='"+forumSubscriptions.get(index).getId()+"'>";
    		contenu += "<input type='submit' value='Accéder au forum'>";
    		contenu += "</form>";
    		//bouton pour se desabonner du forum
    		contenu += "<form action='SupprimerAbonnementForum' method='post'>";
    		contenu += "<input type='hidden' name='forum' value='"+forumSubscriptions.get(index).getId()+"'>";
    		contenu += "<input type='submit' value='Se désabonner'>";
    		contenu += "</form></li>";
    	}
    	contenu += "</ul>";
    	return contenu;
    }
	    
    /**
     * Cette méthode liste les forums auxquels l'utilisateur courant n'est pas abonné.
     * Pour chaque forum on ajoute un formulaire pour s'y abonner.
     * Cette méthode est utilisée pour les utilisateurs classiques.
     * @return String
     */
    public String listerForumsNonAbonne() {
		String contenu= "<dl>";
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			Connection db = JDBCMysql.getConnection();
			//requête qui récupère les forums auxquels l'utilisateur n'est pas abonné
			st = db.prepareStatement("select f.id, f.titre, f.description, u.firstname, u.lastname from forum f, utilisateur u where u.id=f.createur and f.id NOT IN (select id_forum from subscriptions where id_user=?);");
			st.setInt(1, this.id);
			rs = st.executeQuery();
			contenu += "<ul>";
			while(rs.next()) {
				contenu += "<li><dt>"+rs.getString(2) +" by "+ rs.getString(4) +" "+rs.getString(5)+"</dt></a>";
				contenu += "<dd>"+rs.getString(3)+"</dd>";
				//bouton pour s'abonner au forum
				contenu += "<form action='AjouterAbonnementForum' method='post'>";
	    		contenu += "<input type='hidden' name='forum' value='"+rs.getInt(1)+"'>";
	    		contenu += "<input type='submit' value=\"S'abonner\">";
	    		contenu += "</form></li>";
			}
			contenu += "</ul>";
			contenu += "</dl>";
			st.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return contenu;
	}
}