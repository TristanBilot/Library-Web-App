package users;

import mediatheque.*;

/*
 * Un Abonné peut être un bibliothécaire (booléeen bibliotécaire)
 */

public class Abonné implements Utilisateur{
	
	private boolean bibliothécaire;
	private String login;
	private String password;
	private int num;
	
	public Abonné(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public Abonné(int num, String login2, String password2, boolean b) {
		this(login2, password2);
		this.bibliothécaire = b;
		this.num = num;
	}

	@Override
	public boolean isBibliothecaire() {
		return bibliothécaire;
	}

	public boolean isBibliothécaire() {
		return bibliothécaire;
	}

	public void setBibliothécaire(boolean bibliothécaire) {
		this.bibliothécaire = bibliothécaire;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
