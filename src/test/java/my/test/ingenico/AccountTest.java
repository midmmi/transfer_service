package my.test.ingenico;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import my.test.ingenico.model.Account;
import my.test.ingenico.model.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testAccount() {
        this.entityManager.persist(new Account("testAcc", new BigDecimal(121.22)));
        Account account = this.accountRepository.findAccountByName("testAcc");
        assertTrue(account.getName().equals("testAcc"));
        assertTrue(account.getBalance().equals(new BigDecimal(121.22)));
    }
}
