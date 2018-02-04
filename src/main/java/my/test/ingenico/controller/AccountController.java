package my.test.ingenico.controller;

import java.math.BigDecimal;
import my.test.ingenico.model.Account;
import my.test.ingenico.model.AccountRepository;
import my.test.ingenico.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Account controller for the application.
 * Normally I would move all parameters in POST request to the body of request.
 * In this case I made them as path variables for convenience using from console with curl
 */
@RestController
@RequestMapping("/account")
@Scope("request")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferService transferService;

    @RequestMapping
    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @RequestMapping(value = "/create/{name}/{balance}", method = RequestMethod.POST)
    public Account createAccount(@PathVariable String name, @PathVariable String balance) {
        return transferService.createAccount(name, new BigDecimal(balance).setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
