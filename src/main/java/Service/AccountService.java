package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        if(account.getPassword().length() >= 4 && (account.getUsername() != "" && account.getUsername() != null)) {
            if(accountDAO.insertAccount(account) != null) {
                return account;
            }
        }
        return null;
    }

    public Account loginAccount(Account account) {
        if(account.getPassword().length() >= 4 && (account.getUsername() != "" && account.getUsername() != null)) {
            if(accountDAO.getAccount(account) != null) {
                return account;
            }
        }
        return null;
    }
}
