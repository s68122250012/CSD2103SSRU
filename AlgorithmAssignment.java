import java.util.Arrays;
import java.util.Random;

/**
 * แบบฝึกหัดการออกแบบอัลกอริทึมแบบเวียนเกิดและการวิเคราะห์ Big-O
 * ------------------------------------------------------------
 * ไฟล์นี้รวมคำตอบของข้อ 1 - 6 ไว้ในคลาสเดียวเพื่อความสะดวกในการคอมไพล์และรัน
 * แต่ละข้อมีอัลกอริทึมอย่างน้อย 2 วิธีตามที่โจทย์กำหนด พร้อมทั้งมี main()
 * ที่สาธิตผลลัพธ์ด้วยตัวอย่างจากโจทย์ และมีการวัดเวลาการทำงานจริง (Problem 1, Problem 6)
 */
public class AlgorithmAssignment {

    // ==========================================================
    // ข้อ 1: การกลับลำดับสตริง (Reverse a String)
    // ==========================================================

    /**
     * วิธีที่ 1: Recursive
     * แนวคิด: นำตัวอักษรตัวสุดท้ายของ s มาต่อไว้ข้างหน้า แล้วเรียกเมธอดซ้ำ
     * กับสตริงที่ตัดตัวสุดท้ายออก จนกระทั่งเหลือสตริงว่างหรือ 1 ตัวอักษร (Base Case)
     * หมายเหตุ: การต่อสตริงด้วย + ในแต่ละชั้นของการเรียกจะสร้าง String ใหม่ทุกครั้ง
     * (String เป็น immutable) จึงมีผลต่อ Time Complexity ที่แท้จริง (ดูการวิเคราะห์ท้ายไฟล์)
     */
    static String reverseRecursive(String s) {
        // Base case: สตริงว่างหรือมีความยาว 1 ตัวอักษร ถือว่ากลับด้านแล้ว
        if (s.length() <= 1) {
            return s;
        }
        // Recursive case: ตัวอักษรตัวสุดท้าย + ผลลัพธ์การกลับด้านของส่วนที่เหลือ
        char lastChar = s.charAt(s.length() - 1);
        String rest = s.substring(0, s.length() - 1);
        return lastChar + reverseRecursive(rest);
    }

