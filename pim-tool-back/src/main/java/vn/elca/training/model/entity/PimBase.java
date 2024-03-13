package vn.elca.training.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class PimBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 19)
    private Long id;

    @Version
    @Column(nullable = false, length = 10)
    private long version;

    public Long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
