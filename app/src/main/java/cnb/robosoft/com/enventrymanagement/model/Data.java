package cnb.robosoft.com.enventrymanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by deena on 15/4/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data{
    private String expendDesc;
    private String expendAmount;
    private String expendDate;

    public Data() {
    }

    public Data(String expendDesc, String expendAmount, String expendDate) {
        this.expendDesc = expendDesc;
        this.expendAmount = expendAmount;
        this.expendDate = expendDate;
    }

    public String getExpendDesc() {
        return expendDesc;
    }

    public void setExpendDesc(String expendDesc) {
        this.expendDesc = expendDesc;
    }

    public String getExpendAmount() {
        return expendAmount;
    }

    public void setExpendAmount(String expendAmount) {
        this.expendAmount = expendAmount;
    }

    public String getExpendDate() {
        return expendDate;
    }

    public void setExpendDate(String expendDate) {
        this.expendDate = expendDate;
    }
}