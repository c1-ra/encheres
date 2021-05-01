package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;

public class ArticlesDAOJdbcImpl implements ArticlesDAO {
	
	private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	
	private static final String UPDATE_ARTICLE = "UPDATE ARTICLES "
			+ "SET nom = ?, description = ?, date_debut_encheres = ?, "
			+ "date_fin_encheres = ?, prix_initial = ?, id_categorie = ? "
			+ "WHERE id = ?;";
	
	// Enchères ouvertes
	private static final String SELECT_EN_COURS = "SELECT a.id, a.nom, date_fin_encheres, prix_initial, prix_vente, id_categorie, a.id_utilisateur, pseudo, MAX(montant_enchere) as enchere_max" +
			" FROM ARTICLES a "+
				" INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" +
				" LEFT JOIN ENCHERES e ON a.id = e.id_article" +
			" WHERE id_etat_vente = 2" +	
			" GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, prix_initial" +
			" ORDER BY date_fin_encheres;";
	
	private static final String SELECT_EN_COURS_FILTRES = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, pseudo, MAX(montant_enchere) as enchere_max, prix_initial" +
			" FROM ARTICLES a " +
				" INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" +
				" LEFT JOIN ENCHERES e ON a.id = e.id_article" +
			" WHERE id_etat_vente = 2 AND a.nom LIKE ? AND id_categorie = ?" +
			" GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, prix_initial" +
			" ORDER BY date_fin_encheres;";
	
	private static final String SELECT_EN_COURS_NOM = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, pseudo, MAX(montant_enchere) as enchere_max, prix_initial" +
			" FROM ARTICLES a " +
				" INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" +
				" LEFT JOIN ENCHERES e ON a.id = e.id_article" +
			" WHERE id_etat_vente = 2 AND a.nom LIKE ?" +
			" GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, prix_initial" +
			" ORDER BY date_fin_encheres;";
	
	private static final String SELECT_EN_COURS_CATEGORIE = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, pseudo, MAX(montant_enchere) as enchere_max, prix_initial" +
			" FROM ARTICLES a " +
				" INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" +
				" LEFT JOIN ENCHERES e ON a.id = e.id_article" +
			" WHERE id_etat_vente = 2 AND id_categorie = ?" +
			" GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, prix_initial" +
			" ORDER BY date_fin_encheres;";
	
	
	
	// Enchères en cours d'un utilisateur
	private static final String SELECT_ENCHERES_EN_COURS_BY_UTILISATEUR_ID = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, MAX(montant_enchere) as enchere_max, prix_initial" +
			" FROM ARTICLES a" + 
			" INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" +
			" LEFT JOIN ENCHERES e ON a.id = e.id_article" +
		" WHERE id_article IN (" +
			" SELECT a.id" +
				" FROM ARTICLES a" +
					" INNER JOIN ENCHERES e ON a.id = e.id_article" +
				" WHERE e.id_utilisateur = ? AND date_debut_encheres < GETDATE() AND date_fin_encheres > GETDATE()" +	
		") GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, prix_initial" +
		" ORDER BY date_fin_encheres;";
	
	private static final String SELECT_ENCHERES_EN_COURS_BY_UTILISATEUR_ID_FILTRES = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, MAX(montant_enchere) as enchere_max, prix_initial" +
			" FROM ARTICLES a" + 
			" INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" +
			" LEFT JOIN ENCHERES e ON a.id = e.id_article" +
		" WHERE id_article IN (" +
			" SELECT a.id" +
				" FROM ARTICLES a" +
					" INNER JOIN ENCHERES e ON a.id = e.id_article" +
				" WHERE e.id_utilisateur = ? AND date_debut_encheres < GETDATE() AND date_fin_encheres > GETDATE() AND a.nom LIKE ? AND id_categorie = ?" +
		") GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, prix_initial" +		
		" ORDER BY date_fin_encheres;";
	
