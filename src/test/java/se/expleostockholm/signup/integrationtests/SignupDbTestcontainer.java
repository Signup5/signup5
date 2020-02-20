package se.expleostockholm.signup.integrationtests;


import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest()
public class SignupDbTestcontainer extends PostgreSQLContainer<SignupDbTestcontainer> {
    private static final String IMAGE_VERSION = "postgres:12";
    private static SignupDbTestcontainer container;

    private SignupDbTestcontainer() {
        super(IMAGE_VERSION);
    }

    public static SignupDbTestcontainer getInstance() {
        if (container == null) {
            container = new SignupDbTestcontainer()
                    .withDatabaseName("signup")
                    .withUsername("postgres")
                    .withPassword("password")
                    .withExposedPorts(5432);
        }
        return container;
    }

//    @Override
//    public void start() {
//        super.start();
//    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}