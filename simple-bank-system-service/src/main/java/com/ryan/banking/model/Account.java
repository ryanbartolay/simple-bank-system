package com.ryan.banking.model;

import java.util.UUID;

import javax.money.CurrencyUnit;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.javamoney.moneta.Money;
import org.joda.time.DateTime;

import com.ryan.banking.model.converter.AccountTypeConverter;
import com.ryan.banking.model.converter.CurrencyUnitConverter;
import com.ryan.banking.model.converter.DateTimeConverter;
import com.ryan.banking.model.converter.MoneyAmountAttributeConverter;
import com.ryan.banking.model.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Entity
@ToString
@Table(name = "account", schema = "bank")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

    @Id
    @EqualsAndHashCode.Include()
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;
    @Column(name = "type", nullable = false, updatable = false)
    @Convert(converter = AccountTypeConverter.class)
    private AccountType type;
    @Column(name = "balance", nullable = false)
    @Convert(converter = MoneyAmountAttributeConverter.class)
    private Money balance;
    @Column(name = "currency_unit", nullable = false, updatable = false)
    @Convert(converter = CurrencyUnitConverter.class)
    private CurrencyUnit currency;
    @Column(name = "date_created", nullable = false, updatable = false)
    @Convert(converter = DateTimeConverter.class)
    private DateTime dateCreated;
    @Convert(converter = DateTimeConverter.class)
    private DateTime dateLastUpdate;
    @EqualsAndHashCode.Include()
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

}