	private static final String SELECT_ENCHERES_EN_COURS_BY_UTILISATEUR_ID_NOM = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, MAX(montant_enchere) as enchere_max, prix_initial" +
			" FROM ARTICLES a" + 
			" INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" +
			" LEFT JOIN ENCHERES e ON a.id = e.id_article" +
		" WHERE id_article IN (" +
			" SELECT a.id" +
				" FROM ARTICLES a" +
					" INNER JOIN ENCHERES e ON a.id = e.id_article" +
				" WHERE e.id_utilisateur = ? AND date_debut_encheres < GETDATE() AND date_fin_encheres > GETDATE() AND a.nom LIKE ?" +	
		") GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, prix_initial	" +		
		" ORDER BY date_fin_encheres;";
	
	private static final String SELECT_ENCHERES_EN_COURS_BY_UTILISATEUR_ID_CATEGORIE = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, MAX(montant_enchere) as enchere_max, prix_initial" +
			" FROM ARTICLES a" + 
			" INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" +
			" LEFT JOIN ENCHERES e ON a.id = e.id_article" +
		" WHERE id_article IN (" +
			" SELECT a.id" +
				" FROM ARTICLES a" +
					" INNER JOIN ENCHERES e ON a.id = e.id_article" +
				" WHERE e.id_utilisateur = ? AND date_debut_encheres < GETDATE() AND date_fin_encheres > GETDATE() AND id_categorie = ?" +	
		") GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, prix_initial	" +		
		" ORDER BY date_fin_encheres;";
	
	
	
	// Enchères remportées par un utilisateur
	private static final String SELECT_ENCHERES_REMPORTEES_BY_UTILISATEUR_ID = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, pseudo, enchere_max, MAX(montant_enchere) as montant_enchere_max_utilisateur, prix_initial" + 
			" FROM ARTICLES a" + 
			"		INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" + 
			"		LEFT JOIN ENCHERES e ON a.id = e.id_article" + 
			"		INNER JOIN (" + 
			"			SELECT id_article, MAX(montant_enchere) as enchere_max" + 
			"				FROM ENCHERES" + 
			"				GROUP BY id_article" + 
			"		) AS s ON a.id = s.id_article" + 
			"	WHERE date_fin_encheres < GETDATE() AND e.id_utilisateur = ?" + 
			"	GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, pseudo, enchere_max, prix_initial" + 
			"	HAVING enchere_max = MAX(montant_enchere);";
	
	private static final String SELECT_ENCHERES_REMPORTEES_BY_UTILISATEUR_ID_FILTRES = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, pseudo, enchere_max, MAX(montant_enchere) as montant_enchere_max_utilisateur, prix_initial" + 
			" FROM ARTICLES a" + 
			"		INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" + 
			"		LEFT JOIN ENCHERES e ON a.id = e.id_article" + 
			"		INNER JOIN (" + 
			"			SELECT id_article, MAX(montant_enchere) as enchere_max" + 
			"				FROM ENCHERES" +
			"				GROUP BY id_article" + 
			"		) AS s ON a.id = s.id_article" + 
			"	WHERE date_fin_encheres < GETDATE() AND e.id_utilisateur = ? AND a.nom LIKE ? AND id_categorie = ?" + 
			"	GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, pseudo,enchere_max, prix_initial" + 
			"	HAVING enchere_max = MAX(montant_enchere);";
	
	private static final String SELECT_ENCHERES_REMPORTEES_BY_UTILISATEUR_ID_NOM = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, pseudo, enchere_max, MAX(montant_enchere) as montant_enchere_max_utilisateur, prix_initial" + 
			" FROM ARTICLES a" + 
			"		INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" + 
			"		LEFT JOIN ENCHERES e ON a.id = e.id_article" + 
			"		INNER JOIN (" + 
			"			SELECT id_article, MAX(montant_enchere) as enchere_max" + 
			"				FROM ENCHERES" + 
			"				GROUP BY id_article" + 
			"		) AS s ON a.id = s.id_article" + 
			"	WHERE date_fin_encheres < GETDATE() AND e.id_utilisateur = ? AND a.nom LIKE ?" + 
			"	GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, pseudo,enchere_max, prix_initial" + 
			"	HAVING enchere_max = MAX(montant_enchere);";
	
