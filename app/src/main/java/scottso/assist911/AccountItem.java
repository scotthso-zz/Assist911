package scottso.assist911;

public class AccountItem {

    private String accountName;
    private int accountTries;
    private int accountTimesOpened;
    private int highScore;

    public AccountItem(String name, int tries, int timesOpened, int highScore) {
        this.accountName = name;
        this.accountTries = tries;
        this.accountTimesOpened = timesOpened;
        this.highScore = highScore;
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


