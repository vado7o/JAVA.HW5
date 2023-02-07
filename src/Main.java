import java.lang.reflect.Array;
import java.net.StandardSocketOptions;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
//        task1();
//        task2();
        task4(); //задача с двумя **
    }

    static void task1() {
        TreeMap<String, List<String>> phonebook = new TreeMap<>((o1, o2) -> {
            int result = 0;
            if (o1.compareTo(o2) > 0) result = 1;
            if (o1.compareTo(o2) < 0) result = -1;
            if (o1.equals(o2)) result = 0;
            return result;
        });
        Scanner scanner = new Scanner(System.in);
        String key;
        String value;
        while (true) {
            System.out.println("\nВведите имя абонента или Enter для прекращения работы со справочником: ");
            key = scanner.nextLine();
            if (key == "") break;

            System.out.println("\nВведите номер абонента или Enter для прекращения работы со справочником: ");
            value = scanner.nextLine();
            if (value.equals("")) break;

            if (phonebook.containsKey(key)) {
                if (!phonebook.get(key).contains(value)) phonebook.get(key).add(value);
            } else {
                List<String> list = new ArrayList<>();
                list.add(value);
                phonebook.put(key, list);
            }
        }

        System.out.println("__________________________________________________________________________________________________");
        System.out.println("|Имя абонента                           |    Телефонные номера                                   |");
        System.out.println("|_______________________________________|________________________________________________________|");
        for (Map.Entry<String, List<String>> entry : phonebook.entrySet()) {
            Iterator<String> iterator = entry.getValue().listIterator();
            String str = iterator.next();
            System.out.println("|" + entry.getKey() + ":" + " ".repeat(38 - entry.getKey().length()) + "|" + str
                    + " ".repeat(56 - str.length()) + "|");
            while (iterator.hasNext()) {
                str = iterator.next();
                System.out.println("|" + " ".repeat(39) + "|" + str + " ".repeat(56 - str.length()) + "|");
            }
            System.out.println("|---------------------------------------|--------------------------------------------------------|");
        }
        System.out.println("|_______________________________________|________________________________________________________|");
    }

    static void task2() {
        /**
         Пусть дан список сотрудников: Иван Иванов, Светлана Петрова, Кристина Белова, Анна Мусина, Анна Крутова, Иван Юрин, Петр Лыков,
         Павел Чернов, Петр Чернышов, Мария Федорова, Марина Светлова, Мария Савина, Мария Рыкова, Марина Лугова, Анна Владимирова, Иван Мечников,
         Петр Петин, Иван Ежов. Написать программу, которая найдет и выведет повторяющиеся имена с количеством повторений.
         Отсортировать по убыванию популярности. Для сортировки использовать TreeMap.
         **/

        String str = "Иван Иванов, Светлана Петрова, Кристина Белова, Анна Мусина, Анна Крутова, Иван Юрин, Петр Лыков, Павел Чернов, Петр Чернышов, Мария Федорова, Марина Светлова, Мария Савина, Мария Рыкова, Марина Лугова, Анна Владимирова, Иван Мечников, Петр Петин, Иван Ежов";
        TreeMap<String, Integer> names = new TreeMap<>();
        List<String> namesList = Arrays.stream(str.split(",")).toList();
        for (String el : namesList) {
            if (names.containsKey(el.trim().split(" ")[0])) {
                int value = names.get(el.trim().split(" ")[0]);
                names.put(el.trim().split(" ")[0], value + 1);
            } else names.put(el.trim().split(" ")[0], 1);
        }
        TreeMap<Integer, List<String>> sortedNames = new TreeMap<>((o1, o2) -> {
            int result = 0;
            if (o1 < o2) result = 1;
            if (o1 > o2) result = -1;
            return result;
        });
        for (Map.Entry<String, Integer> entry : names.entrySet()) {
            if (sortedNames.containsKey(entry.getValue())) {
                sortedNames.get(entry.getValue()).add(entry.getKey());
            } else {
                ArrayList<String> list = new ArrayList<>();
                list.add(entry.getKey());
                sortedNames.put(entry.getValue(), list);
            }
        }
        for (Map.Entry<Integer, List<String>> entry: sortedNames.entrySet()) {
            System.out.println("Имена, которые встречаются " + entry.getKey() + " раза: " + Arrays.toString(sortedNames.get(entry.getKey()).toArray())
                    .replace("[", "").replace("]", ""));
        }
    }

    //ЗАДАЧА С ДВУМЯ **
    static void task4() {

       ArrayList<ArrayList<Integer>> board = new ArrayList<>(7);
       for (int n = 0; n <= 7; n++) board.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0)));
       int figures = 1;

       while(figures < 8) {
           figures = 1;
           Random rand = new Random();
           int num = rand.nextInt(7);
           int num2 = rand.nextInt(7);
           board.get(num).set(num2, 1);
           for (int i = 0; i <= 7; i++) {
               for (int j = 0; j <= 7; j++) {
                   if (!checkLines(board, i, j)) continue;
                   if (!checkDiagonales(board, i, j)) continue;
                   board.get(i).set(j, 1);
                   figures++;
               }
           }
           if (figures >= 8) printBoard(board);
           board.clear();
           for (int n = 0; n <= 7; n++) board.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0)));
        }
    }

    static Boolean checkLines(ArrayList<ArrayList<Integer>> board, Integer i, Integer j) {
        if (board.get(i).contains(1)) return false;
        for (int m = 0; m <= 7; m++) {
            if (board.get(m).get(j) == 1) return false;
        }
        return true;
    }

    static Boolean checkDiagonales(ArrayList<ArrayList<Integer>> board, Integer i, Integer j) {
        int m = j;
        for (int l = i; l >= 0; l--) {
            if (board.get(l).get(m) == 1) return false;
            m--;
            if (m == -1) break;
        }
        int l = i;
        for (m = j; m <=7; m++) {
            if (board.get(l).get(m) == 1)  return false;
            l++;
            if (l > 7) {
                break;
            }
        }
        m = j;
        for (l = i; l >= 0; l--) {
            if (board.get(l).get(m) == 1) return false;
            m++;
            if (m > 7) break;
        }
        l = i;
        for (m = j; m >= 0; m--) {
            if (board.get(l).get(m) == 1) return false;
            l++;
            if (l > 7) break;
        }
        return true;
    }

    static void printBoard(ArrayList<ArrayList<Integer>> board) {
        String startLine = "________________________________";
        String line = "--------------------------------";
        String yes = "| O ";
        String no = "|   ";

        System.out.println(startLine);
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (board.get(i).get(j) == 1) System.out.print(yes);
                else System.out.print(no);
            }
            System.out.println("|");
            System.out.println(line);
        }
    }
}