	private static final String SELECT_ENCHERES_REMPORTEES_BY_UTILISATEUR_ID_CATEGORIE = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, pseudo, enchere_max, MAX(montant_enchere) as montant_enchere_max_utilisateur, prix_initial" + 
			" FROM ARTICLES a" + 
			"		INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" + 
			"		LEFT JOIN ENCHERES e ON a.id = e.id_article" + 
			"		INNER JOIN (" + 
			"			SELECT id_article, MAX(montant_enchere) as enchere_max" + 
			"				FROM ENCHERES" + 
			"				GROUP BY id_article" + 
			"		) AS s ON a.id = s.id_article" + 
			"	WHERE date_fin_encheres < GETDATE() AND e.id_utilisateur = ? AND id_categorie = ?" + 
			"	GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, pseudo,enchere_max, prix_initial" + 
			"	HAVING enchere_max = MAX(montant_enchere);";
	
	
	
	// Ventes
	private static final String SELECT_VENTES_BY_UTILISATEUR_ID = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, MAX(montant_enchere) as enchere_max, prix_initial" +
			" FROM ARTICLES a" +
			" INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" +
			" LEFT JOIN ENCHERES e ON a.id = e.id_article" +
		" WHERE id_etat_vente = ? AND a.id_utilisateur = ?" +
		" GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, prix_initial" +
		" ORDER BY date_fin_encheres;";
	
	private static final String SELECT_VENTES_BY_UTILISATEUR_ID_FILTRES = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, MAX(montant_enchere) as enchere_max, prix_initial" +
			" FROM ARTICLES a" +
			" INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" +
			" LEFT JOIN ENCHERES e ON a.id = e.id_article" +
		" WHERE id_etat_vente = ? AND a.id_utilisateur = ? AND a.nom LIKE ? AND id_categorie = ?" +
		" GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, prix_initial" +
		" ORDER BY date_fin_encheres;";
	
	private static final String SELECT_VENTES_BY_UTILISATEUR_ID_NOM ="SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, MAX(montant_enchere) as enchere_max, prix_initial" +
			" FROM ARTICLES a" +
			" INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" +
			" LEFT JOIN ENCHERES e ON a.id = e.id_article" +
		" WHERE id_etat_vente = ? AND a.id_utilisateur = ? AND a.nom LIKE ?" +
		" GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, prix_initial" +
		" ORDER BY date_fin_encheres;";
	
	private static final String SELECT_VENTES_BY_UTILISATEUR_ID_CATEGORIE = "SELECT a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, MAX(montant_enchere) as enchere_max, prix_initial" +
			" FROM ARTICLES a" +
			" INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" +
			" LEFT JOIN ENCHERES e ON a.id = e.id_article" +
		" WHERE id_etat_vente = ? AND a.id_utilisateur = ? AND id_categorie = ?" +
		" GROUP BY a.id, a.nom, date_fin_encheres, prix_vente, id_categorie, a.id_utilisateur, u.pseudo, prix_initial" +
		" ORDER BY date_fin_encheres;";
	
	
	
	private static final String SELECT_DETAIL_ARTICLE = "SELECT a.id, a.nom, a.description, id_categorie, libelle AS categorie, montant_enchere AS enchere_max, s.id_utilisateur AS idEncherisseur, s.pseudo AS pseudoEncherisseur, prix_initial, date_debut_encheres, date_fin_encheres, r.rue, r.code_postal, r.ville, a.id_utilisateur AS idVendeur, u.pseudo AS pseudoVendeur, id_etat_vente" + 
			"	FROM ARTICLES a " + 
			"		INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.id" + 
			"		INNER JOIN CATEGORIES c ON a.id_categorie = c.id" + 
			"		INNER JOIN RETRAITS r ON a.id = r.id_article" + 
			"		LEFT JOIN (" + 
			"			SELECT id_article, id_utilisateur, montant_enchere, pseudo" + 
			"				FROM ENCHERES e" + 
			"				INNER JOIN UTILISATEURS u ON e.id_utilisateur = u.id" + 
			"			WHERE e.id_article = ? AND e.montant_enchere = (SELECT max(montant_enchere) FROM ENCHERES e2 WHERE e.id_article = e2.id_article)" + 
			"		) AS s ON a.id = s.id_article" + 
			"	WHERE a.id = ?" + 
			"	ORDER BY date_fin_encheres;";
	
	
	
