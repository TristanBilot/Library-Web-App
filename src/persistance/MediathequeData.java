package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import documents.*;
import mediatheque.*;
import users.Abonné;

// classe mono-instance  dont l'unique instance n'est connue que de la bibliotheque
// via une auto-d�claration dans son bloc static

public class MediathequeData implements PersistentMediatheque {	
	public static final String JDBC_URL = "jdbc:mysql://localhost/library?";
	public static final String LOGIN = "root";
	public static final String PASSWORD = "rootroot";
	
	static {
		Mediatheque.getInstance().setData(new MediathequeData());
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {e.printStackTrace();}
	}

	@Override
	public List<mediatheque.Document> tousLesDocuments() {
		Connection c = null;
		try {
			c = DriverManager.getConnection(JDBC_URL + "user=" + LOGIN + "&password=" + PASSWORD);
		} catch (SQLException e) {e.printStackTrace();}
		
		List<Document> documents = new ArrayList<Document>();
		try {
			PreparedStatement pstmt = c.prepareStatement("SELECT * FROM DOCUMENT WHERE disponible = 1;");
			ResultSet res = pstmt.executeQuery();
			String nom, type, dispo, réservé, num;
			
			while (res.next()) {
					nom = res.getString("nom"); // Label du document
					type = res.getString("type"); // Cd, DVD, Livre
					num = res.getString("id");
					dispo = res.getString("disponible");
					réservé = res.getString("réservé");
					
					switch (type) {
					case "Cd":
						documents.add(new Cd(Integer.parseInt(num), nom, (dispo.equals("1")) ? true:false, (réservé.equals("1")) ? true:false));
					case "Dvd":
						documents.add(new Dvd(Integer.parseInt(num), nom, (dispo.equals("1")) ? true:false, (réservé.equals("1")) ? true:false));
					case "Livre":
						documents.add(new Livre(Integer.parseInt(num), nom, (dispo.equals("1")) ? true:false, (réservé.equals("1")) ? true:false));
					default:
						break;
					}
			}
		} catch (SQLException ex){System.out.println(ex.getMessage());}
		return documents;
	}
	
	
	public boolean documentExists(int numero) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Connection c = null;
		try {
			c = DriverManager.getConnection(JDBC_URL + "user=" + LOGIN + "&password=" + PASSWORD);
		} catch (SQLException e) {e.printStackTrace();}
		
		try { // create database tp1JavaWeb; --> sur la cli mysql			
			String req = "SELECT * FROM DOCUMENT;";
			Statement st = c.createStatement();
			ResultSet res = st.executeQuery(req);

			while (res.next()) { 
				if (res.getString("id").equals(String.valueOf(numero)))
					return true;
			}
		} catch (SQLException ex){System.out.println(ex.getMessage());}
		return false;
    }
	
	public boolean userIsBibliothécaire(String login, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Connection c = null;
		try {
			c = DriverManager.getConnection(JDBC_URL + "user=" + LOGIN + "&password=" + PASSWORD);
		} catch (SQLException e) {e.printStackTrace();}
		try {
			PreparedStatement pstmt = c.prepareStatement("SELECT * FROM USER WHERE login = ? AND password = ?;");
			pstmt.setString(1, login);
			pstmt.setString(2, password); 
			ResultSet res = pstmt.executeQuery();

			while (res.next()) {
				if (res.getString("isB").equals("1"))
					return true;
			}
		} catch (SQLException ex){System.out.println(ex.getMessage());}
		return false;
    }
	
	public String getNumeroOfUser(String user) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Connection c = null;
		try {
			c = DriverManager.getConnection(JDBC_URL + "user=" + LOGIN + "&password=" + PASSWORD);
		} catch (SQLException e) {e.printStackTrace();}
		String num = "";	
		PreparedStatement pstmt = c.prepareStatement("SELECT * FROM USER WHERE login=?;");
		pstmt.setString(1, user);
		ResultSet res = pstmt.executeQuery();
		while (res.next()) 
			num = res.getString("id");
		return num;
	}
	
	@Override
	public Utilisateur getUser(String login, String password) {
		int num = 0;
		try {
			if (Authentification.userExists(login, password)) {// vérifie si le user existe 
				try {
					num = Integer.parseInt(getNumeroOfUser(login));
				} catch (NumberFormatException | SQLException e) {e.printStackTrace();}
				
				if (userIsBibliothécaire(login, password))
					return new Abonné(num, login, password, true);
				else
					return new Abonné(num, login, password, false);
			}
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		return null;
	}

	@Override
	public Document getDocument(int numDocument) {
		Connection c = null;
		try {
			c = DriverManager.getConnection(JDBC_URL + "user=" + LOGIN + "&password=" + PASSWORD);
		} catch (SQLException e) {e.printStackTrace();}
		String nom, type, dispo, réservé, num;
		try {
			if (documentExists(numDocument)) {
				try {			
					String req = "SELECT * FROM DOCUMENT;";
					Statement st = c.createStatement();
					ResultSet res = st.executeQuery(req);

					while (res.next()) {
						if (res.getString("id").equals(String.valueOf(numDocument))) {
							nom = res.getString("nom"); // Label du document
							type = res.getString("type"); // Cd, DVD, Livre
							num = res.getString("id");
							dispo = res.getString("disponible");
							réservé = res.getString("réservé");
							
							switch (type) {
							case "cd":
								return new Cd(Integer.parseInt(num), nom, (dispo.equals("1")) ? true:false, (réservé.equals("1")) ? true:false);
							case "dvd":
								return new Dvd(Integer.parseInt(num), nom, (dispo.equals("1")) ? true:false, (réservé.equals("1")) ? true:false);
							case "livre":
								return new Livre(Integer.parseInt(num), nom, (dispo.equals("1")) ? true:false, (réservé.equals("1")) ? true:false);
							default:
								break;
							}
						}							
					}
				} catch (SQLException ex){System.out.println(ex.getMessage());}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public void nouveauDocument(int type, Object... args) {
		if (type > 3 || type < 1)
			throw new IllegalArgumentException();
		String typeDoc = "";
		int id = getPlusGrandId() + 1;
		switch (type) {
		case 1:
			typeDoc = "livre";
			break;
		case 2:
			typeDoc = "cd";
			break;
		case 3:
			typeDoc = "dvd";
			break;
		default:
			break;
		}
		Connection c = null;
		try {
			c = DriverManager.getConnection(MediathequeData.JDBC_URL + "user=" + MediathequeData.LOGIN + "&password=" + MediathequeData.PASSWORD);
		} catch (SQLException e) {e.printStackTrace();}				

		try {
			String sql = "INSERT INTO DOCUMENT VALUES(?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, String.valueOf(id)); // id incrémenté
			stmt.setString(2, typeDoc); // livre, cd etc
			stmt.setString(3, "NULL"); // proprio non utilisé dans ce projet
			stmt.setString(4, "1"); // dispo
			stmt.setString(5, "0"); // non réservé
			stmt.setString(6, (String) args[0]); // nom
			stmt.setString(7, (String) args[1]); // auteur
			stmt.executeUpdate();	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private int getPlusGrandId() {
		Connection c = null;
		int maxId = 0;
		int actualId = 0;
		try {
			c = DriverManager.getConnection(JDBC_URL + "user=" + LOGIN + "&password=" + PASSWORD);
		} catch (SQLException e) {e.printStackTrace();}
		PreparedStatement pstmt;
		try {
			pstmt = c.prepareStatement("SELECT * FROM DOCUMENT;");
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				actualId = Integer.parseInt(res.getString("id"));
				if (actualId > maxId)
					maxId = actualId;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		return maxId;
	}

}
