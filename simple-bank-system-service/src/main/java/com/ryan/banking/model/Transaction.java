package com.ryan.banking.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.javamoney.moneta.Money;
import org.joda.time.DateTime;

import com.ryan.banking.model.converter.DateTimeConverter;
import com.ryan.banking.model.converter.MoneyAmountAttributeConverter;
import com.ryan.banking.model.converter.TransactionStatusConverter;
import com.ryan.banking.model.converter.TransactionTypeConverter;
import com.ryan.banking.model.enums.TransactionStatus;
import com.ryan.banking.model.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@ToString
@Table(name = "transaction", schema = "bank")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {

    @Id
    @EqualsAndHashCode.Include()
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    @Convert(converter = TransactionTypeConverter.class)
    private TransactionType type;
    @Column(name = "amount")
    @Convert(converter = MoneyAmountAttributeConverter.class)
    private Money amount;
    @Column(name = "balance_starting", updatable = true)
    @Convert(converter = MoneyAmountAttributeConverter.class)
    private Money balanceStarting;
    @Column(name = "balance_running", updatable = true)
    @Convert(converter = MoneyAmountAttributeConverter.class)
    private Money balanceRunning;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Convert(converter = TransactionStatusConverter.class)
    private TransactionStatus status;
    @Column(name = "start_date", updatable = false)
    @Convert(converter = DateTimeConverter.class)
    private DateTime startDate;
    @Column(name = "expiration_date", updatable = false)
    @Convert(converter = DateTimeConverter.class)
    private DateTime expirationDate;
    @Column(name = "completed_date", updatable = true)
    @Convert(converter = DateTimeConverter.class)
    private DateTime completedDate;
    @Column(name = "remarks", updatable = true, columnDefinition = "text")
    private String remarks;
}