	private static final String DELETE_ARTICLE = "DELETE FROM ARTICLES WHERE id = ?;";
	
	
		
	// requêtes utilitaires et pour vérifications	
	
	private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS VALUES (?, ?, ?, ?);";
	
	private static final String UPDATE_RETRAIT = "UPDATE RETRAITS SET rue = ?, code_postal = ?, ville = ? WHERE id_article = ?;";
	
	
	
	@Override
	public void insertArticle(Article article) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			
			cnx.setAutoCommit(false);
			
			try {
				PreparedStatement pstmt = cnx.prepareStatement(INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, article.getNom());
				pstmt.setString(2, article.getDescription());
				pstmt.setDate(3, Date.valueOf(article.getDateDebut()));
				pstmt.setDate(4, Date.valueOf(article.getDateFin()));
				pstmt.setInt(5, article.getPrixInitial());
				pstmt.setInt(6, article.getPrixVente());
				pstmt.setInt(7, article.getUtilisateur().getId());
				pstmt.setInt(8, article.getCategorie().getId());
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					article.setId(rs.getInt(1));
				}
				rs.close();
				pstmt.close();
				
				pstmt = cnx.prepareStatement(INSERT_RETRAIT);
				pstmt.setInt(1, article.getId());
				pstmt.setString(2, article.getRetrait().getRue());
				pstmt.setString(3, article.getRetrait().getCodePostal());
				pstmt.setString(4, article.getRetrait().getVille());
				pstmt.executeUpdate();
				pstmt.close();
				
