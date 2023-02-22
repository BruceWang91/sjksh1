package datart.core.mappers;

import datart.core.entity.VisitorStatistics;
import org.apache.ibatis.jdbc.SQL;

public class VisitorStatisticsSqlProvider {
    public String insertSelective(VisitorStatistics record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("visitor_statistics");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id}");
        }
        if (record.getModuleName() != null) {
            sql.VALUES("module_name", "#{moduleName}");
        }
        if (record.getType() != null) {
            sql.VALUES("type", "#{type}");
        }
        if (record.getNum() != null) {
            sql.VALUES("num", "#{num}");
        }
        return sql.toString();
    }
}
