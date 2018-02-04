package my.test.ingenico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Iterator;
import my.test.ingenico.model.Transfer;
import my.test.ingenico.model.Transfer.Status;
import my.test.ingenico.model.TransferRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransferTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransferRepository transferRepository;

    @Test
    public void testTransfer() {
        this.entityManager.persist(
                new Transfer("testFrom", "testTo", new BigDecimal(121.22).setScale(2, BigDecimal.ROUND_HALF_UP)));
        Iterable<Transfer> all = transferRepository.findAll();
        Iterator<Transfer> iterator = all.iterator();
        assertTrue(all.iterator().hasNext());
        Transfer transfer = iterator.next();
        assertEquals(transfer.getFromAccount(), "testFrom");
        assertEquals(transfer.getToAccount(), "testTo");
        assertEquals(transfer.getAmount(), new BigDecimal(121.22).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertEquals(transfer.getStatus(), Status.Initialized);
        assertFalse(iterator.hasNext());
    }
}
