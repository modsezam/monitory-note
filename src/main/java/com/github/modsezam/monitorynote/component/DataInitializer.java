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
        addUser("manager", "manager", "MANAGER");

        Company company1 = new Company (null, "Coca-cola", "coca-cola street", "Chicago", "11-200", "USA", null, null);
        Company company2 = new Company (null, "Amazon", "amazon street", "New York", "55-888", "England", null, null);
        Company company3 = new Company (null, "Adidas", "adidas street", "Łódź", "33-147", "Poland", null, null);

        Person person1 = new Person(null, "Michał", "Kowal", "ALL 25664", company1, null);
        Person person2 = new Person(null, "Żaneta", "Ćwir", "WRO 65894", company1, null);
        Person person3 = new Person(null, "Janusz", "Wór", "LKK 556644", company2, null);
        Person person4 = new Person(null, "Grażyna", "Koc", "KJG 88888", company2, null);
        Person person5 = new Person(null, "Błażej", "Stół", "LOE 564891", company3, null);
        Person person6 = new Person(null, "Krzysztof", "Mały", "DRO 223355", company3, null);
        Person person7 = new Person(null, "Jarosław", "Kaczyński", "KKG 85763", company3, null);
        Person person8 = new Person(null, "Lech", "Kaczyński", "HKL 98593", company3, null);

        Car car1 = new Car(null, "AP09876", "Toyota", "Yaris", company1, null);
        Car car2 = new Car(null, "GD58697", "Fiat", "Multipla", company1, null);
        Car car3 = new Car(null, "WR45601", "Skoda", "Fabia", company2, null);
        Car car4 = new Car(null, "LD45901", "Lada", "Niva", company2, null);
        Car car5 = new Car(null, "GG40239", "Mercedes", "Vito", company3, null);
        Car car6 = new Car(null, "SC58403", "Opel", "Insignia", company3, null);
        Car car7 = new Car(null, "KGJ97322", "Opel", "Corsa", company3, null);
        Car car8 = new Car(null, "PRO94573", "Opel", "Corsa", company3, null);

        addCompany(company1);
        addCompany(company2);
        addCompany(company3);
        addCar(car1);
        addCar(car2);
        addCar(car3);
        addCar(car4);
        addCar(car5);
        addCar(car6);
        addCar(car7);
        addCar(car8);
        addPerson(person1);
        addPerson(person2);
        addPerson(person3);
        addPerson(person4);
        addPerson(person5);
        addPerson(person6);
        addPerson(person7);
        addPerson(person8);
    }



    private void addPerson (Person person) {
        if (!personRepository.existsByIdDocument(person.getIdDocument())) {
            personRepository.save(person);
        }
    }

    private void addCar (Car car) {
        if (!carRepository.existsByRegistrationNr(car.getRegistrationNr())) {
            carRepository.save(car);
        }
    }

    private void addCompany (Company company) {
        if (!companyRepository.existsByName(company.getName())) {
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
