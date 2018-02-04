package my.test.ingenico.service;

import java.math.BigDecimal;
import my.test.ingenico.model.Account;
import my.test.ingenico.model.Transfer;

public interface TransferService {

    Account createAccount(String name, BigDecimal amount);

    Transfer doTransfer(Transfer transfer);
}
