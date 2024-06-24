package app.entities;

public class Commodity {

    private String cpc_code;
    private String descriptor;
    private String group_code;

    public Commodity(String cpc_code, String descriptor, String group_code) {
        this.cpc_code = cpc_code;
        this.descriptor = descriptor;
        this.group_code = group_code;
    }

    public Commodity() {
    }

    public String getCpc_code() {
        return cpc_code;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public String getGroup_code() {
        return group_code;
    }

    public void setCpc_code(String cpc_code) {
        this.cpc_code = cpc_code;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }

}
