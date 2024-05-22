package com.serediuk.bander_client.util;

public class StringHelper {
    private static final int ALLOWED_DISTANCE = 3;

    public static boolean inString(String value, String string) {
        String[] parts = string.split(" ");
        for (String part : parts) {
            if (levenshteinDistance(value, part) <= ALLOWED_DISTANCE) {
                return true;
            }
        }
        return false;
    }

    public static int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1));
                }
            }
        }

        return dp[a.length()][b.length()];
    }
}
