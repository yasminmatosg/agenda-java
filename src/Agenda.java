import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Agenda {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Contato> agenda = new ArrayList<Contato>();
        while (true) {
            mostrarMenu();
            int opcao = sc.nextInt();
            sc.nextLine(); // consume newline
            switch (opcao) {
                case 1:
                    adicionarContato(agenda, sc);
                    break;
                case 2:
                    listarContatos(agenda);
                    break;
                case 3:
                    procurarContato(agenda, sc);
                    break;
                case 4:
                    excluirContato(agenda, sc);
                    break;
                case 5:
                    printLinha();
                    System.out.println("Saindo...");
                    printLinha();
                    sc.close();
                    return;
                default:
                    printLinha();
                    System.out.println("Opção inválida! Tente novamente.");
                    printLinha();
                    break;
            }
        }
    }

    public static void listarContatos(List<Contato> agenda) {
        printLinha();
        for (int i = 0; i < agenda.size(); i++) {
            System.out.println("Contato: " + (i + 1));
            System.out.println(agenda.get(i).toString());
            printLinha();
        }
    }

    public static void excluirContato(List<Contato> agenda, Scanner scanner) {
        System.out.println("Digite o nome do contato que deseja excluir:");
        String nomeExcluir = scanner.nextLine();

        boolean encontrado = false;

        for (int i = 0; i < agenda.size(); i++) {
            if (nomeExcluir.equals(agenda.get(i).getNome())) {
                agenda.remove(i);
                printLinha();
                System.out.println("Contato excluído.");
                printLinha();
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            printLinha();
            System.out.println("Contato não encontrado.");
            printLinha();
        }
    }

    public static void procurarContato(List<Contato> agenda, Scanner scanner) {
        System.out.println("Digite o nome do contato que deseja procurar:");
        String nomeProcurado = scanner.nextLine();

        boolean encontrado = false;

        for (int i = 0; i < agenda.size(); i++) {
            if (nomeProcurado.equals(agenda.get(i).getNome())) {
                printLinha();
                System.out.println("Contato encontrado:");
                System.out.println(agenda.get(i).toString());
                printLinha();
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            printLinha();
            System.out.println("Contato não encontrado.");
            printLinha();
        }
    }

    public static void adicionarContato(List<Contato> agenda, Scanner scanner) {
        String nome;
        while (true) {
            System.out.println("Digite o nome do contato:");
            nome = scanner.nextLine().trim();
            if (isValidName(nome)) break;
            printLinha();
            System.out.println("Nome inválido. Use apenas letras, espaços, hífens ou apóstrofos.");
            printLinha();
        }

        String telefone;
        while (true) {
            System.out.println("Digite o telefone do contato com DDD (ex: (11) 91234-5678 ou 11 91234-5678):");
            telefone = scanner.nextLine().trim();
            if (isValidPhone(telefone)) break;
            printLinha();
            System.out.println("Telefone inválido. Formato esperado: (11) 91234-5678 ou 11912345678).");
            printLinha();
        }

        String email;
        while (true) {
            System.out.println("Digite o e-mail do contato:");
            email = scanner.nextLine().trim();
            if (isValidEmail(email)) break;
            printLinha();
            System.out.println("E-mail inválido. Formato esperado: usuario@dominio.ex");
            printLinha();
        }

        Contato novoContato = new Contato(nome, telefone, email);
        agenda.add(novoContato);

        System.out.println("Contato adicionado com sucesso!");
        printLinha();
    }

    public static boolean isValidName(String name) {
        if (name == null || name.isEmpty()) return false;
        return name.matches("^[\\p{L} '\\-]+$");
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) return false;
        String digits = phone.replaceAll("\\D", "");

        // check length: 8 to 15 digits
        if (digits.length() < 8 || digits.length() > 15) return false;

        // reject if starts with 00 (invalid DDD)
        if (digits.startsWith("00")) return false;

        // reject placeholder patterns with many 0s (e.g., 00000, 000000)
        if (digits.matches("^0{4,}.*")) return false;

        // reject excessive digit repetition (5+ same digits in a row)
        if (digits.matches(".*([0-9])\\1{4,}.*")) return false;

        return true;
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return email.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    }

    public static void mostrarMenu() {
        String[] lines = new String[]{
            "AGENDA PESSOAL DE CONTATOS",
            "",
            "Escolha uma opção:",
            "1. Adicionar Contato",
            "2. Listar Contatos",
            "3. Procurar Contato",
            "4. Excluir Contato",
            "5. Sair"
        };

        int max = 0;
        for (String s : lines) {
            if (s.length() > max) max = s.length();
        }

        int innerWidth = max + 2; // padding of 1 space each side
        StringBuilder border = new StringBuilder();
        border.append('+');
        for (int i = 0; i < innerWidth; i++) border.append('-');
        border.append('+');

        System.out.println(border.toString());
        for (String s : lines) {
            System.out.println("| " + padRight(s, max) + " |");
        }
        System.out.println(border.toString());
    }

    public static String padRight(String s, int n) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < n) sb.append(' ');
        return sb.toString();
    }

    public static void printLinha() {
        System.out.println("---------------------------------------------------------");
    }
}