    /**
     * วิธีที่ 2: Iterative
     * แนวคิด: ใช้ลูปอ่านตัวอักษรจากตำแหน่งสุดท้ายไปยังตำแหน่งแรก แล้วต่อเข้ากับ
     * StringBuilder (mutable) เพื่อหลีกเลี่ยงการสร้าง String ใหม่ซ้ำ ๆ
     */
    static String reverseIterative(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    // ==========================================================
    // ข้อ 2: การตรวจสอบ Palindrome
    // ==========================================================

    /**
     * วิธีที่ 1: Reverse and Compare
     * แนวคิด: สร้างสตริงย้อนกลับก่อน แล้วเปรียบเทียบกับสตริงเดิมทั้งสตริง
     */
    static boolean isPalindromeByReverse(String s) {
        StringBuilder sb = new StringBuilder(s);
        String reversed = sb.reverse().toString();
        return s.equals(reversed);
    }

    /**
     * วิธีที่ 2: Recursive Two-Pointer
     * แนวคิด: เปรียบเทียบตัวอักษรซ้ายสุด (left) และขวาสุด (right)
     * - Base case: left >= right แปลว่าตรวจครบทุกคู่แล้ว และไม่พบความต่าง -> true
     * - หากตัวอักษรที่ left และ right ต่างกัน -> false ทันที (หยุดก่อนครบทุกตัวอักษรได้)
     * - หากเหมือนกัน เรียกเมธอดซ้ำกับคู่ถัดไป (left+1, right-1)
     */
    static boolean isPalindromeRecursive(String s, int left, int right) {
        if (left >= right) {
            return true; // ตรวจครบทุกคู่แล้ว
        }
        if (s.charAt(left) != s.charAt(right)) {
            return false; // เจอคู่ที่ไม่ตรงกัน หยุดทันทีโดยไม่ต้องตรวจต่อ
        }
        return isPalindromeRecursive(s, left + 1, right - 1);
    }

    /**
     * เงื่อนไขเพิ่มเติม: ทำความสะอาดสตริงก่อนตรวจสอบ โดยตัดช่องว่าง เครื่องหมายวรรคตอน
     * และแปลงเป็นตัวพิมพ์เล็กทั้งหมด เช่น "A man, a plan, a canal: Panama" -> "amanaplanacanalpanama"
     */
    static String cleanForPalindrome(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    // ==========================================================
    // ข้อ 3: การเปรียบเทียบจำนวนสระและพยัญชนะ
    // ==========================================================

    private static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    /**
     * วิธีที่ 1: Recursive Counting
     * แนวคิด: ตรวจตัวอักษรทีละตัว (index) แล้วส่งค่า "ผลต่างสุทธิ" (สระ - พยัญชนะ)
     * ของส่วนที่เหลือกลับขึ้นมารวมกันแบบเวียนเกิด (ผ่านเมธอดช่วย countNetRecursive)
     * เพื่อให้เมธอดหลักคงลายเซ็นตามที่โจทย์กำหนดคือรับพารามิเตอร์ String s เพียงตัวเดียว
     */
    static boolean hasMoreVowelsRecursive(String s) {
        return countNetRecursive(s, 0) > 0;
    }

    private static int countNetRecursive(String s, int index) {
        // Base case: อ่านครบทุกตัวอักษรแล้ว ไม่มีผลต่างเพิ่มอีก
        if (index == s.length()) {
            return 0;
        }
        char c = Character.toLowerCase(s.charAt(index));
        int current = 0;
        if (Character.isLetter(c)) {
            current = isVowel(c) ? 1 : -1; // สระ +1, พยัญชนะ -1, ตัวเลข/สัญลักษณ์ไม่นับ
        }
        // Recursive case: ผลของตัวอักษรนี้ + ผลรวมของตัวอักษรที่เหลือ
        return current + countNetRecursive(s, index + 1);
    }

    /**
     * วิธีที่ 2: Iterative Counting
     * แนวคิด: ใช้ลูปอ่านทุกตัวอักษร แล้วเพิ่มตัวนับสระหรือพยัญชนะตามเงื่อนไข
     */
    static boolean hasMoreVowelsIterative(String s) {
        int vowelCount = 0;
        int consonantCount = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = Character.toLowerCase(s.charAt(i));
            if (Character.isLetter(c)) {
                if (isVowel(c)) {
                    vowelCount++;
                } else {
                    consonantCount++;
                }
            }
            // ไม่นับตัวเลข ช่องว่าง และเครื่องหมายพิเศษ
        }
        return vowelCount > consonantCount;
    }

    // ==========================================================
    // ข้อ 4: การจัดกลุ่มจำนวนคู่และจำนวนคี่
    // ==========================================================

    /**
     * วิธีที่ 1: Recursive Two-Pointer
     * แนวคิด: left เดินหน้าถ้าพบเลขคู่, right ถอยหลังถ้าพบเลขคี่,
     * ถ้า left เป็นคี่และ right เป็นคู่ ให้สลับค่ากัน แล้วเรียกซ้ำกับช่วงที่แคบลง
     */
    static void rearrangeRecursive(int[] a, int left, int right) {
        // Base case: ตัวชี้ไขว้กันหรือชนกันแล้ว ไม่มีสมาชิกให้ตรวจต่อ
        if (left >= right) {
            return;
        }
        if (a[left] % 2 == 0) {
            rearrangeRecursive(a, left + 1, right);
        } else if (a[right] % 2 != 0) {
            rearrangeRecursive(a, left, right - 1);
        } else {
            swap(a, left, right);
            rearrangeRecursive(a, left + 1, right - 1);
        }
    }

    /**
     * วิธีที่ 2: Iterative Two-Pointer
     * แนวคิด: หลักการเดียวกับวิธีที่ 1 แต่ใช้ลูป while แทนการเวียนเกิด
     * (ประหยัดหน่วยความจำ stack เพราะไม่มี call stack สะสม)
     */
    static void rearrangeTwoPointer(int[] a) {
        int left = 0;
        int right = a.length - 1;
        while (left < right) {
            if (a[left] % 2 == 0) {
                left++;
            } else if (a[right] % 2 != 0) {
                right--;
            } else {
                swap(a, left, right);
                left++;
                right--;
            }
        }
    }

    /**
     * วิธีที่ 3: Extra Array
     * แนวคิด: สร้างอาร์เรย์ใหม่ วนอ่านอาร์เรย์เดิมรอบแรกเพื่อคัดจำนวนคู่ (เรียงตามลำดับเดิม)
     * แล้ววนรอบสองเพื่อคัดจำนวนคี่ต่อท้าย -> วิธีนี้เป็น Stable เพราะไม่มีการสลับตำแหน่งข้าม
     * กลุ่ม จึงรักษาลำดับสัมพัทธ์เดิมของสมาชิกแต่ละกลุ่มไว้ได้
     */
    static int[] rearrangeExtraArray(int[] a) {
        int[] result = new int[a.length];
        int index = 0;
        for (int value : a) {
            if (value % 2 == 0) {
                result[index++] = value;
            }
        }
        for (int value : a) {
            if (value % 2 != 0) {
                result[index++] = value;
            }
        }
        return result;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // ==========================================================
    // ข้อ 5: การแบ่งอาร์เรย์ตามค่า k
    // ==========================================================

    /**
     * วิธีที่ 1: Recursive Partition
     * แนวคิด: เหมือน Two-Pointer Partition ของ Quick Sort แต่เขียนแบบเวียนเกิด
     */
    static void partitionRecursive(int[] a, int k, int left, int right) {
        if (left > right) {
            return; // Base case: ตัวชี้ไขว้กันแล้ว
        }
        if (a[left] <= k) {
            partitionRecursive(a, k, left + 1, right);
        } else if (a[right] > k) {
            partitionRecursive(a, k, left, right - 1);
        } else {
            swap(a, left, right);
            partitionRecursive(a, k, left + 1, right - 1);
        }
    }

    /**
     * วิธีที่ 2: Iterative Partition
     * แนวคิด: เหมือนขั้นตอน Partition ของ Quick Sort (Lomuto/Hoare style)
     * ทำงานแบบ In-place ด้วยตัวชี้สองตำแหน่ง
     */
    static void partitionIterative(int[] a, int k) {
        int left = 0;
        int right = a.length - 1;
        while (left <= right) {
            if (a[left] <= k) {
                left++;
            } else if (a[right] > k) {
                right--;
            } else {
                swap(a, left, right);
                left++;
                right--;
            }
        }
    }

    /**
     * วิธีที่ 3: Sorting-Based
     * แนวคิด: เรียงอาร์เรย์ทั้งหมดก่อนด้วย Arrays.sort (O(n log n))
     * ผลลัพธ์ที่ได้จะเรียงจากน้อยไปมากทั้งหมด ซึ่ง "เกินความจำเป็น" ของโจทย์
     * (โจทย์ต้องการแค่แบ่งกลุ่ม ไม่ต้องเรียงลำดับภายในกลุ่ม)
     */
    static void partitionBySorting(int[] a, int k) {
        Arrays.sort(a); // ทำงาน in-place บน primitive array
        // หมายเหตุ: ไม่จำเป็นต้องใช้ k ในการเรียง แต่ค่า k ใช้ตอนวิเคราะห์ว่า
        // ตำแหน่งสุดท้ายที่ <= k อยู่ที่ index ใด (ทำได้ด้วย Binary Search เพิ่มเติม)
    }

    // ==========================================================
    // ข้อ 6: การค้นหาคู่จำนวนที่มีผลรวมเท่ากับ k
    // ==========================================================

    /**
     * วิธีที่ 1: Brute Force
     * แนวคิด: ตรวจสอบทุกคู่ (i, j) ที่เป็นไปได้ด้วยลูปซ้อน
     */
    static boolean findPairBruteForce(int[] a, int k) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (a[i] + a[j] == k) {
                    System.out.println("Pair found: " + a[i] + " and " + a[j]);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * วิธีที่ 2: Recursive Two-Pointer (ใช้ได้เพราะอาร์เรย์เรียงลำดับแล้ว)
     * แนวคิด: left เริ่มที่ 0, right เริ่มที่ n-1
     * - ถ้าผลรวม == k -> พบคำตอบ
     * - ถ้าผลรวม < k -> เพิ่ม left เพื่อขยับผลรวมให้มากขึ้น
     * - ถ้าผลรวม > k -> ลด right เพื่อขยับผลรวมให้น้อยลง
     */
    static boolean findPairRecursive(int[] a, int k, int left, int right) {
        if (left >= right) {
            return false; // Base case: ตัวชี้ชนกัน ไม่พบคู่ที่ผลรวมตรงกับ k
        }
        int sum = a[left] + a[right];
        if (sum == k) {
            System.out.println("Pair found: " + a[left] + " and " + a[right]);
            return true;
        } else if (sum < k) {
            return findPairRecursive(a, k, left + 1, right);
        } else {
            return findPairRecursive(a, k, left, right - 1);
        }
    }

    /**
     * วิธีที่ 3: Binary Search
     * แนวคิด: เลือกสมาชิก a[i] ทีละตัว แล้วใช้ Binary Search หาค่า k - a[i]
     * ในส่วนของอาร์เรย์ที่อยู่ถัดจาก i (เพื่อไม่ให้จับคู่กับตัวมันเองหรือคู่ซ้ำ)
     */
    static boolean findPairBinarySearch(int[] a, int k) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int target = k - a[i];
            int lo = i + 1;
            int hi = n - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (a[mid] == target) {
                    System.out.println("Pair found: " + a[i] + " and " + a[mid]);
                    return true;
                } else if (a[mid] < target) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }
        return false;
    }

    // ==========================================================
    // การทดลองวัดเวลาการทำงานจริง (เลือกข้อ 1 และข้อ 6 ตามที่โจทย์กำหนด "อย่างน้อย 2 ข้อ")
    // ==========================================================

    private static final int TRIALS = 5;
    private static final Random RNG = new Random(42); // seed คงที่เพื่อผลลัพธ์ทำซ้ำได้

    private static String randomString(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append((char) ('a' + RNG.nextInt(26)));
        }
        return sb.toString();
    }

