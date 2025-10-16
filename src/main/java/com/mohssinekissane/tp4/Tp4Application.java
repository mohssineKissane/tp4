package com.mohssinekissane.tp4;

import entities.Machine;
import entities.Salle;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import services.MachineService;
import services.SalleService;

import java.util.Date;

@SpringBootApplication
public class Tp4Application {

    public static void main(String[] args) {
        SpringApplication.run(Tp4Application.class, args);
    }

    @Bean
    CommandLineRunner run() {
        return args -> {
            System.out.println("=== Démarrage des tests Hibernate ===\n");
            
            SalleService salleService = new SalleService();
            MachineService machineService = new MachineService();
            
            // 1. Création et insertion de salles
            System.out.println("--- Création de salles ---");
            Salle salle1 = new Salle("A1");
            Salle salle2 = new Salle("B2");
            
            if (salleService.create(salle1)) {
                System.out.println("✓ Salle " + salle1.getCode() + " créée avec ID: " + salle1.getId());
            }
            if (salleService.create(salle2)) {
                System.out.println("✓ Salle " + salle2.getCode() + " créée avec ID: " + salle2.getId());
            }
            
            // 2. Création et insertion de machines
            System.out.println("\n--- Création de machines ---");
            Machine machine1 = new Machine("M123", new Date(), salle1);
            Machine machine2 = new Machine("M124", new Date(), salle2);
            Machine machine3 = new Machine("M125", new Date(), salle1);
            
            if (machineService.create(machine1)) {
                System.out.println("✓ Machine " + machine1.getRef() + " créée avec ID: " + machine1.getId());
            }
            if (machineService.create(machine2)) {
                System.out.println("✓ Machine " + machine2.getRef() + " créée avec ID: " + machine2.getId());
            }
            if (machineService.create(machine3)) {
                System.out.println("✓ Machine " + machine3.getRef() + " créée avec ID: " + machine3.getId());
            }
            
            // 3. Affichage de toutes les salles et leurs machines
            System.out.println("\n--- Liste des salles et leurs machines ---");
            for (Salle salle : salleService.findAll()) {
                System.out.println("📍 Salle: " + salle.getCode() + " (ID: " + salle.getId() + ")");
                if (salle.getMachines() != null && !salle.getMachines().isEmpty()) {
                    for (Machine machine : salle.getMachines()) {
                        System.out.println("   └─ Machine: " + machine.getRef() + 
                            " (achetée le " + machine.getDateAchat() + ")");
                    }
                } else {
                    System.out.println("   └─ Aucune machine");
                }
            }
            
            // 4. Test de findById
            System.out.println("\n--- Test findById ---");
            Salle foundSalle = salleService.findById(salle1.getId());
            if (foundSalle != null) {
                System.out.println("✓ Salle trouvée: " + foundSalle.getCode());
            }
            
            Machine foundMachine = machineService.findById(machine1.getId());
            if (foundMachine != null) {
                System.out.println("✓ Machine trouvée: " + foundMachine.getRef());
            }
            
            // 5. Test de update
            System.out.println("\n--- Test update ---");
            salle1.setCode("A1-UPDATED");
            if (salleService.update(salle1)) {
                System.out.println("✓ Salle mise à jour: " + salle1.getCode());
            }
            
            // 6. Utilisation de findBetweenDate
            System.out.println("\n--- Test findBetweenDate ---");
            Date d1 = new Date(System.currentTimeMillis() - 86400000); // Hier
            Date d2 = new Date(); // Aujourd'hui
            System.out.println("Machines achetées entre " + d1 + " et " + d2 + ":");
            for (Machine m : machineService.findBetweenDate(d1, d2)) {
                System.out.println("  → " + m.getRef() + " achetée le " + m.getDateAchat());
            }
            
            // 7. Statistiques
            System.out.println("\n--- Statistiques ---");
            System.out.println("Nombre total de salles: " + salleService.findAll().size());
            System.out.println("Nombre total de machines: " + machineService.findAll().size());
            
            System.out.println("\n=== Tests terminés avec succès ===");
        };
    }

}
