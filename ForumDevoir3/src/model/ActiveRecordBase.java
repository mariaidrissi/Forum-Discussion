package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ActiveRecordBase {

	protected int id;
    protected boolean _builtFromDB;

    /**
     * Méthode à implémenter pour chaque classe fille : on définit la requête propre à l'objet voulu pour le mettre jour dans la base de données.
     * @return String
     */
    protected abstract String _update();

    /**
     * Méthode à implémenter pour chaque classe fille : on définit la requête propre à l'objet voulu pour l'ajouter dans la base de données.
     * @return String
     */
    protected abstract String _insert();

    /**
     * Méthode à implémenter pour chaque classe fille : on définit la requête propre à l'objet voulu pour le supprimer de la base de données.
     * @return String
     */
    protected abstract String _delete();

    /**
     * Méthode qui sauvegarde l'objet courant dans le base de données. Si l'objet était déjà persistant, on 
     * fait une mise à jour, sinon on l'insert dans la base et on le rend donc persistant. On récupère en même temps
     * l'identificateur auto généré par la base de données pour l'objet.
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void save() throws SQLException, IOException, ClassNotFoundException {
        Connection conn = JDBCMysql.getConnection();
        Statement s = conn.createStatement();
        if (_builtFromDB) {
            System.out.print("Executing this command : " + _update() + "\n");
            s.executeUpdate(_update());
        } else {
            System.out.println("Executing this command: " + _insert() + "\n");

            // Récupérer la valeur de clé artificielle (auto_incrément)
            s.executeLargeUpdate(_insert(), Statement.RETURN_GENERATED_KEYS);
            _builtFromDB = true;
            ResultSet r = s.getGeneratedKeys();
            while (r.next()) {
                id = r.getInt(1);
            }
        }
    }

    /**
     * Méthode qui supprime l'objet persistant courant.
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void delete() throws IOException, ClassNotFoundException, SQLException {
        Connection conn = JDBCMysql.getConnection();
        Statement s = conn.createStatement();
        if (_builtFromDB) {
            System.out.println("Executing this command: " + _delete() + "\n");
            s.executeUpdate(_delete());     
        } else {
            System.out.println("Objet non persistant!");
        }
    }

    /**
     * Getter id
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Setter id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    

}