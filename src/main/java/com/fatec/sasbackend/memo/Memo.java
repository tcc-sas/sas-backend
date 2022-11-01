package com.fatec.sasbackend.memo;

import com.fatec.sasbackend.util.converter.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "memo")
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "request_date")
    private LocalDateTime requestDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_memo")
    private EStatusMemo statusMemo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_delivery")
    private EStatusDelivery statusDelivery;

}
