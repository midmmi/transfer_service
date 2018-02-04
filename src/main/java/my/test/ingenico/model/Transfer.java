package my.test.ingenico.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private Status status;

    public Transfer() {
    }

    public Transfer(String fromAccount, String toAccount, BigDecimal amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.status = Status.Initialized;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transfer)) {
            return false;
        }

        Transfer transfer = (Transfer) o;

        if (fromAccount != null ? !fromAccount.equals(transfer.fromAccount) : transfer.fromAccount != null) {
            return false;
        }
        if (toAccount != null ? !toAccount.equals(transfer.toAccount) : transfer.toAccount != null) {
            return false;
        }
        if (amount != null ? !amount.equals(transfer.amount) : transfer.amount != null) {
            return false;
        }
        return status == transfer.status;
    }

    @Override
    public int hashCode() {
        int result = fromAccount != null ? fromAccount.hashCode() : 0;
        result = 31 * result + (toAccount != null ? toAccount.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    public enum Status {
        Initialized,
        Completed,
        Error
    }
}
