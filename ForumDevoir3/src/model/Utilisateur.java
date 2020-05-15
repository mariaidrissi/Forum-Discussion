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
    private String login; //mail adress
    private String gender;
    private String pwd;
    private Role role = Role.Other;
    private static String _query = "select * from utilisateur"; // for findAll static Method
    private ArrayList<Forum> forumSubscriptions;

	 public ArrayList<Forum> getForumSubscriptions() {
	        return forumSubscriptions;
	    }

	    private enum Role {
	        Other, Admin
	    };

	    public Utilisateur() {
	    	this.forumSubscriptions = new ArrayList<Forum>();
	        _builtFromDB = false;
	    }

	    public Utilisateur(String lastName, String firstName, String login, String gender, String pwd) {

	        this.lastName = lastName;
	        this.firstName = firstName;
	        this.login = login;
	        this.gender = gender;
	        this.pwd = pwd;
	        this.forumSubscriptions = new ArrayList<Forum>();
	        _builtFromDB = false;
	    }

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

	    public void setLogin(String login) {
	        this.login = login;
	    }

	    public void setGender(String gender) {
	        this.gender = gender;
	    }

	    public String getGender() {
	        return gender;
	    }

	    public void setPwd(String pwd) {
	        this.pwd = pwd;
	    }

	    public String getLogin() {
	        return login;
	    }

	    public String getPwd() {
	        return pwd;
	    }

	    public String getLastName() {
	        return lastName;
	    }

	    public String getFirstName() {
	        return firstName;
	    }

	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	    }

	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	    }

	    public void setRole(String role) {
	        this.role = Role.valueOf(role);
	    }

	    public String getRole() {
	        return role.toString();
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

	    public Utilisateur(String lastName, String firstName) {
	        this.lastName = lastName;
	        this.firstName = firstName;

	    }

	    @Override
	    public String toString() {
	        return "User{" + "lastName=" + lastName + ", firstName=" + firstName + ""
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

	    public static Utilisateur FindByID(int id) {
	    	Connection conn = JDBCMysql.getConnection();
	    	ResultSet res = null;
	        String select_query = "select * from `utilisateur` where `id` = ?;";
	        try { 
	        	PreparedStatement sql = null;
	        
		        sql = conn.prepareStatement(select_query);
		        sql.setInt(1, id);
		        res = sql.executeQuery();

				if (res.next()) {
					Utilisateur u = new Utilisateur(res);
				    return u;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        return null;
	    }

	    public static Utilisateur FindByloginAndPwd(String login, String pwd) throws IOException, ClassNotFoundException, SQLException {
	        Connection conn = JDBCMysql.getConnection();
	        String select_query = "select * from `utilisateur` where `login` = ? and `pwd` = ? ;";
	        PreparedStatement sql = null;
	        sql = conn.prepareStatement(select_query);
	        sql.setString(1, login);
	        sql.setString(2, pwd);
	        ResultSet res = sql.executeQuery();
	        if (res.next()) {
	        	Utilisateur u = new Utilisateur(res);
	            return u;

	        }
	        return null;
	    }

	    public static ArrayList<Utilisateur> FindAll() throws IOException, ClassNotFoundException, SQLException {
	        ArrayList<Utilisateur>  listUser = new ArrayList<Utilisateur>() ;
	        
	        Connection conn = JDBCMysql.getConnection();
	        Statement sql = conn.createStatement();
	        ResultSet res = sql.executeQuery(_query);
	        while (res.next()) {
	        	Utilisateur newUser= new Utilisateur(res);
	            listUser.add(newUser);
	        }

	        return listUser;
	    }
	    
	    public static String FindAlltoString() throws IOException, ClassNotFoundException, SQLException {

	        String contenu = "<ol>";
	      
	        ArrayList<Utilisateur> users = Utilisateur.FindAll();
	        for (int index = 0; index < users.size(); index++) {
                contenu += "<li>";
                contenu += users.get(index).toString();
                contenu += "</li>";
            }

	        contenu += "</ol>";
	        return contenu;
	    }
	    
	    public static Utilisateur FindByLastAndFirstName(String fname, String lname) throws IOException, ClassNotFoundException, SQLException {
	        Connection conn = JDBCMysql.getConnection();
	        String select_query = "select * from `utilisateur` where `firstname` = ? and `lastname` = ? ;";
	        PreparedStatement sql = null;
	        sql = conn.prepareStatement(select_query);
	        sql.setString(1, fname);
	        sql.setString(2, lname);
	        ResultSet res = sql.executeQuery();
	        if (res.next()) {
	        	Utilisateur u = new Utilisateur(res);
	            return u;
	        }
	        return null;
	    }
	    
	    public ArrayList<Forum> LoadForumSubscriptions() {
	    	ArrayList<Forum> forumSubs = new ArrayList<Forum>();

	    	ResultSet rs = null;
			PreparedStatement st = null;
			try {
			
				Connection db = JDBCMysql.getConnection();
				st = db.prepareStatement("SELECT id_forum FROM subscriptions WHERE id_user = ?;");
				st.setInt(1, id);
				rs = st.executeQuery();
				while(rs.next()) {
					forumSubs.add(new Forum(rs.getInt(1)));
				}
			}catch(Exception e) {
				System.out.println(e);
			}
			try {
				st.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
	    	
	    	return forumSubs;
	    }

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
	    
	    public String getForumsByAdmin() {
	    	String contenu = "<ul>";
	    	ArrayList<Forum> forums = findForumCree();
	    	for (int index = 0; index < forums.size(); index++) {
	            contenu += "<li>";
	            contenu += forums.get(index).toStringListe();
	            contenu += "<form action='SupprimerForum' method='post'><input type='hidden' name='forum' value='"+forums.get(index).getId()+"'><input type='submit' value='Supprimer'></form>";
	            contenu += "<form action='AfficherForum' method='post'><input type='hidden' name='forum' value='"+forums.get(index).getId()+"'><input type='submit' value='Acceder au forum'></form>";
	            contenu += "</li>";
	        }
	    	contenu += "</ul>";
	    	return contenu;
	    }
	    
	    public ArrayList<Forum> findForumCree() {
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

	    public String listerForumToString() {
	    	String contenu="";
	    	contenu += "<ul>";
	    	this.forumSubscriptions = LoadForumSubscriptions();
	    	for (int index = 0; index < this.forumSubscriptions.size(); index++) {
	    		contenu += "<li>";
	    		contenu += forumSubscriptions.get(index).toStringListe();
	    		contenu += "<form action='AfficherForum' method='post'>";
	    		contenu += "<input type='hidden' name='forum' value='"+forumSubscriptions.get(index).getId()+"'>";
	    		contenu += "<input type='submit' value='Acceder au forum'>";
	    		contenu += "</form>";
	    		contenu += "<form action='SupprimerAbonnementForum' method='post'>";
	    		contenu += "<input type='hidden' name='forum' value='"+forumSubscriptions.get(index).getId()+"'>";
	    		contenu += "<input type='submit' value='Se desabonner'>";
	    		contenu += "</form></li>";
	    	}
	    	contenu += "</ul>";
	    	return contenu;
	    }
	    
	    public String listerForumsNonAbonne() {
			String contenu= "<dl>";
			
			ResultSet rs = null;
			PreparedStatement st = null;
			try {
				Connection db = JDBCMysql.getConnection();
				st = db.prepareStatement("select f.id, f.titre, f.description, u.firstname, u.lastname from forum f, utilisateur u where u.id=f.createur and f.id NOT IN (select id_forum from subscriptions where id_user=?);");
				st.setInt(1, this.id);
				rs = st.executeQuery();
				contenu += "<ul>";
				while(rs.next()) {
					contenu += "<li><dt>"+rs.getString(2) +" by "+ rs.getString(4) +" "+rs.getString(5)+"</dt></a>";
					contenu += "<dd>"+rs.getString(3)+"</dd>";
					contenu += "<form action='AjouterAbonnementForum' method='post'>";
		    		contenu += "<input type='hidden' name='forum' value='"+rs.getInt(1)+"'>";
		    		contenu += "<input type='submit' value=\"S'abonner\">";
		    		contenu += "</form></li>";
				}
				contenu += "</ul>";
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
}