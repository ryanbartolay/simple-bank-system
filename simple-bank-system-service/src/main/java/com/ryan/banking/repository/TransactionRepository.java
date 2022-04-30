package com.ryan.banking.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ryan.banking.model.Account;
import com.ryan.banking.model.Transaction;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, UUID> {

    List<Transaction> findAllByAccount(Account account);

    @Query("select a from Transaction a where account = :account and date(a.completedDate) = date(:currentDate)")
    List<Transaction> findAllByAccountToday(@Param("account") Account account, @Param("currentDate") Date currentDate);

    Page<Transaction> findAllByAccount(Account account, Pageable pageable);

}
