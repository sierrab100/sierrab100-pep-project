package Service;
import Model.Account;
import DAO.AccountsDAO;

public class AccountsService {
    
    private AccountsDAO accountsDAO;

    public AccountsService(){
    accountsDAO = new AccountsDAO();
    }

    public AccountsService(AccountsDAO accountsDAO){
        this.accountsDAO = accountsDAO;
    }
 
    public Account addAccount(Account account) {
        Account test = accountsDAO.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (test != null || account.getUsername() == "" || account.getPassword().length() < 4){
            return null;
        }
        
        Account result = accountsDAO.insertAccount(account);
        return result;
    }
 
    public Account login (Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        return accountsDAO.getAccountByUsernameAndPassword(username, password);
    }

}
