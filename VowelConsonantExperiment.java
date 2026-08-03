public class VowelConsonantExperiment {

    static int vowels;
    static int consonants;

    // Recursive Counting
    static void countRecursive(
            String s,
            int index) {

        // Base Case
        if (index == s.length()) {
            return;
        }

        char ch =
                Character.toLowerCase(
                        s.charAt(index));

        if (Character.isLetter(ch)) {

            if ("aeiou".indexOf(ch) != -1) {
                vowels++;
            } else {
                consonants++;
            }
        }

        // Recursive Case
        countRecursive(s, index + 1);
    }

    static boolean hasMoreVowelsRecursive(
            String s) {

        vowels = 0;
        consonants = 0;

        countRecursive(s, 0);

        return vowels > consonants;
    }

    // Iterative Counting
    static boolean hasMoreVowelsIterative(
            String s) {

        int vowel = 0;
        int consonant = 0;

        for (int i = 0; i < s.length(); i++) {

            char ch =
                    Character.toLowerCase(
                            s.charAt(i));

            if (Character.isLetter(ch)) {

                if ("aeiou".indexOf(ch) != -1) {
                    vowel++;
                } else {
                    consonant++;
                }
            }
        }

        return vowel > consonant;
    }

    // สร้างข้อมูลทดสอบ
    static String createTestString(int n) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            sb.append((char) ('a' + (i % 26)));
        }

        return sb.toString();
    }

    // วัดเวลา Recursive
    static double measureRecursive(String data) {

        long totalTime = 0;

        for (int i = 0; i < 5; i++) {

            long start = System.nanoTime();

            hasMoreVowelsRecursive(data);

            long end = System.nanoTime();

            totalTime += (end - start);
        }

        return totalTime / 5.0;
    }

    // วัดเวลา Iterative
    static double measureIterative(String data) {

        long totalTime = 0;

        for (int i = 0; i < 5; i++) {

            long start = System.nanoTime();

            hasMoreVowelsIterative(data);

            long end = System.nanoTime();

            totalTime += (end - start);
        }

        return totalTime / 5.0;
    }

    public static void main(String[] args) {

        int[] sizes = {100, 1000, 10000, 100000};

        System.out.printf("%-10s %-20s %-20s%n",
                "n",
                "Recursive(ns)",
                "Iterative(ns)");

        for (int n : sizes) {

            String data = createTestString(n);

            try {

                double recursiveTime =
                        measureRecursive(data);

                double iterativeTime =
                        measureIterative(data);

                System.out.printf(
                        "%-10d %-20.0f %-20.0f%n",
                        n,
                        recursiveTime,
                        iterativeTime);

            } catch (StackOverflowError e) {

                double iterativeTime =
                        measureIterative(data);

                System.out.printf(
                        "%-10d %-20s %-20.0f%n",
                        n,
                        "StackOverflow",
                        iterativeTime);
            }
        }
    }
}