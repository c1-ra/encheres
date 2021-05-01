package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;

public class EncheresDAOJdbcImpl implements EncheresDAO {
	
	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES VALUES (?, ?, ?, ?);";
	
	// requêtes utilitaires et de vérification

	private static final String DERNIERE_ENCHERE_ARTICLE_ID = "SELECT id_utilisateur, montant_enchere" + 
			"	FROM ENCHERES" + 
			"	WHERE id_article = ? AND montant_enchere = (SELECT MAX(montant_enchere) FROM ENCHERES WHERE id_article = ?);";
	
	private static final String CREDITER_UTILISATEUR_ID = "UPDATE UTILISATEURS SET credit += ? WHERE id = ?";
	
	private static final String DEBITER_UTILISATEUR_ID = "UPDATE UTILISATEURS SET credit -= ? WHERE id = ?";

	@Override
	public void insertEnchere(Enchere enchere) throws BusinessException {
	try(Connection cnx = ConnectionProvider.getConnection()) {
			
			cnx.setAutoCommit(false);
			
			try {
				int idMeilleurEncherisseur = 0;
				int montantMeilleurEncherisseur = 0;
				
				PreparedStatement pstmt = cnx.prepareStatement(DEBITER_UTILISATEUR_ID);
				pstmt.setInt(1, enchere.getMontantEnchere());
				pstmt.setInt(2, enchere.getUtilisateur().getId());
				pstmt.executeUpdate();
				pstmt.close();
				
				pstmt = cnx.prepareStatement(DERNIERE_ENCHERE_ARTICLE_ID);
				pstmt.setInt(1, enchere.getArticle().getId());
				pstmt.setInt(2, enchere.getArticle().getId());
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					idMeilleurEncherisseur = rs.getInt("id_utilisateur");
					montantMeilleurEncherisseur = rs.getInt("montant_enchere");
				}
				rs.close();
				pstmt.close();
				
				if (idMeilleurEncherisseur != 0) {
					pstmt = cnx.prepareStatement(CREDITER_UTILISATEUR_ID);
					pstmt.setInt(1, montantMeilleurEncherisseur);
					pstmt.setInt(2, idMeilleurEncherisseur);
					pstmt.executeUpdate();
					pstmt.close();
				}
				
				pstmt = cnx.prepareStatement(INSERT_ENCHERE);
				pstmt.setInt(1, enchere.getUtilisateur().getId());
				pstmt.setInt(2, enchere.getArticle().getId());
				pstmt.setDate(3, Date.valueOf(LocalDate.now()));
				pstmt.setInt(4, enchere.getMontantEnchere());		
				pstmt.executeUpdate();
				pstmt.close();
				
				cnx.commit();
				
			} catch (Exception e) {
				e.printStackTrace();
				cnx.rollback();
				throw e;
			}
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_ENCHERE_ERREUR);
			throw businessException;
		}
		
	}
	
	
}