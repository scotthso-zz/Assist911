package scottso.assist911;

public class AccountItem {

    private String accountName;
    private int accountTries;
    private int accountTimesOpened;
    private int highScore;
    private String address;

    public AccountItem(String name, int tries, int timesOpened, int highScore, String address) {
        this.accountName = name;
        this.accountTries = tries;
        this.accountTimesOpened = timesOpened;
        this.highScore = highScore;
        this.address = address;
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

    public int getHighScore() {
        return highScore;
    }

    public String getAddress() {
        return address;
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

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}


