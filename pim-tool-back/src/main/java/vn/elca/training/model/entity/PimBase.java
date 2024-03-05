package vn.elca.training.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class PimBase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private long version;

}