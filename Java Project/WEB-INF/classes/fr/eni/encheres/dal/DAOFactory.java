package fr.eni.encheres.dal;

public abstract class DAOFactory {
	
	public static UtilisateursDAO getUtilisateursDAO() {
		return new UtilisateursDAOJdbcImpl();
	}
	
	public static ArticlesDAO getArticlesDAO() {
		return new ArticlesDAOJdbcImpl();
	}
	
	public static EncheresDAO getEncheresDAO() {
		return new EncheresDAOJdbcImpl();
	}
	
}
