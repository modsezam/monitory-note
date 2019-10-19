package com.github.modsezam.monitorynote.component;

import com.github.modsezam.monitorynote.model.*;
import com.github.modsezam.monitorynote.repository.*;
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
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CompanyRepository companyRepository;

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
        addCompany("companyName", "company address 12/20", "City", "00-100", "Country");
        addCar("GD88880", "Toyota", "Avensis");
        addPerson("Donald", "Trump", "ASS1011002");
        addCompanyToPerson();
        addCompanyToCar();
    }

    private void addCompanyToCar () {
        Car car = carRepository.findAll().get(0);
        Company company = companyRepository.findAll().get(0);
        car.setCompany(company);
        carRepository.save(car);
    }

    private void addCompanyToPerson () {
        Company company = companyRepository.findAll().get(0);
        Person person = personRepository.findAll().get(0);
        person.setCompany(company);
        personRepository.save(person);
    }

    private void addPerson (String firstname, String lastname, String idDocument) {
        if (!personRepository.existsByIdDocument(idDocument)) {
            Person person = new Person();
            person.setFirstname(firstname);
            person.setLastname(lastname);
            person.setIdDocument(idDocument);
            personRepository.save(person);
        }
    }

    private void addCar (String registrationNr, String mark, String model) {
        if (!carRepository.existsByRegistrationNr(registrationNr)) {
            Car car = new Car();
            car.setRegistrationNr(registrationNr);
            car.setMark(mark);
            car.setModel(model);
            carRepository.save(car);
        }
    }

    private void addCompany (String name, String address, String city, String postCode, String country) {
        if (!companyRepository.existsByName(name)) {
            Company company = new Company();
            company.setName(name);
            company.setAddress(address);
            company.setCity(city);
            company.setPostCode(postCode);
            company.setCountry(country);
            companyRepository.save(company);
        }
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