				cnx.commit();
				
			} catch (Exception e) {
				e.printStackTrace();
				cnx.rollback();
				article.setId(0);
				throw e;
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_ARTICLE_ERREUR);
			throw businessException;
		}
	}
	
	@Override
	public void updateArticle(Article article) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_ARTICLE);
			pstmt.setString(1, article.getNom());
			pstmt.setString(2, article.getDescription());
			pstmt.setDate(3, Date.valueOf(article.getDateDebut()));
			pstmt.setDate(4, Date.valueOf(article.getDateFin()));
			pstmt.setInt(5, article.getPrixInitial());
			pstmt.setInt(6, article.getCategorie().getId());
			pstmt.setInt(7, article.getId());
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = cnx.prepareStatement(UPDATE_RETRAIT);
			pstmt.setString(1, article.getRetrait().getRue());
			pstmt.setString(2, article.getRetrait().getCodePostal());
			pstmt.setString(3, article.getRetrait().getVille());
			pstmt.setInt(4, article.getId());
			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_ARTICLE_ERREUR);
			throw businessException;
		}
	}
	
	@Override
	public List<Article> selectEncheresOuvertes() throws BusinessException {
		List<Article> listeArticlesEncheresEnCours = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_EN_COURS);
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeArticlesEncheresEnCours.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_EN_COURS_ERREUR);
			throw businessException;
		}
		return listeArticlesEncheresEnCours;
	}
	
	@Override
	public List<Article> selectEncheresOuvertes(String recherche, int idCategorie) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_EN_COURS_FILTRES);
			pstmt.setString(1, "%" + recherche + "%");
			pstmt.setInt(2, idCategorie);
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_EN_COURS_ERREUR);
			throw businessException;
		}
		return listeResultat;		
	}
	
	@Override
	public List<Article> selectEncheresOuvertes(String recherche) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {	
			pstmt = cnx.prepareStatement(SELECT_EN_COURS_NOM);
			pstmt.setString(1, "%" + recherche + "%");		
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_EN_COURS_ERREUR);
			throw businessException;
		}
		return listeResultat;		
	}
	
	@Override
	public List<Article> selectEncheresOuvertes(int idCategorie) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_EN_COURS_CATEGORIE);
			pstmt.setInt(1, idCategorie);
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_EN_COURS_ERREUR);
			throw businessException;
		}
		return listeResultat;		
	}
	
	
	
	@Override
	public List<Article> selectEncheresUtilisateurEnCours(int idUtilisateur) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_ENCHERES_EN_COURS_BY_UTILISATEUR_ID);
			pstmt.setInt(1, idUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_UTILISATEUR_EN_COURS_ERREUR);
			throw businessException;
		}
		return listeResultat;
	}

	@Override
	public List<Article> selectEncheresUtilisateurEnCours(int idUtilisateur, String recherche, int idCategorie) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_ENCHERES_EN_COURS_BY_UTILISATEUR_ID_FILTRES);
			pstmt.setInt(1, idUtilisateur);
			pstmt.setString(2, "%" + recherche + "%");
			pstmt.setInt(3, idCategorie);
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_UTILISATEUR_EN_COURS_ERREUR);
			throw businessException;
		}
		return listeResultat;
	}

	@Override
	public List<Article> selectEncheresUtilisateurEnCours(int idUtilisateur, String recherche)	throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_ENCHERES_EN_COURS_BY_UTILISATEUR_ID_NOM);
			pstmt.setInt(1, idUtilisateur);
			pstmt.setString(2, "%" + recherche + "%");
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_UTILISATEUR_EN_COURS_ERREUR);
			throw businessException;
		}
		return listeResultat;
	}

	@Override
	public List<Article> selectEncheresUtilisateurEnCours(int idUtilisateur, int idCategorie) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_ENCHERES_EN_COURS_BY_UTILISATEUR_ID_CATEGORIE);
			pstmt.setInt(1, idUtilisateur);
			pstmt.setInt(2, idCategorie);		
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_UTILISATEUR_EN_COURS_ERREUR);
			throw businessException;
		}
		return listeResultat;
	}
	
	
	
	@Override
	public List<Article> selectEncheresUtilisateurRemportees(int idUtilisateur) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_ENCHERES_REMPORTEES_BY_UTILISATEUR_ID);
			pstmt.setInt(1, idUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_REMPORTEES_ERREUR);
			throw businessException;
		}
		return listeResultat;	
	}
	
	@Override
	public List<Article> selectEncheresUtilisateurRemportees(int idUtilisateur, String recherche, int idCategorie) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_ENCHERES_REMPORTEES_BY_UTILISATEUR_ID_FILTRES);
			pstmt.setInt(1, idUtilisateur);
			pstmt.setString(2, "%" + recherche + "%");
			pstmt.setInt(3, idCategorie);
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_REMPORTEES_ERREUR);
			throw businessException;
		}
		return listeResultat;	
	}

	@Override
	public List<Article> selectEncheresUtilisateurRemportees(int idUtilisateur, String recherche) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_ENCHERES_REMPORTEES_BY_UTILISATEUR_ID_NOM);
			pstmt.setInt(1, idUtilisateur);
			pstmt.setString(2, "%" + recherche + "%");
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_REMPORTEES_ERREUR);
			throw businessException;
		}
		return listeResultat;	
	}

	@Override
	public List<Article> selectEncheresUtilisateurRemportees(int idUtilisateur, int idCategorie) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_ENCHERES_REMPORTEES_BY_UTILISATEUR_ID_CATEGORIE);
			pstmt.setInt(1, idUtilisateur);
			pstmt.setInt(2, idCategorie);
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ENCHERES_REMPORTEES_ERREUR);
			throw businessException;
		}
		return listeResultat;
	}

	
	@Override
	public List<Article> selectVentesUtilisateur(int idUtilisateur, int etatVente) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_VENTES_BY_UTILISATEUR_ID);
			pstmt.setInt(1, etatVente);
			pstmt.setInt(2, idUtilisateur);
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_VENTES_UTILISATEUR_ERREUR);
			throw businessException;
		}
		return listeResultat;		
	}
	
	public List<Article> selectVentesUtilisateur(int idUtilisateur, int etatVente, String recherche, int idCategorie) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_VENTES_BY_UTILISATEUR_ID_FILTRES);
			pstmt.setInt(1, etatVente);
			pstmt.setInt(2, idUtilisateur);
			pstmt.setString(3, "%" + recherche + "%");
			pstmt.setInt(4, idCategorie);
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_VENTES_UTILISATEUR_ERREUR);
			throw businessException;
		}
		return listeResultat;		
	}
	
	public List<Article> selectVentesUtilisateur(int idUtilisateur, int etatVente, String recherche) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_VENTES_BY_UTILISATEUR_ID_NOM);
			pstmt.setInt(1, etatVente);
			pstmt.setInt(2, idUtilisateur);
			pstmt.setString(3, "%" + recherche + "%");
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_VENTES_UTILISATEUR_ERREUR);
			throw businessException;
		}
		return listeResultat;		
	}
	
	public List<Article> selectVentesUtilisateur(int idUtilisateur, int etatVente, int idCategorie) throws BusinessException {
		List<Article> listeResultat = new ArrayList<>();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_VENTES_BY_UTILISATEUR_ID_CATEGORIE);
			pstmt.setInt(1, etatVente);
			pstmt.setInt(2, idUtilisateur);
			pstmt.setInt(3, idCategorie);
			ResultSet rs = pstmt.executeQuery();
			Article articleCourant = new Article();
			while (rs.next()) {
				articleCourant = articleBuilderAccueil(rs);
				listeResultat.add(articleCourant);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_VENTES_UTILISATEUR_ERREUR);
			throw businessException;
		}
		return listeResultat;		
	}
	
	
	
	public Article selectDetailArticle(int idArticle) throws BusinessException {
		Article articleResultat = new Article();
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(SELECT_DETAIL_ARTICLE);
			pstmt.setInt(1, idArticle);
			pstmt.setInt(2, idArticle);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				articleResultat = articleBuilderDetail(rs);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_DETAIL_ARTICLE_ERREUR);
			throw businessException;
		}
		return articleResultat;
	}
	
	
	
	@Override
	public void deleteArticle(int idArticle) throws BusinessException {
		PreparedStatement pstmt;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(DELETE_ARTICLE);
			pstmt.setInt(1, idArticle);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_ARTICLE_ERREUR);
			throw businessException;
		}
	}
	
	
	
	// Méthodes privées

		private Article articleBuilderAccueil(ResultSet rs) throws SQLException {
			Article article = new Article();
			article.setId(rs.getInt("id"));
			article.setNom(rs.getString("nom"));
			article.setDateFin((rs.getDate("date_fin_encheres")).toLocalDate());
			if (rs.getInt("enchere_max") == 0) {
				article.setPrixVente(rs.getInt("prix_initial"));
			} else {
				article.setPrixVente(rs.getInt("enchere_max"));
			}
			article.getCategorie().setId(rs.getInt("id_categorie"));
			article.getUtilisateur().setId(rs.getInt("id_utilisateur"));
			article.getUtilisateur().setPseudo(rs.getString("pseudo"));
			return article;
		}
		
		private Article articleBuilderDetail(ResultSet rs) throws SQLException {
			//Enchere enchereA = new Enchere();
			//enchereA.getUtilisateur().setId(2);
			Article article = new Article();
			article.setId(rs.getInt("id"));
			article.setNom(rs.getString("nom"));
			article.setDescription(rs.getString("description"));
			article.getCategorie().setId(rs.getInt("id_categorie"));
			article.getCategorie().setLibelle(rs.getString("categorie"));
			if (rs.getInt("enchere_max") != 0) {
				article.setPrixVente(rs.getInt("enchere_max"));
			}
			article.setPrixInitial(rs.getInt("prix_initial"));
			article.setDateDebut((rs.getDate("date_debut_encheres")).toLocalDate());
			article.setDateFin((rs.getDate("date_fin_encheres")).toLocalDate());
			article.getRetrait().setRue(rs.getString("rue"));
			article.getRetrait().setVille(rs.getString("ville"));
			article.getRetrait().setCodePostal(rs.getString("code_postal"));
			article.getUtilisateur().setId(rs.getInt("idVendeur"));
			article.getUtilisateur().setPseudo(rs.getString("pseudoVendeur"));
			if (rs.getInt("idEncherisseur") != 0) {
			article.getListeEncheres().add(new Enchere(rs.getInt("idEncherisseur"), rs.getString("pseudoEncherisseur"), rs.getInt("enchere_max")));
			}		
			article.setIdEtatVente(rs.getInt("id_etat_vente"));
			return article;
		}
		

}
