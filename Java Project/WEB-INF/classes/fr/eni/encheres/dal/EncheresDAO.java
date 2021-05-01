package fr.eni.encheres.dal;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Enchere;

public interface EncheresDAO {
	
	public void insertEnchere(Enchere enchere) throws BusinessException;

 }
