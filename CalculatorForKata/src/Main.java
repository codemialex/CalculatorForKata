import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static Map<String,Integer> romeToArabic = new TreeMap<>();
    private static TreeMap<Integer,String> arabicToRome = new TreeMap<>(Comparator.reverseOrder());

    private static boolean arabic;
    private static int a;
    private static int b;
    private static char action;
    static {
        romeToArabic.put("I",1);
        romeToArabic.put("II",2);
        romeToArabic.put("III",3);
        romeToArabic.put("IV",4);
        romeToArabic.put("V",5);
        romeToArabic.put("VI",6);
        romeToArabic.put("VII",7);
        romeToArabic.put("VIII",8);
        romeToArabic.put("IX",9);
        romeToArabic.put("X",10);


        arabicToRome.put(100,"C");
        arabicToRome.put(50,"L");
        arabicToRome.put(10,"X");
        arabicToRome.put(9,"IX");
        arabicToRome.put(5,"V");
        arabicToRome.put(4,"IV");
        arabicToRome.put(1,"I");

    }
    public static void main(String[] args) throws Exception {
        BufferedReader exampleReader = new BufferedReader(new InputStreamReader(System.in));
        String example = exampleReader.readLine().replaceAll("\s","");
        exampleReader.close();
        chooseTypeOfExample(example);
        doEvaluation();

    }
    private static void chooseTypeOfExample(String example) throws Exception {
        String algebraRegex = "^(\\d?\\d?|[\\w]{1,4}?)[-+*\\/](\\d?\\d?[\\w]{1,4}?)$";
        String actionRegex = "[+]|[-]|[*]|[\\/]";
        String arabicRegex = "^\\b(1|[2-9]|10)\\b$";
        String[] exampleArray;
        if (example.matches(algebraRegex)){
            exampleArray = example.split(actionRegex);
        } else {
            throw new Exception("Некорректное выражение");
        }
        String tempA = exampleArray[0];
        String tempB = exampleArray[1];

        action = example.charAt(tempA.length());
        if (romeToArabic.keySet().contains(tempA) &&
                romeToArabic.keySet().contains(tempB)) {
            a = romeToArabic.get(tempA);
            b = romeToArabic.get(tempB);
            arabic = false;
        } else if (tempA.matches(arabicRegex) && tempB.matches(arabicRegex)){
            arabic = true;
            a = Integer.parseInt(tempA);
            b = Integer.parseInt(tempB);
        } else {
            throw new Exception("Некорректный ввод");
        }
    }
    private static void doEvaluation() throws Exception {
        int result = 0;
        switch (action) {
            case '+' : {
                result = a + b;
                break;
            }
            case '-' : {
                result = a - b;
                break;
            }
            case '*' : {
                result = a * b;
                break;
            }
            case '/' : {
                result = a / b;
                break;
            }
        }
        if (arabic) {
            System.out.println(result);
        } else if (result > 0) {
            arabicToRome(result);
        } else {
            throw new Exception("Ошибка вычисления, некорректные входные данные");
        }
    }
    private static void arabicToRome(int result){
        String romeResult = "";
        Integer[] keys = arabicToRome.keySet().toArray(new Integer[arabicToRome.keySet().size()]);
        for (int i = 0; i < keys.length; i++){
            Integer key = keys[i];
            if (result == 0) break;
            if (key <= result) {
                result -= key;
                romeResult += arabicToRome.get(key);
                i = 0;
            }
        }
        System.out.println(romeResult);
    }

}