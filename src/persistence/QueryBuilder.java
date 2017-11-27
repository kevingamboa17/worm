package persistence;

import domain.FieldWormType;
import domain.WormColumn;

import java.lang.annotation.Annotation;
import java.sql.Time;
import java.util.Date;

/**
 * The QueryBuilder is a helper that its responsibility
 * is generate the queries that do different task if they are executed
 */
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
    private final String NULL = "NULL";
    private final String PRIMARY_KEY = "PRIMARY KEY";
    private final String INT = "INT";
    private final String NOT = "NOT";
    private final String AUTO_INCREMENT = "AUTO_INCREMENT";

    /**
     * Method that build a query that create a database if is executed
     * @param dataBaseName name of the database that will be created
     * @return a sentence that will create a database
     */
    @Override
    public String createDB(String dataBaseName) {

        StringBuilder query = new StringBuilder("");

        query
            .append(CREATE)
            .append(" DATABASE ")
            .append(dataBaseName);

        return query.toString();

    }

    /**
     * Method that build a query that create a table if is executed
     * @param tableName name of the table that will be created
     * @param attributesNames model data to create a table with its specifications
     * @return a sentence that will create a table
     */
    @Override
    public String createTable(String tableName, FieldWormType[] attributesNames) {
        StringBuilder query = new StringBuilder("");

        query
            .append(CREATE)
            .append(" TABLE ")
            .append(tableName)
            .append(" (")
            .append(getColumnName(attributesNames[0]))
            .append(" ")
            .append(INT)
            .append(" ")
            .append(NOT)
            .append(" ")
            .append(NULL)
            .append(" ")
            .append(AUTO_INCREMENT)
            .append(",");

        for(int i=1;i<attributesNames.length;i++){
            query
                .append(getColumnName(attributesNames[i]))
                .append(" ")
                .append(attributesNames[i].getDatabaseType())
                .append(" ")
                .append(NULL)
                .append(",");
        }

        query
            .append(PRIMARY_KEY)
            .append(" (")
            .append(getColumnName(attributesNames[0]))
            .append(")")
            .append(");");



        return query.toString();
    }

    /**
     * Method that build a query that insert a row in a specific table if is executed
     * @param tableName name of the table where will insert a new row
     * @param attributes model data that will be inserted into table
     * @return a sentence that will insert a new row in a table
     */
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

    /**
     * Method that build a query that update the data of a row in a specific table if is executed
     * @param tableName name of the table where will update a row existing
     * @param attributes model data that will update a row existing
     * @return a sentence that will update a row existing in a specific table
     */
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

    /**
     * Method that build a query that delete a row existing in a specific table if is executed
     * @param tableName name of the table where will delete a row existing
     * @param idFieldName column name of a table where is the id of the object
     * @param id number id of the row that will be deleted
     * @return a sentence that will delete a row existing in a specific table
     */
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

    /**
     * Method that build a query that return a row existing in a specific table if is executed
     * @param tableName name of the table where will search a row
     * @param idFieldName column name of a table where is the id of the object
     * @param id number id of the row that want to retrieve
     * @return a sentence that will return a row with the id specified
     */
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

    /**
     * Method that build a query that return all row existing in a specific table if is executed
     * @param tableName name of the table where will search all row
     * @return a sentence that will return all row
     */
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

    /**
     * Method that build a query that return the name and types of the columns of a specific table
     * @param DBName name of the database where is the table
     * @param tableName name of the table that need the names and types
     * @return a sentence that will return the names and types of the columns of the table specified
     */
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

    /**
     * Method that build a query that return a table of a specific database
     * @param DBName name of the database where is the table
     * @param tableName name of the table that need to search
     * @return a sentence that will return a table specified
     */
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

    /**
     * Method that build a query that return a row existing in a table
     * @param tableName name of the table where will research
     * @param idFieldName column name where is the id of the table
     * @param id number id of the row that need find
     * @return a sentence that will return a row specified
     */
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

    /**
     * Method that build a query that return a specific database
     * @param dataBaseName name of database that need find
     * @return a sentence that will return a database specified
     */
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
                .append("'")
                .append(dataBaseName)
                .append("'");

        return query.toString();
    }

    /**
     * Method that return the column name, the name of the attribute model or the annotation wrote
     * @param field attribute of the model
     * @return the name of the column in table
     */
    private String getColumnName(FieldWormType field) {
        Annotation annotation = field.getAnnotation();
        boolean hasWormTableAnnotation = annotation != null && annotation instanceof WormColumn;
        if (hasWormTableAnnotation) {
            return ((WormColumn) annotation).value();
        }
        return field.getFieldName();
    }
}
