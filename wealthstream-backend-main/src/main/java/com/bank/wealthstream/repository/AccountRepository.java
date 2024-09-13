package com.bank.wealthstream.repository;

import com.bank.wealthstream.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("SELECT A FROM Account A " +
            "WHERE A.accountNumber=?1")
    Account getAccount(String accountNumber);

    @Query("SELECT A FROM Account A " +
            "JOIN Customer C ON A.idCus = C.idCus " +
            "WHERE C.idCus = ?1 AND A.accountNumber = ?2")
    Optional<Account> ifExistAccount(String idCus, String accountNumber);

    @Query("SELECT A FROM Account A " +
            "JOIN Customer C ON A.idCus = C.idCus " +
            "JOIN Person P ON C.idPer = P.idPer " +
            "WHERE P.identification = ?1")
    List<Account> getAccountByIdentification(String identification);
}
