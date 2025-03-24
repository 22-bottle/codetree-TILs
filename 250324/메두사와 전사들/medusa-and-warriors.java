import java.io.*;
import java.util.*;

public class Main {
    static int N, M;
    static int[] medusa = new int[2], park = new int[2];
    static int[][] map, warriorMap, nextWarriorMap;
    static boolean[][] visited;
    static List<int[]> warriorList = new ArrayList<>();

    public static void main(String[] args) {
        init();
        StringBuilder sb = new StringBuilder();
        while (medusa[0] != park[0] || medusa[1] != park[1]) {
            visited[medusa[0]][medusa[1]] = true;
            
        }
    }

    public static void init() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][N];
        visited = new boolean[N][N];
        warriorMap = new int[N][N];
        nextWarriorMap = new int[N][N];
        st = new StringTokenizer(br.readLine());
        medusa[0] = Integer.parseInt(st.nextToken());
        medusa[1] = Integer.parseInt(st.nextToken());
        park[0] = Integer.parseInt(st.nextToken());
        park[1] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            warriorList.add(new int[] {r, c});
            warriorMap[r][c] = 1;
        }
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }
}