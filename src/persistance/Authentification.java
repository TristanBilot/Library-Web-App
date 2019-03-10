package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Authentification {
			
	public static boolean userExists(String login, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Connection c = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(MediathequeData.JDBC_URL + "user=" + MediathequeData.LOGIN + "&password=" + MediathequeData.PASSWORD);
		} catch (SQLException e) {e.printStackTrace();}
		
		boolean loginOK = false, pwOK = false;
		
		try { 		
			String req = "SELECT * FROM USER;";
			Statement st = c.createStatement();
			ResultSet res = st.executeQuery(req);

			while (res.next()) { 
				if (res.getString("login").equals(login))
				loginOK = true;
				if (res.getString("password").equals(password))
					pwOK = true;
			}
		} catch (SQLException ex){System.out.println(ex.getMessage());}
		if (loginOK && pwOK) 
			return true;
		return false;
    }
}
