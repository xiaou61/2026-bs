import com.xiaou.pet.common.Result;
import com.xiaou.pet.entity.User;
import com.xiaou.pet.mapper.UserMapper;
import com.xiaou.pet.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Result<User> login(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser == null) {
            return Result.error("User not found");
        }
        // Simple MD5 check (In production, use BCrypt)
        String inputPass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        if (!dbUser.getPassword().equals(inputPass)) {
            return Result.error("Incorrect password");
        }
        dbUser.setPassword(null); // Hide password
        return Result.success(dbUser);
    }

    @Override
    public Result<User> register(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser != null) {
            return Result.error("Username already exists");
        }
        // Encrypt password
        String encryptedPass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(encryptedPass);
        if (user.getRole() == null) {
            user.setRole("USER");
        }
        userMapper.insert(user);
        return Result.success(user);
    }

    @Override
    public Result<User> getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return Result.success(user);
    }

    @Override
    public Result<User> updateUserInfo(User user) {
        userMapper.update(user);
        return Result.success(user);
    }
}
