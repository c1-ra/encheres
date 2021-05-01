package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;

public class UtilisateursDAOJdbcImpl implements UtilisateursDAO {
	
	private static final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	private static final String SELECT_ALL_UTILISATEURS = "SELECT * FROM UTILISATEURS;";
	
	private static final String SELECT_ALL_UTILISATEURS_EXCEPT_ONE = "SELECT * FROM UTILISATEURS WHERE NOT id=?;";
	
	private static final String SELECT_UTILISATEUR_BY_ID = "SELECT * FROM UTILISATEURS WHERE id = ?;";
	
	private static final String CONNEXION = "SELECT * FROM UTILISATEURS WHERE pseudo = ? OR email = ?;";
	
	private static final String SELECT_UTILISATEUR_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo = ?;";
	
	private static final String UPDATE_UTILISATEUR = "UPDATE UTILISATEURS SET pseudo=?, nom=?, prenom=?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=? WHERE id=?;";

	private static final String DELETE_UTILISATEUR = "DELETE FROM UTILISATEURS WHERE id=?;";
	
	// requêtes pour vérifications
	private static final String COUNT_PSEUDO = "SELECT COUNT(pseudo) FROM UTILISATEURS WHERE pseudo=? AND id<>?;";
	
	private static final String COUNT_EMAIL = "SELECT COUNT(email) FROM UTILISATEURS WHERE email=? AND id<>?;";	
	
	@Override
	public void insertUtilisateur(Utilisateur utilisateur) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(INSERT_UTILISATEUR, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setString(6, utilisateur.getRue());
			pstmt.setString(7, utilisateur.getCodePostal());
			pstmt.setString(8, utilisateur.getVille());
			pstmt.setString(9, utilisateur.getMotDePasse());
			pstmt.setInt(10, utilisateur.getCredit());
			pstmt.setBoolean(11, utilisateur.isAdministrateur());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				utilisateur.setId(rs.getInt(1));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_UTILISATEUR_ERREUR);
			throw businessException;
		}
	}
	
	@Override
	public List<Utilisateur> selectAllUtilisateurs() throws BusinessException {
		List<Utilisateur> listeUtilisateurs = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_UTILISATEURS);
			ResultSet rs = pstmt.executeQuery();
			Utilisateur utilisateurCourant = new Utilisateur();
			while (rs.next()) {
				utilisateurCourant = utilisateurBuilder(rs);
				listeUtilisateurs.add(utilisateurCourant);
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_UTILISATEURS_ERREUR);
			throw businessException;
		}
		return listeUtilisateurs;
	}
	
	public List<Utilisateur> selectAllUtilisateursExceptOne(int id) throws BusinessException {
		List<Utilisateur> listeUtilisateurs = new ArrayList<>();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL_UTILISATEURS_EXCEPT_ONE);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			Utilisateur utilisateurCourant = new Utilisateur();
			while (rs.next()) {
				utilisateurCourant = utilisateurBuilder(rs);
				listeUtilisateurs.add(utilisateurCourant);
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_ALL_UTILISATEURS_EXCEPT_ONE_ERREUR);
			throw businessException;
		}
		return listeUtilisateurs;
	}
	
	@Override
	public Utilisateur selectUtilisateurById(int id) throws BusinessException {
		Utilisateur utilisateur = new Utilisateur();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_UTILISATEUR_BY_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {				
				utilisateur = utilisateurBuilder(rs);
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_UTILISATEUR_BY_ID_ERREUR);
			throw businessException;
		}
		return utilisateur;
	}
	
	@Override
	public Utilisateur selectUtilisateurByLogin(String login) throws BusinessException {
		Utilisateur utilisateur = new Utilisateur();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(CONNEXION);
			pstmt.setString(1, login);
			pstmt.setString(2, login);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {				
				utilisateur = utilisateurBuilder(rs);
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_UTILISATEUR_BY_LOGIN_ERREUR);
			throw businessException;
		}
		return utilisateur;
	}
	
	@Override
	public Utilisateur selectUtilisateurByPseudo(String pseudo) throws BusinessException {
		Utilisateur utilisateur = new Utilisateur();
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_UTILISATEUR_BY_PSEUDO);
			pstmt.setString(1, pseudo);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {				
				utilisateur = utilisateurBuilder(rs);
			}
			rs.close();
			pstmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SELECT_UTILISATEUR_BY_PSEUDO_ERREUR);
			throw businessException;
		}
		return utilisateur;
	}
	
	@Override
	public void updateUtilisateur(int id, Utilisateur utilisateur) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_UTILISATEUR);
			pstmt.setString(1, utilisateur.getPseudo());
			pstmt.setString(2, utilisateur.getNom());
			pstmt.setString(3, utilisateur.getPrenom());
			pstmt.setString(4, utilisateur.getEmail());
			pstmt.setString(5, utilisateur.getTelephone());
			pstmt.setString(6, utilisateur.getRue());
			pstmt.setString(7, utilisateur.getCodePostal());
			pstmt.setString(8, utilisateur.getVille());
			pstmt.setString(9, utilisateur.getMotDePasse());
			pstmt.setInt(10, utilisateur.getId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_UTILISATEUR_ERREUR);
			throw businessException;
		}
	}
	
	@Override
	public void deleteUtilisateur(int id) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_UTILISATEUR);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_UTILISATEUR_ERREUR);
			throw businessException;
		}
	}
	
	
	// Pour vérifications
	
	@Override
	public boolean presencePseudo(String pseudo, int id) throws BusinessException {
		boolean present = true;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(COUNT_PSEUDO);
			pstmt.setString(1, pseudo);
			pstmt.setInt(2, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {				
				int count = rs.getInt(1);
				if (count == 0) {
					present = false;
				}
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.COUNT_PSEUDO_ERREUR);
			throw businessException;
		}
		return present;
	}
	
	@Override
	public boolean presenceEmail(String email, int id) throws BusinessException {
		boolean present = true;
		try(Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(COUNT_EMAIL);
			pstmt.setString(1, email);
			pstmt.setInt(2, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {				
				int count = rs.getInt(1);
				if (count == 0) {
					present = false;
				}
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.COUNT_EMAIL_ERREUR);
			throw businessException;
		}
		return present;
	}
	
	
	// Méthodes privées
	private Utilisateur utilisateurBuilder(ResultSet rs) throws SQLException {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setId(rs.getInt("id"));
		utilisateur.setPseudo(rs.getString("pseudo"));
		utilisateur.setNom(rs.getString("nom"));
		utilisateur.setPrenom(rs.getString("prenom"));
		utilisateur.setEmail(rs.getString("email"));
		utilisateur.setTelephone(rs.getString("telephone"));
		utilisateur.setRue(rs.getString("rue"));
		utilisateur.setCodePostal(rs.getString("code_postal"));
		utilisateur.setVille(rs.getString("ville"));
		utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
		utilisateur.setCredit(rs.getInt("credit"));
		utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
		return utilisateur;
	}

}
