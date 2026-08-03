public class ReverseStringExperiment {

    // Recursive Algorithm
    static String reverseRecursive(String s) {

        // Base Case
        if (s.length() <= 1) {
            return s;
        }

        // Recursive Case
        return s.charAt(s.length() - 1)
                + reverseRecursive(
                        s.substring(0,
                                s.length() - 1));
    }

    // Iterative Algorithm
    static String reverseIterative(String s) {

        StringBuilder result = new StringBuilder();

        for (int i = s.length() - 1; i >= 0; i--) {
            result.append(s.charAt(i));
        }

        return result.toString();
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

            reverseRecursive(data);

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

            reverseIterative(data);

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