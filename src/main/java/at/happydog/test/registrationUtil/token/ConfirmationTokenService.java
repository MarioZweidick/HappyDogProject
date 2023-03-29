package at.happydog.test.registrationUtil.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 ConfirmationTokenService
 **/
@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public void deleteConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.delete(token);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return confirmationTokenRepository.findByToken(token);
    }

    public Optional<ConfirmationToken> getTokenById(Long id){
        return confirmationTokenRepository.findById(id);
    }

    public int setNewId(String token, Long newId){
        return confirmationTokenRepository.updateId(token, newId);
    }

    public int deleteConfirmationTokenByAppUserId(Long id){
        return confirmationTokenRepository.deleteConfirmationTokenByAppUserId(id);
    };

    public int setConfirmedAt(String token){
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
