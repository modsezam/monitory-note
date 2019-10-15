package com.github.modsezam.monitorynote.component;

import com.github.modsezam.monitorynote.model.Account;
import com.github.modsezam.monitorynote.model.AccountRole;
import com.github.modsezam.monitorynote.repository.AccountRepository;
import com.github.modsezam.monitorynote.repository.AccountRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

// Klasa która uruchomi metodę "onApplicationEvent" w momencie gdy baza zostanie poprawnie załadowana
@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountRoleRepository accountRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${default.roles}")
    private String[] defaultRoles;

    @Value("${default.admin.password:admin}")
    private String defaultAdminPassword;

    //    Ta metoda uruchomi się automatycznie po stworzeniu/aktualizacji bazy
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (String role : defaultRoles) {
            addRole(role);
        }

        addUser("admin", defaultAdminPassword, "USER", "ADMIN");
        addUser("user", "user", "USER");
    }

    private void addUser(String username, String password, String... roles) {
        if (!accountRepository.existsByUsername(username)) {
            Account account = new Account();

            account.setUsername(username);
            account.setPassword(passwordEncoder.encode(password));

            /*Magia z powiązaniem z rolami*/
            Set<AccountRole> userRoles = findRoles(roles);
            account.setRoles(userRoles);

            accountRepository.save(account);
        }
    }

    private Set<AccountRole> findRoles(String[] roles) {
        Set<AccountRole> accountRoles = new HashSet<>();
        for (String role : roles) {
            accountRoleRepository.findByName(role).ifPresent(accountRoles::add);
        }
        return accountRoles;
    }

    private void addRole(String roleToCreate) {
        if (!accountRoleRepository.existsByName(roleToCreate)) {
            AccountRole accountRole = new AccountRole();
            accountRole.setName(roleToCreate);

            accountRoleRepository.save(accountRole);
        }
    }
}
