package persistence;

import domain.FieldWormType;
import domain.WormColumn;

import java.lang.annotation.Annotation;
import java.sql.Time;
import java.util.Date;

public class QueryBuilder implements persistence.contracts.QueryBuilder.CRUD, persistence.contracts.QueryBuilder.Validator {

    private final String CREATE = "CREATE";
    private final String SELECT = "SELECT";
    private final String FROM = "FROM";
    private final String INSERT_INTO = "INSERT INTO";
    private final String UPDATE = "UPDATE";
    private final String DELETE = "DELETE";
    private final String VALUES = "VALUES";
    private final String WHERE = "WHERE";
    private final String SET = "SET";
    private final String AND = "AND";



    @Override
    public String createDB(String dataBaseName) {

        StringBuilder query = new StringBuilder("");

        query
            .append(CREATE)
            .append(" DATABASE ")
            .append(dataBaseName);

        return query.toString();

    }

    @Override
    public String createTable(String tableName, String[] attributesNames) {
        StringBuilder query = new StringBuilder("");

        query
            .append(CREATE)
            .append(" TABLE ")
            .append(tableName);

        return query.toString();
    }



    @Override
    public String insertEntity(String tableName, FieldWormType[] attributes) {
        StringBuilder query = new StringBuilder("");

        query
            .append(INSERT_INTO)
            .append(" ")
            .append(tableName).append(" (");

        for (int j=1;j<attributes.length;j++){
            query
                .append(
                        getColumnName(attributes[j])
                )
                .append(",");
        }

        query.deleteCharAt(query.length()-1);
        query
            .append(") ")
            .append(VALUES)
            .append(" (");

        for (int j=1;j<attributes.length;j++){

            switch (attributes[j].getValue().getClass().getName()){
                case "java.util.Date":
                    Date date = Date.class.cast(attributes[j].getValue());

                    query
                        .append("'")
                        .append(date.getYear()+1900)
                        .append("-")
                        .append(date.getMonth()+1)
                        .append("-")
                        .append(date.getDate()+1)
                        .append("'")
                        .append(",");
                    break;
                case "java.sql.Time":
                    Time time = Time.class.cast(attributes[j].getValue());

                    query
                        .append("'")
                        .append(time.getHours())
                        .append(":")
                        .append(time.getMinutes())
                        .append(":")
                        .append(time.getSeconds())
                        .append("'")
                        .append(",");
                    break;
                case "java.lang.String":
                    query
                        .append("'")
                        .append(attributes[j].getValue())
                        .append("'")
                        .append(",");
                    break;
                default:
                    query
                        .append(attributes[j].getValue())
                        .append(",");
            }

        }

        query.deleteCharAt(query.length()-1);
        query.append(")");

        return query.toString();
    }

    @Override
    public String updateEntity(String tableName, FieldWormType[] attributes) {
        StringBuilder query = new StringBuilder("");

        query
            .append(UPDATE)
            .append(" ")
            .append(tableName)
            .append(" ")
            .append(SET).append(" ");

        for (int i=0;i<attributes.length;i++){
            query
                .append(
                        getColumnName(attributes[i])
                )
                .append(" = ");

            switch (attributes[i].getValue().getClass().getName()){
                case "java.util.Date":
                    Date date = Date.class.cast(attributes[i].getValue());

                    query
                            .append("'")
                            .append(date.getYear()+1900)
                            .append("-")
                            .append(date.getMonth()+1)
                            .append("-")
                            .append(date.getDate()+1)
                            .append("'");
                    break;
                case "java.sql.Time":
                    Time time = Time.class.cast(attributes[i].getValue());

                    query
                            .append("'")
                            .append(time.getHours())
                            .append(":")
                            .append(time.getMinutes())
                            .append(":")
                            .append(time.getSeconds())
                            .append("'");
                    break;
                case "java.lang.String":
                    query
                            .append("'")
                            .append(attributes[i].getValue())
                            .append("'");
                    break;
                default:
                    query.append(attributes[i].getValue());
            }
                query.append(",");
        }

        query.deleteCharAt(query.length()-1);
        query
            .append(" ")
            .append(WHERE)
            .append(" ")
            .append(tableName)
            .append(".")
            .append(attributes[0].getFieldName()) //objectId
            .append("=")
            .append(attributes[0].getValue());

        return query.toString();
    }

