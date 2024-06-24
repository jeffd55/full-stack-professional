package com.amigoscode;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRepository;
import com.amigoscode.customer.Gender;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        //        ConfigurableApplicationContext applicationContext = SpringApplication.run(Main.class, args);
//        printBeans(applicationContext);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository repository) {
        return args -> {
            Faker faker = new Faker();
            Random random = new Random();
            Name name = faker.name();
            String firstName = name.firstName();
            String lastName = name.lastName();
            int age = random.nextInt(16, 99);
            Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
            Customer customer = new Customer(
                    firstName + " " + lastName,
                    firstName.toLowerCase() + "." + lastName.toLowerCase() + "@amigoscode.com",
                    age,
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
