package my.test.ingenico.controller;

import java.math.BigDecimal;
import my.test.ingenico.model.Transfer;
import my.test.ingenico.model.TransferRepository;
import my.test.ingenico.service.DefaultTransferServiceHelperImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Transfer controller for my application.
 * Normally I would move all parameters of POST request in the body of request.
 * In this case I made them as path variables for convenience using from console with curl
 */
@RestController
@RequestMapping("/transfer")
@Scope("request")
public class TransferController {

    @Autowired
    private DefaultTransferServiceHelperImp transferServiceHelper;

    @Autowired
    private TransferRepository transferRepository;

    @RequestMapping
    public Iterable<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    @RequestMapping(value = "/{from}/{to}/{amount}", method = RequestMethod.POST)
    public Transfer transfer(@PathVariable String from, @PathVariable String to, @PathVariable String amount)
            throws InterruptedException {
        return transferServiceHelper.transfer(from, to, new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    @RequestMapping(value = "/barrier/{from}/{to}/{amount}", method = RequestMethod.POST)
    public Transfer barrierTransfer(@PathVariable String from, @PathVariable String to, @PathVariable String amount)
            throws InterruptedException {
        return transferServiceHelper.barrierTransfer(from, to, new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
