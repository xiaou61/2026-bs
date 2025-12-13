package com.xiaou.ticket.service;

import com.xiaou.ticket.entity.User;
import com.xiaou.ticket.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public User updateUser(Long id, User user) {
        User existing = getUserById(id);
        existing.setPhone(user.getPhone());
        existing.setEmail(user.getEmail());
        existing.setRealName(user.getRealName());
        existing.setIdCard(user.getIdCard());
        return userRepository.save(existing);
    }
    
    @Transactional
    public User recharge(Long userId, BigDecimal amount) {
        User user = getUserById(userId);
        user.setBalance(user.getBalance().add(amount));
        return userRepository.save(user);
    }
    
    public BigDecimal getBalance(Long userId) {
        User user = getUserById(userId);
        return user.getBalance();
    }
}
