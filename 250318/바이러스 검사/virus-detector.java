import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, jang, won;
    static int[] rests;
    
    public static void main(String[] args) throws Exception {
        init();
        long answer = N;
        for (int i = 0; i < N; i++) {
            rests[i] -= jang;
            if (rests[i] <= 0) continue;
            answer += rests[i] / won;
            if (rests[i] % won != 0) answer++;
        }
        System.out.println(answer);
    }

    public static void init() throws Exception {
        N = Integer.parseInt(br.readLine());
        rests = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            rests[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        jang = Integer.parseInt(st.nextToken());
        won = Integer.parseInt(st.nextToken());
    }
}