package scottso.assist911;

/**
 * Created by scottso on 2014-09-07.
 */
public class ListAccountItem {

    private String accountName;
    private int accountAge;

    public ListAccountItem(String name, int age) {
        this.accountName = name;
        this.accountAge = age;
    }

    public String getAccountName() {return accountName;}

    public int getAccountAge() {return accountAge;}

    public void setAccountName (String name) {this.accountName = name;}

    public void setAccountAge (int age) {this.accountAge = age;}

}


