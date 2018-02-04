package my.test.ingenico.service;

import java.math.BigDecimal;
import my.test.ingenico.exception.MyApplicationException;
import my.test.ingenico.model.Transfer;
import my.test.ingenico.model.Transfer.Status;
import my.test.ingenico.model.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Additional transfer service. We should execute transactional methods from outside of the bean to be sure transaction
 * works.
 */
@Service
@Scope("request")
public class DefaultTransferServiceHelperImp implements TransferServiceHelper {

    private final static Logger logger = LoggerFactory.getLogger(DefaultTransferServiceImpl.class);

    @Autowired
    private TransferService transferService;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private BarrierService barrierService;

    /**
     * Prepares Transfer object and performs barrier transfer operation. In barrier mode it waits until 10 requests,
     * keep them on hold, and then, simultaneously release them all. This was made in purpose of demonstrating
     * multithreading.
     *
     * Updates Transfer status in case of error.
     *
     * @param fromAccount withdrawing account
     * @param toAccount crediting account
     * @param amount amount of barrierTransfer
     * @return true if operation was successful, false otherwise
     */
    public Transfer barrierTransfer(String fromAccount, String toAccount, BigDecimal amount) {
        Transfer transfer = new Transfer(fromAccount, toAccount, amount);
        transfer = transferRepository.save(transfer);

        barrierService.barrier();
        logger.debug("Thread {} started, Transfer {}", Thread.currentThread().getName(), transfer);

        return performTransfer(transfer);
    }

    /**
     * Prepares Transfer object and performs normal transfer operation. Updates Transfer status in case of error.
     *
     * @param fromAccount withdrawing account
     * @param toAccount depositing account
     * @param amount amount of barrierTransfer
     * @return true if operation was successful, false otherwise
     */
    public Transfer transfer(String fromAccount, String toAccount, BigDecimal amount) {
        Transfer transfer = new Transfer(fromAccount, toAccount, amount);
        transfer = transferRepository.save(transfer);

        return performTransfer(transfer);
    }

    private Transfer performTransfer(Transfer transfer) {
        try {
            transfer = transferService.doTransfer(transfer);
        } catch (MyApplicationException e) {
            logger.error("Error performing transfer {}", transfer, e);
            transfer.setStatus(Status.Error);
            transfer = transferRepository.save(transfer);
        }

        return transfer;
    }
}
