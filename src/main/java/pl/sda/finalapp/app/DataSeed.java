package pl.sda.finalapp.app;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.finalapp.app.users.Role;
import pl.sda.finalapp.app.users.RoleRepository;
import pl.sda.finalapp.app.users.User;
import pl.sda.finalapp.app.users.UserRepository;

@Service
public class DataSeed implements InitializingBean {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(roleRepository.count()==0){
            roleRepository.save(new Role(Role.ADMIN));
            roleRepository.save(new Role(Role.USER));
        }
        User admin1 = new User("Admin",
                "Admin",
                "admin@admin.pl",
                passwordEncoder.encode("admin123"),
                "city",
                "pl",
                "20-123",
                "street",
                "1-1-1",
                "1",
                "1",
                true);
        userRepository.save(admin1);
        User user1 = new User("User",
                "User",
                "user@user.pl",
                passwordEncoder.encode("user123"),
                "city",
                "pl",
                "20-123",
                "street",
                "1-1-1",
                "1",
                "1",
                true);
    }
}