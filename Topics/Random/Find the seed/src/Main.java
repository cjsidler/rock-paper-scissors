import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int n = scanner.nextInt();
        int k = scanner.nextInt();

        int minimumSeed = a;
        int maximum = k + 1;

        for (int i = a; i <= b; i++) {
            Random random = new Random(i);

            int currentMaximum = -1;

            for (int j = 0; j < n; j++) {
                currentMaximum = Math.max(currentMaximum, random.nextInt(k));
            }

            if (currentMaximum < maximum) {
                minimumSeed = i;
                maximum = currentMaximum;
            }
        }

        System.out.println(minimumSeed);
        System.out.println(maximum);
    }
}