package at.happydog.test.registrationUtil.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 ConfirmationTokenRepository
 **/

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("DELETE ConfirmationToken c WHERE c.token = ?1")
    void deleteByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c SET c.confirmedAt = ?2 WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c SET c.id = ?2 WHERE c.token = ?1")
    int updateId(String token, Long id);

    @Transactional
    @Modifying
    @Query("DELETE ConfirmationToken c WHERE c.appUser.id = ?1")
    int deleteConfirmationTokenByAppUserId(Long id);


}
