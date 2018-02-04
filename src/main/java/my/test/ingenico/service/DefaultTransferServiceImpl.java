package my.test.ingenico.service;

import java.math.BigDecimal;
import my.test.ingenico.exception.MyApplicationException;
import my.test.ingenico.model.Account;
import my.test.ingenico.model.AccountRepository;
import my.test.ingenico.model.Transfer;
import my.test.ingenico.model.Transfer.Status;
import my.test.ingenico.model.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope("request")
public class DefaultTransferServiceImpl implements TransferService {

    private final static Logger logger = LoggerFactory.getLogger(DefaultTransferServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferRepository transferRepository;

    public Account createAccount(String name, BigDecimal amount) {
        logger.debug("Creating account with name {}, amount {}", name, amount);

        Account account = accountRepository.findAccountByName(name);
        if (account != null) {
            String message = String.format("Account with name [%s] already exist.", name);
            logger.error(message);
            throw new MyApplicationException(message);
        }

        return accountRepository.save(new Account(name, amount));
    }

    @Transactional
    public Transfer doTransfer(Transfer transfer) {
        logger.debug("Transfer operation executed for {}", transfer);

        Account from = accountRepository.findAccountByName(transfer.getFromAccount());
        if (from == null) {
            String message = String.format("Account with name [%s] was not found.", transfer.getFromAccount());
            logger.error(message);
            throw new MyApplicationException(message);
        }

        Account to = accountRepository.findAccountByName(transfer.getToAccount());
        if (to == null) {
            String message = String.format("Account with name [%s] was not found.", transfer.getToAccount());
            logger.error(message);
            throw new MyApplicationException(message);
        }

        from.withdraw(transfer.getAmount());
        to.credit(transfer.getAmount());

        accountRepository.save(from);
        accountRepository.save(to);

        transfer.setStatus(Status.Completed);
        Transfer save = transferRepository.save(transfer);
        logger.debug("Transfer operation processed. Transfer data: {}", save);
        return save;
    }
}
