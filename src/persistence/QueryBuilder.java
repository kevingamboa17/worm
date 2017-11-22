package persistence;

import domain.FieldWormType;

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

        for (int j=0;j<attributes.length;j++){
            query
                .append(attributes[j].getFieldName())
                .append(",");
        }

        query.deleteCharAt(query.length()-1);
        query
            .append(") ")
            .append(VALUES)
            .append(" (");

        for (int j=0;j<attributes.length;j++){

            if(attributes[j].getOriginalType().getTypeName() == "java.lang.String"){
                query
                    .append("'")
                    .append(attributes[j].getValue())
                    .append("'")
                    .append(",");
            }else{
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
                .append(attributes[i].getFieldName())
                .append(" = ");
                if(attributes[i].getOriginalType().getTypeName() == "java.lang.String"){
                    query
                        .append("'")
                        .append(attributes[i].getValue())
                        .append("'");
                }else{
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
}