    private static int[] randomSortedUniqueArray(int n) {
        // สร้างค่าที่ไม่ซ้ำกันแล้วเรียงลำดับ เพื่อให้ตรงกับเงื่อนไขของข้อ 6
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i * 2; // 0, 2, 4, ... รับประกันว่าไม่ซ้ำและเรียงแล้ว
        }
        return a;
    }

    private static void benchmarkProblem1() {
        System.out.println("\n=== การวัดเวลา: ข้อ 1 การกลับลำดับสตริง ===");
        int[] sizes = {100, 1_000, 10_000, 100_000};
        System.out.printf("%-10s %-25s %-25s%n", "n", "Recursive (avg ns)", "Iterative (avg ns)");
        for (int n : sizes) {
            String s = randomString(n);

            long recursiveTotal = 0;
            boolean recursiveOk = true;
            for (int t = 0; t < TRIALS && recursiveOk; t++) {
                try {
                    long start = System.nanoTime();
                    reverseRecursive(s);
                    long end = System.nanoTime();
                    recursiveTotal += (end - start);
                } catch (StackOverflowError e) {
                    recursiveOk = false; // recursion ลึกเกินไปสำหรับ n นี้
                }
            }

            long iterativeTotal = 0;
            for (int t = 0; t < TRIALS; t++) {
                long start = System.nanoTime();
                reverseIterative(s);
                long end = System.nanoTime();
                iterativeTotal += (end - start);
            }

            String recursiveResult = recursiveOk
                    ? String.valueOf(recursiveTotal / TRIALS)
                    : "StackOverflowError";
            System.out.printf("%-10d %-25s %-25d%n", n, recursiveResult, iterativeTotal / TRIALS);
        }
    }

    private static void benchmarkProblem6() {
        System.out.println("\n=== การวัดเวลา: ข้อ 6 การค้นหาคู่จำนวนที่มีผลรวมเท่ากับ k (worst case: ไม่มีคำตอบ) ===");
        int[] sizes = {100, 1_000, 10_000, 100_000};
        System.out.printf("%-10s %-20s %-20s %-20s%n", "n", "Brute (avg ns)", "TwoPointer (avg ns)", "BinarySearch (avg ns)");
        for (int n : sizes) {
            int[] a = randomSortedUniqueArray(n);
            int k = -1; // ค่าที่หาไม่เจอแน่นอน (ค่าทุกตัวใน a เป็น >= 0) เพื่อวัด worst case จริง ๆ

            long bruteTotal = 0;
            for (int t = 0; t < TRIALS; t++) {
                long start = System.nanoTime();
                findPairBruteForceSilent(a, k);
                long end = System.nanoTime();
                bruteTotal += (end - start);
            }

            long twoPointerTotal = 0;
            for (int t = 0; t < TRIALS; t++) {
                long start = System.nanoTime();
                findPairRecursive(a, k, 0, a.length - 1);
                long end = System.nanoTime();
                twoPointerTotal += (end - start);
            }

            long binaryTotal = 0;
            for (int t = 0; t < TRIALS; t++) {
                long start = System.nanoTime();
                findPairBinarySearchSilent(a, k);
                long end = System.nanoTime();
                binaryTotal += (end - start);
            }

            System.out.printf("%-10d %-20d %-20d %-20d%n",
                    n, bruteTotal / TRIALS, twoPointerTotal / TRIALS, binaryTotal / TRIALS);
        }
    }

    // เวอร์ชันไม่ print เพื่อไม่ให้ I/O รบกวนผลการวัดเวลา (ใช้เฉพาะตอน benchmark)
    private static boolean findPairBruteForceSilent(int[] a, int k) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (a[i] + a[j] == k) return true;
            }
        }
        return false;
    }

    private static boolean findPairBinarySearchSilent(int[] a, int k) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int target = k - a[i];
            int lo = i + 1, hi = n - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (a[mid] == target) return true;
                else if (a[mid] < target) lo = mid + 1;
                else hi = mid - 1;
            }
        }
        return false;
    }

    // ==========================================================
    // main: สาธิตผลลัพธ์ของทุกข้อด้วยตัวอย่างจากโจทย์ + รันการวัดเวลา
    // ==========================================================
    public static void main(String[] args) {

        System.out.println("=== ข้อ 1: การกลับลำดับสตริง ===");
        String s1 = "pots&pans";
        System.out.println("Input: " + s1);
        System.out.println("reverseRecursive  -> " + reverseRecursive(s1));
        System.out.println("reverseIterative  -> " + reverseIterative(s1));

        System.out.println("\n=== ข้อ 2: การตรวจสอบ Palindrome ===");
        String[] palTests = {"racecar", "level", "algorithm", "gohangasalamiimalasagnahog"};
        for (String t : palTests) {
            System.out.printf("%-30s isPalindromeByReverse=%-6b isPalindromeRecursive=%-6b%n",
                    t, isPalindromeByReverse(t), isPalindromeRecursive(t, 0, t.length() - 1));
        }
        String rawPhrase = "A man, a plan, a canal: Panama";
        String cleaned = cleanForPalindrome(rawPhrase);
        System.out.println("Input (มีช่องว่าง/เครื่องหมายวรรคตอน): " + rawPhrase);
        System.out.println("หลังทำความสะอาด: " + cleaned);
        System.out.println("ผลลัพธ์ -> " + isPalindromeByReverse(cleaned));

        System.out.println("\n=== ข้อ 3: การเปรียบเทียบจำนวนสระและพยัญชนะ ===");
        String s3 = "education";
        System.out.println("Input: " + s3);
        System.out.println("hasMoreVowelsRecursive -> " + hasMoreVowelsRecursive(s3));
        System.out.println("hasMoreVowelsIterative -> " + hasMoreVowelsIterative(s3));

        System.out.println("\n=== ข้อ 4: การจัดกลุ่มจำนวนคู่และจำนวนคี่ ===");
        int[] a4 = {7, 2, 9, 4, 1, 6, 3, 8};
        int[] a4copy1 = a4.clone();
        rearrangeRecursive(a4copy1, 0, a4copy1.length - 1);
        System.out.println("Input: " + Arrays.toString(a4));
        System.out.println("rearrangeRecursive   -> " + Arrays.toString(a4copy1));
        int[] a4copy2 = a4.clone();
        rearrangeTwoPointer(a4copy2);
        System.out.println("rearrangeTwoPointer  -> " + Arrays.toString(a4copy2));
        System.out.println("rearrangeExtraArray  -> " + Arrays.toString(rearrangeExtraArray(a4)));

        int[] stableTest = {5, 2, 7, 4, 9, 6};
        System.out.println("\nทดสอบความเสถียร (Stable) ด้วย Input: " + Arrays.toString(stableTest));
        System.out.println("rearrangeExtraArray (คาดว่า Stable) -> " + Arrays.toString(rearrangeExtraArray(stableTest)));
        int[] stableTest2 = stableTest.clone();
        rearrangeTwoPointer(stableTest2);
        System.out.println("rearrangeTwoPointer (ไม่รับประกัน Stable) -> " + Arrays.toString(stableTest2));

        System.out.println("\n=== ข้อ 5: การแบ่งอาร์เรย์ตามค่า k ===");
        int[] a5 = {12, 4, 7, 15, 3, 10, 8};
        int k5 = 8;
        int[] a5copy1 = a5.clone();
        partitionRecursive(a5copy1, k5, 0, a5copy1.length - 1);
        System.out.println("Input: " + Arrays.toString(a5) + ", k = " + k5);
        System.out.println("partitionRecursive -> " + Arrays.toString(a5copy1));
        int[] a5copy2 = a5.clone();
        partitionIterative(a5copy2, k5);
        System.out.println("partitionIterative -> " + Arrays.toString(a5copy2));
        int[] a5copy3 = a5.clone();
        partitionBySorting(a5copy3, k5);
        System.out.println("partitionBySorting -> " + Arrays.toString(a5copy3) + " (เรียงทั้งหมด ซึ่งเกินความจำเป็นของโจทย์)");

        System.out.println("\n=== ข้อ 6: การค้นหาคู่จำนวนที่มีผลรวมเท่ากับ k ===");
        int[] a6 = {2, 4, 7, 11, 15, 20};
        int k6 = 18;
        System.out.println("Input: " + Arrays.toString(a6) + ", k = " + k6);
        System.out.print("findPairBruteForce   -> ");
        findPairBruteForce(a6, k6);
        System.out.print("findPairRecursive    -> ");
        findPairRecursive(a6, k6, 0, a6.length - 1);
        System.out.print("findPairBinarySearch -> ");
        findPairBinarySearch(a6, k6);

        // งานทดลองเปรียบเทียบประสิทธิภาพ (เลือกข้อ 1 และข้อ 6)
        benchmarkProblem1();
        benchmarkProblem6();
    }
}
