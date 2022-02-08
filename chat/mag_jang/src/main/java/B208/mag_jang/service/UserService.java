package B208.mag_jang.service;

import B208.mag_jang.domain.User;
import B208.mag_jang.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

//@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

//    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(User user){
//        validateDuplicateUser(user);
        userRepository.save(user);
        return user;
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByNickName(user.getNickName())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 닉네임입니다.");
                });
    }

    public User findUserUsingEmail(String email){
        return userRepository.findByEmail(email)
                .orElseGet(() -> JoinNewUserWithEmail(email));
    }

    public User JoinNewUserWithEmail(String email){
        User user = new User();
        user.setEmail(email);
        return join(user);
    }

    public String setNickName(Long userId, String nickName){
        if (userRepository.findByNickName(nickName).isPresent()) {
            return "이미 존재하는 닉네임입니다!";
        }
        Optional<User> user = userRepository.findById(userId);
//                .ifPresent(u -> u.setNickName(nickName));
        if (user.isPresent()) {
            user.ifPresent(u -> u.setNickName(nickName));
            return "true";
        } else {
            return "유저 데이터 오류! 관리자에게 문의하세요";
        }
    }

}
