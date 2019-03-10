package documents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mediatheque.EmpruntException;
import mediatheque.Utilisateur;
import persistance.MediathequeData;

public abstract class AbstractDocument implements mediatheque.Document {
	String nom;
	boolean réservé; // implanté pour d'eventuelles évolutions
	boolean dispo;
	int numéro;
	
	public AbstractDocument(int id, String nom, boolean dispo, boolean réservé) {
		this.nom = nom;
		this.réservé = réservé; 
		this.dispo = dispo;
		this.numéro = id;
	}
	
	public String getNom() {
		return nom;
	}
	
	@Override
	public void emprunter(Utilisateur user) throws EmpruntException {
		  if (dispo)
			  if (!réservé) {
				Connection c = null;
				try {
					c = DriverManager.getConnection(MediathequeData.JDBC_URL + "user=" + MediathequeData.LOGIN + "&password=" + MediathequeData.PASSWORD);
				} catch (SQLException e) {e.printStackTrace();}				
				try { 	
					String req = "SELECT * FROM DOCUMENT;";
					Statement st = c.createStatement();
					ResultSet res = st.executeQuery(req);
		
					while (res.next()) { 
						if (res.getString("id").equals(String.valueOf(this.numéro))) {
							// INSERT 
							String sql = "UPDATE DOCUMENT SET disponible = 0, réservé = 0 WHERE id = ?;";
							PreparedStatement stmt = c.prepareStatement(sql);
							stmt.setString(1, String.valueOf(this.numéro));
							stmt.executeUpdate();
							this.dispo = false;
							this.réservé = false;
						}
					}
				} catch (SQLException ex){System.out.println(ex.getMessage());}
			}
	}
	
	@Override
	public void retour() {
		if (!dispo)	{
			Connection c = null;
			try {
				c = DriverManager.getConnection(MediathequeData.JDBC_URL + "user=" + MediathequeData.LOGIN + "&password=" + MediathequeData.PASSWORD);
			} catch (SQLException e) {e.printStackTrace();}				
			try { 	
				String req = "SELECT * FROM DOCUMENT;";
				Statement st = c.createStatement();
				ResultSet res = st.executeQuery(req);
		
				while (res.next()) { 
					if (res.getString("id").equals(String.valueOf(this.numéro))) {
							// INSERT 
						String sql = "UPDATE DOCUMENT SET disponible = 1, réservé = 0 WHERE id = ?;";
						PreparedStatement stmt = c.prepareStatement(sql);
						stmt.setString(1, String.valueOf(this.numéro));
						stmt.executeUpdate();
						this.dispo = true;
						this.réservé = false;
					}
				}
			} catch (SQLException ex){System.out.println(ex.getMessage());}
		}		
	}
	
	@Override
	public Object[] affiche() {
		// TODO Auto-generated method stub
		return null;
	}

}
