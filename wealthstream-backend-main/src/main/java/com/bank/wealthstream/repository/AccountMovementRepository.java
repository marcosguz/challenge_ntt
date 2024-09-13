package com.bank.wealthstream.repository;

import com.bank.wealthstream.model.AccountMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccountMovementRepository extends JpaRepository<AccountMovement, String> {
    @Query("SELECT M FROM Person P " +
            "JOIN Customer C ON P.idPer = C.idPer " +
            "JOIN Account A ON C.idCus = A.idCus " +
            "JOIN AccountMovement M ON A.idAcc = M.idAcc " +
            "WHERE P.identification = ?1 " +
            "ORDER BY M.date DESC")
    List<AccountMovement> getAccountMovementsByIdentification(String identification);

    @Query("SELECT M FROM Person P " +
            "JOIN Customer C ON P.idPer = C.idPer " +
            "JOIN Account A ON C.idCus = A.idCus " +
            "JOIN AccountMovement M ON A.idAcc = M.idAcc " +
            "WHERE M.date BETWEEN ?1 AND ?2 AND C.state = true " +
            "ORDER BY M.date DESC")
    List<AccountMovement> getMovements(LocalDateTime dateInit, LocalDateTime dateFinish);
}
