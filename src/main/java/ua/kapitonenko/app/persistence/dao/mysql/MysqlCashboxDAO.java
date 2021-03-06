package ua.kapitonenko.app.persistence.dao.mysql;

import ua.kapitonenko.app.persistence.dao.CashboxDAO;
import ua.kapitonenko.app.persistence.dao.mysql.helpers.PreparedStatementSetter;
import ua.kapitonenko.app.persistence.dao.mysql.helpers.ResultSetExtractor;
import ua.kapitonenko.app.persistence.records.Cashbox;
import ua.kapitonenko.app.persistence.tables.BaseTable;
import ua.kapitonenko.app.persistence.tables.CashboxesTable;

import java.sql.Connection;
import java.util.List;

public class MysqlCashboxDAO extends BaseDAO<Cashbox> implements CashboxDAO {
	
	private static final String UPDATE = "UPDATE " +
			                                     CashboxesTable.NAME + " SET " +
			                                     CashboxesTable.FN_NUMBER + " = ?, " +
			                                     CashboxesTable.ZN_NUMBER + " = ?, " +
			                                     CashboxesTable.MAKE + " = ? " +
			                                     WHERE_ID;
	
	private static final String DELETE = "UPDATE " +
			                                     CashboxesTable.NAME + " SET " +
			                                     CashboxesTable.DELETED_AT + " = NOW(), " +
			                                     CashboxesTable.DELETED_BY + " = ?" + WHERE_ID;
	
	private static final String INSERT = "INSERT INTO " +
			                                     CashboxesTable.NAME + " (" +
			                                     CashboxesTable.FN_NUMBER + ", " +
			                                     CashboxesTable.ZN_NUMBER + ", " +
			                                     CashboxesTable.MAKE + ") VALUES (?, ?, ? )";
	
	MysqlCashboxDAO(Connection connection) {
		super(connection);
	}
	
	@Override
	protected String getTableName() {
		return CashboxesTable.NAME;
	}
	
	@Override
	protected String getInsertQuery() {
		return INSERT;
	}
	
	
	@Override
	protected String getUpdateQuery() {
		return UPDATE;
	}
	
	public Cashbox findOne(final Long id) {
		return getRow(getSelectOneNotDeletedQuery(),
				ps -> ps.setLong(1, id),
				getResultSetExtractor());
	}
	
	public List<Cashbox> findAll() {
		return getList(getSelectAllNotDeletedQuery(), getResultSetExtractor());
	}
	
	@Override
	protected PreparedStatementSetter getInsertStatementSetter(final Cashbox entity) {
		return ps -> {
			ps.setString(1, entity.getFnNumber());
			ps.setString(2, entity.getZnNumber());
			ps.setString(3, entity.getMake());
		};
	}
	
	@Override
	protected PreparedStatementSetter getUpdateStatementSetter(final Cashbox entity) {
		return ps -> {
			ps.setString(1, entity.getFnNumber());
			ps.setString(2, entity.getZnNumber());
			ps.setString(3, entity.getMake());
			ps.setLong(4, entity.getId());
		};
	}
	
	@Override
	public boolean delete(final Cashbox entity, Long userId) {
		entity.setDeletedBy(userId);
		int result = executeUpdate(DELETE, ps -> {
			ps.setLong(1, entity.getDeletedBy());
			ps.setLong(2, entity.getId());
		});
		return result > 0;
	}
	
	@Override
	protected ResultSetExtractor<Cashbox> getResultSetExtractor() {
		return rs -> {
			Cashbox row = new Cashbox();
			row.setId(rs.getLong(BaseTable.ID));
			row.setFnNumber(rs.getString(CashboxesTable.FN_NUMBER));
			row.setZnNumber(rs.getString(CashboxesTable.ZN_NUMBER));
			row.setMake(rs.getString(CashboxesTable.MAKE));
			row.setDeletedAt(rs.getTimestamp(CashboxesTable.DELETED_AT));
			row.setDeletedBy(rs.getLong(CashboxesTable.DELETED_BY));
			return row;
		};
	}
	
}