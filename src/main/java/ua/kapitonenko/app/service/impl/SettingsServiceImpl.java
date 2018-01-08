package ua.kapitonenko.app.service.impl;

import org.apache.log4j.Logger;
import ua.kapitonenko.app.config.Application;
import ua.kapitonenko.app.dao.connection.ConnectionPool;
import ua.kapitonenko.app.dao.interfaces.*;
import ua.kapitonenko.app.domain.records.*;
import ua.kapitonenko.app.service.SettingsService;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SettingsServiceImpl implements SettingsService {
	private static final Logger LOGGER = Logger.getLogger(SettingsService.class);
	private static SettingsServiceImpl instance = new SettingsServiceImpl();
	private ConnectionPool pool = Application.getConnectionPool();
	
	// TODO add to cache
	private HashMap<String, Object> cache = new HashMap<>();
	
	private SettingsServiceImpl() {
	}
	
	public static SettingsServiceImpl getInstance() {
		return instance;
	}
	
	@Override
	public List<UserRole> getRoleList() {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			UserRoleDAO roleDAO = Application.getDAOFactory().getUserRoleDAO(connection);
			List<UserRole> list = roleDAO.findAll();
			return list;
		} finally {
			pool.close(connection);
		}
	}
	
	@Override
	public Map<Long, String> getTaxMap() {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			TaxCategoryDAO taxCategoryDAO = Application.getDAOFactory().getTaxCategoryDAO(connection);
			List<TaxCategory> list = taxCategoryDAO.findAll();
			LOGGER.debug(list);
			return list.stream().collect(
					Collectors.toMap(BaseEntity::getId, BaseLocalizedEntity::getBundleKey));
		} finally {
			pool.close(connection);
		}
	}
	
	@Override
	public List<TaxCategory> getTaxCatList() {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			TaxCategoryDAO taxCategoryDAO = Application.getDAOFactory().getTaxCategoryDAO(connection);
			List<TaxCategory> list = taxCategoryDAO.findAll();
			return list;
		} finally {
			pool.close(connection);
		}
	}
	
	@Override
	public Map<Long, String> getUnitMap() {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			UnitDAO unitDAO = Application.getDAOFactory().getUnitDAO(connection);
			List<Unit> list = unitDAO.findAll();
			//LOGGER.debug(list);
			return list.stream().collect(
					Collectors.toMap(BaseEntity::getId, BaseLocalizedEntity::getBundleKey));
		} finally {
			pool.close(connection);
		}
	}
	
	@Override
	public List<Unit> getUnitList() {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			UnitDAO unitDAO = Application.getDAOFactory().getUnitDAO(connection);
			List<Unit> list = unitDAO.findAll();
			return list;
		} finally {
			pool.close(connection);
		}
	}
	
	@Override
	public Cashbox findCashbox(Long id) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			CashboxDAO cashboxDAO = Application.getDAOFactory().getCashboxDao(connection);
			return cashboxDAO.findOne(id);
		} finally {
			pool.close(connection);
		}
	}
	
	@Override
	public PaymentType findPaymentType(Long id) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PaymentTypeDAO paymentTypeDAO = Application.getDAOFactory().getPaymentTypeDAO(connection);
			return paymentTypeDAO.findOne(id);
		} finally {
			pool.close(connection);
		}
	}
	
	@Override
	public List<LocaleRecord> getLocaleList() {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			LocaleDAO localeDAO = Application.getDAOFactory().getLocaleDAO(connection);
			
			List<LocaleRecord> list = localeDAO.findAll();
			
			return list;
		} finally {
			pool.close(connection);
		}
	}
	
	@Override
	public List<String> getSupportedLanguages() {
		return getSupportedLocales().keySet().stream().sorted().collect(Collectors.toList());
	}
	
	@Override
	public Map<String, LocaleRecord> getSupportedLocales() {
		return getLocaleList().stream()
				       .collect(Collectors.toMap(LocaleRecord::getLanguage, Function.identity()));
	}
	
	@Override
	public Company findCompany(Long id) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			CompanyDAO companyDAO = Application.getDAOFactory().getCompanyDAO(connection);
			return companyDAO.findOne(id);
		} finally {
			pool.close(connection);
		}
	}
	
	@Override
	public List<PaymentType> getPaymentTypes() {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			PaymentTypeDAO paymentTypeDAO = Application.getDAOFactory().getPaymentTypeDAO(connection);
			
			List<PaymentType> list = paymentTypeDAO.findAll();
			
			return list;
		} finally {
			pool.close(connection);
		}
	}
	
	@Override
	public List<Cashbox> getCashboxList() {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			CashboxDAO cashboxDAO = Application.getDAOFactory().getCashboxDao(connection);
			
			List<Cashbox> list = cashboxDAO.findAll();
			
			return list;
		} finally {
			pool.close(connection);
		}
	}
}
