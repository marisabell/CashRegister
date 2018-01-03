package ua.kapitonenko.dao.interfaces;


import ua.kapitonenko.domain.entities.ProductLocale;

import java.util.List;

public interface ProductLocaleDAO extends DAO<ProductLocale> {

	List<ProductLocale> findByProductAndKey(Long id, String key);
}