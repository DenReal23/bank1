package denis_sofia.mavenproject1.run;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import denis_sofia.mavenproject1.controller.Avtomat;

@Component
public class Run implements CommandLineRunner {
    private final Avtomat avtomat;

    public Run(Avtomat avtomat) {
        this.avtomat = avtomat;
    }

    @Override
    public void run(String... args) {
        avtomat.start();
    }
}