package my.test.ingenico;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import my.test.ingenico.exception.MyApplicationException;
import my.test.ingenico.model.Account;
import my.test.ingenico.model.AccountRepository;
import my.test.ingenico.model.Transfer;
import my.test.ingenico.model.TransferRepository;
import my.test.ingenico.service.DefaultTransferServiceImpl;
import my.test.ingenico.service.TransferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Component
public class TransferServiceTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public TransferService transferService() {
            return new DefaultTransferServiceImpl();
        }
    }

    @Autowired
    private TransferService transferService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    @SuppressWarnings("unused")
    private TransferRepository transferRepository;

    @Before
    public void setUp() {
        Account testAccount = new Account("testAccount", new BigDecimal("121.22"));
        Account from = new Account("from", new BigDecimal("100"));
        Account to = new Account("to", new BigDecimal("100"));

        Mockito.when(accountRepository.findAccountByName("testAccount")).thenReturn(testAccount);
        Mockito.when(accountRepository.findAccountByName("from")).thenReturn(from);
        Mockito.when(accountRepository.findAccountByName("to")).thenReturn(to);
    }

    @Test
    public void testAccountCreation() {
        transferService.createAccount("testAccount02", new BigDecimal("121.22"));
        Account testAccount = accountRepository.findAccountByName("testAccount02");
        assertNull(testAccount);

        Exception exp = null;
        try {
            transferService.createAccount("testAccount", new BigDecimal("121.22"));
        } catch (MyApplicationException e) {
            exp = e;
        }
        assertNotNull(exp);
    }

    @Test
    public void testTransferFlow() {
        Transfer transfer = new Transfer("from", "to", new BigDecimal("50"));
        transferService.doTransfer(transfer);

        transfer = new Transfer("from1", "to", new BigDecimal("110"));
        Exception exp = null;
        try {
            transferService.doTransfer(transfer);
        } catch (MyApplicationException e) {
            exp = e;
        }
        assertNotNull(exp);

        transfer = new Transfer("from", "to1", new BigDecimal("50"));
        exp = null;
        try {
            transferService.doTransfer(transfer);
        } catch (MyApplicationException e) {
            exp = e;
        }
        assertNotNull(exp);

        transfer = new Transfer("from", "to", new BigDecimal("110"));
        exp = null;
        try {
            transferService.doTransfer(transfer);
        } catch (MyApplicationException e) {
            exp = e;
        }
        assertNotNull(exp);
    }
}
