package my.test.ingenico.service;

import java.math.BigDecimal;
import my.test.ingenico.model.Transfer;

public interface TransferServiceHelper {

    Transfer barrierTransfer(String fromAccount, String toAccount, BigDecimal amount);

    Transfer transfer(String fromAccount, String toAccount, BigDecimal amount);
}
