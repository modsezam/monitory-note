package com.github.modsezam.monitorynote.service;

import com.github.modsezam.monitorynote.model.AccountRole;
import com.github.modsezam.monitorynote.repository.AccountRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountRoleService {

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    public List<AccountRole> getAll() {
        return accountRoleRepository.findAll();
    }
}