    @Override
    public String deleteEntity(String tableName, String idFieldName, int id) {

        StringBuilder query = new StringBuilder("");

        query
            .append(DELETE)
            .append(" ")
            .append(FROM)
            .append(" ")
            .append(tableName)
            .append(" ")
            .append(WHERE)
            .append(" ")
            .append(tableName)
            .append(".")
            .append(idFieldName)
            .append("=")
            .append(id);


        return query.toString();
    }

    @Override
    public String findEntity(String tableName, String idFieldName, int id) {
        StringBuilder query = new StringBuilder("");

        query
            .append(SELECT)
            .append(" * ")
            .append(FROM)
            .append(" ")
            .append(tableName)
            .append(" ")
            .append(WHERE)
            .append(" ")
            .append(tableName)
            .append(".")
            .append(idFieldName)
            .append("=")
            .append(id);

        return query.toString();
    }

    @Override
    public String allEntities(String tableName) {
        StringBuilder query = new StringBuilder("");

        query
            .append(SELECT)
            .append(" * ")
            .append(FROM)
            .append(" ")
            .append(tableName);

        return query.toString();
    }



    @Override
    public String getTableFieldsNamesAndTypes(String DBName, String tableName) {
        StringBuilder query = new StringBuilder("");

        query
                .append(SELECT)
                .append(" DATA_TYPE ")
                .append(FROM)
                .append(" INFORMATION_SCHEMA.COLUMNS ")
                .append(WHERE)
                .append(" TABLE_SCHEMA = ")
                .append("'")
                .append(DBName)
                .append("'")
                .append(AND)
                .append(" table_name = ")
                .append("'")
                .append(tableName)
                .append("'");

        return query.toString();
    }

    @Override
    public String existTable(String DBName, String tableName) {
        StringBuilder query = new StringBuilder("");

        query
                .append(SELECT)
                .append(" table_name ")
                .append(FROM)
                .append(" information_schema.tables ")
                .append(WHERE)
                .append(" table_schema = ")
                .append("'")
                .append(DBName)
                .append("'")
                .append(AND)
                .append(" table_name = ")
                .append("'")
                .append(tableName)
                .append("'");


        return query.toString();
    }

    @Override
    public String existRow(String tableName, String idFieldName, int id) {
        StringBuilder query = new StringBuilder("");

        query
                .append(SELECT)
                .append(" * ")
                .append(FROM)
                .append(" ")
                .append(tableName)
                .append(" ")
                .append(WHERE)
                .append(" ")
                .append(tableName)
                .append(".")
                .append(idFieldName)
                .append("=")
                .append(id);

        return query.toString();
    }

    @Override
    public String existDB(String dataBaseName) {
        StringBuilder query = new StringBuilder("");

        query
                .append(SELECT)
                .append(" ")
                .append("SCHEMA_NAME")
                .append(" ")
                .append(FROM)
                .append(" ")
                .append("INFORMATION_SCHEMA.SCHEMATA")
                .append(" ")
                .append(WHERE)
                .append(" ")
                .append("SCHEMA_NAME")
                .append(" ")
                .append("=")
                .append(" ")
                .append(dataBaseName);

        return query.toString();
    }
  
    private String getColumnName(FieldWormType field) {
        Annotation annotation = field.getAnnotation();
        boolean hasWormTableAnnotation = annotation != null && annotation instanceof WormColumn;
        if (hasWormTableAnnotation) {
            return ((WormColumn) annotation).value();
        }
        return field.getFieldName();
    }
}
