package pojo.database;

public class ConversionsDB {

    private int id;
    private String type;
    private String from;
    private String to;
    private String formula;
    private String value;

    public ConversionsDB(int id, String type, String from,
                         String to, String formula, String value) {
        this.id = id;
        this.type = type;
        this.from = from;
        this.to = to;
        this.formula = formula;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
