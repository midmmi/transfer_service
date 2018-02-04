package my.test.ingenico.service;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import my.test.ingenico.exception.MyApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Barrier service for demonstration multithreading Singleton service.
 */
@Service
public class BarrierService {

    private final static Logger logger = LoggerFactory.getLogger(BarrierService.class);

    private CyclicBarrier barrier = new CyclicBarrier(10);

    void barrier() {
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            logger.error("Error in cycling barrier", e);
            throw new MyApplicationException("Internal error.");
        }
    }
}
