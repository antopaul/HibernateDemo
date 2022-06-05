package test.hibernate.interceptor;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class DAOInterceptor extends EmptyInterceptor {
	@Override
    public String onPrepareStatement(String sql) {
		System.out.println("TenantDAOInterceptor: " + sql);
		
        return sql;
    }
	
}
