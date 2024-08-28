package com.amigoscode;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRepository;
import com.amigoscode.customer.Gender;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        //        ConfigurableApplicationContext applicationContext = SpringApplication.run(Main.class, args);
//        printBeans(applicationContext);
    }

    @Bean
//    @ConditionalOnProperty(
//            prefix = "command.line.runner",
//            value = "enabled",
//            havingValue = "true",
//            matchIfMissing = true
//    )
    CommandLineRunner runner(
            CustomerRepository repository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            Faker faker = new Faker();
            Random random = new Random();
            Name name = faker.name();
            String firstName = name.firstName();
            String lastName = name.lastName();
            int age = random.nextInt(16, 99);
            Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@amigoscode.com";
            Customer customer = new Customer(
                    firstName + " " + lastName,
                    email,
//                    firstName.toLowerCase() + "." + lastName.toLowerCase() + "@amigoscode.com",
//                    passwordEncoder.encode(UUID.randomUUID().toString()), age,
                    passwordEncoder.encode("password"), age,
                    gender
            );
//            Customer alex = new Customer(
//                    "Alex",
//                    "alex@gmail.com",
//                    21
//            );
//            Customer jamila = new Customer(
//                    "Jamila",
//                    "jamila@gmail.com",
//                    19
//            );

//            List<Customer> customers = List.of(alex, jamila);
//            repository.saveAll(customers);
            repository.save(customer);
            System.out.println(email);

        };
    }

//    @Bean
//    public Foo getFoo() {
//        return new Foo("bar");
//    }
//
//    record Foo(String name) {}
//
//    private static void printBeans(ConfigurableApplicationContext ctx) {
//        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName);
//        }
//    }

}
