package container;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SetOpt {
    public static void main(String[] args) {
        Set<String> ss = new HashSet<>();
    }

    @Data
    static class Pet {
        static private int counter;
        @Getter
        private int id = counter++;
        @Setter @Getter
        private String name;
    }

    void display(Collection<Pet> pets) {
        for (Pet p : pets) {
            System.out.print(p.getId() + ":" + p.getName() + " ");
        }
        System.out.println();
    }
}
