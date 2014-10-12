package scottso.assist911;

public class AccountItem {

    private String accountName;
    private int accountTries;
    private int accountTimesOpened;

    public AccountItem(String name, int tries, int timesOpened) {
        this.accountName = name;
        this.accountTries = tries;
        this.accountTimesOpened = timesOpened;
    }

    public String getAccountName() {
        return accountName;
    }

    public int getAccountTries() {
        return accountTries;
    }

    public int getAccountTimesOpened() {
        return accountTimesOpened;
    }

    public void setAccountName (String name) {
        this.accountName = name;
    }

    public void setAccountTries (int tries) {
        this.accountTries = tries;
    }

    public void setAccountTimesOpened(int accountTimesOpened) {
        this.accountTimesOpened = accountTimesOpened;
    }
}


