import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class UsaClasses {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        ArrayList<Tank> tanques = new ArrayList<>();

        System.out.println("=== SIMULADOR DE COMBATE (LAB 01) ===");
        int qtd = 0;
        while (qtd < 2 || qtd > 10) {
            System.out.print("Quantos tanques deseja criar (2-10)? ");
            if (leitor.hasNextInt()) {
                qtd = leitor.nextInt();
            } else {
                leitor.next(); 
            }
        }
        leitor.nextLine(); 
     
        for (int i = 0; i < qtd; i++) {
            System.out.print("Digite o nome do tanque " + (i + 1) + ": ");
            String nome = leitor.nextLine();
            tanques.add(new Tank(nome));
        }

        while (tanques.size() > 1) {
            Collections.shuffle(tanques);
            ArrayList<Tank> destruidos = new ArrayList<>();

            for (Tank atacante : tanques) {
                if (!atacante.isAlive() || (tanques.size() - destruidos.size()) <= 1) continue;

                System.out.println("\n--- VEZ DE: " + atacante.getName() + " ---");
                
                System.out.println("Alvos disponíveis:");
                for (int i = 0; i < tanques.size(); i++) {
                    Tank alvoPotencial = tanques.get(i);
                    if (alvoPotencial != atacante && !destruidos.contains(alvoPotencial)) {
                        System.out.println(i + " - " + alvoPotencial.getName() + " (Armor: " + alvoPotencial.getArmor() + ")");
                    }
                }

                int alvoIdx;
                do {
                    System.out.print("Escolha o índice do alvo para atirar: ");
                    alvoIdx = leitor.nextInt();
                } while (alvoIdx < 0 || alvoIdx >= tanques.size() || tanques.get(alvoIdx) == atacante || destruidos.contains(tanques.get(alvoIdx)));

                Tank inimigo = tanques.get(alvoIdx);
                atacante.fire_at(inimigo); 

                if (!inimigo.isAlive()) {
                    destruidos.add(inimigo);
                }
            }
       
            tanques.removeAll(destruidos);

           
            System.out.println("\n--- STATUS APÓS A RODADA ---");
            for (Tank t : tanques) {
                System.out.println(t.toString()); 
            }
        }

        System.out.println("\n O VENCEDOR DA SIMULAÇÃO É: " + tanques.get(0).getName() + "! ");
        leitor.close();
    }
}