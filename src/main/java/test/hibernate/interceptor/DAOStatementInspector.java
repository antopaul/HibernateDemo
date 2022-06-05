package test.hibernate.interceptor;

import org.hibernate.resource.jdbc.spi.StatementInspector;

public class DAOStatementInspector implements StatementInspector {

	@Override
	public String inspect(String sql) {
		debug("Executing SQL: " + sql);
		debug("Final SQL:     " + sql);
		return sql;
	}

	private static void debug(String m) {
		System.out.println(m);
	}
}
