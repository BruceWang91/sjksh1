package datart.server.config.datasource;

import datart.server.common.SpringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 动态数据源
 *
 * @author wangya
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal dataSource = ThreadLocal.withInitial(() -> (DataSource) SpringUtils.getBean("defaultDataSource"));

    public static void setDataSource(DataSource dataSource) {

        DynamicDataSource.dataSource.set(dataSource);
    }

    public static DataSource getDataSource() {

        return (DataSource) DynamicDataSource.dataSource.get();
    }

    @Override
    protected Object determineCurrentLookupKey() {

        return null;
    }

    @Override
    protected DataSource determineTargetDataSource() {

        return getDataSource();
    }

    public static void clear() {

        DynamicDataSource.dataSource.remove();
    }
}