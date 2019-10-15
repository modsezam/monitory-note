package com.github.modsezam.monitorynote.service;

import com.github.modsezam.monitorynote.model.Account;
import com.github.modsezam.monitorynote.model.AccountRole;
import com.github.modsezam.monitorynote.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();

            String[] roles = account.getRoles()
                    .stream()
                    .map(AccountRole::getName)
                    .toArray(String[]::new);

            return User.builder()
                    .accountLocked(account.isLocked())
                    .username(account.getUsername())
                    .password(account.getPassword())
                    .roles(roles)
                    .build();
        }
        throw new UsernameNotFoundException("User not found.");
    }
}
