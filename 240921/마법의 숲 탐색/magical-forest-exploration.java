import java.io.*;
import java.util.*;

public class Main {
    static int R, C, K, r, c, d;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int[][] map;
    static boolean[][] visited, exit;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        init();

        int answer = 0;
        for (int i = 1; i <= K; i++) {
            st = new StringTokenizer(br.readLine());
            r = -1; c = Integer.parseInt(st.nextToken()); d = Integer.parseInt(st.nextToken());
            while (true) {
                if (canGoDown()) r++;
                else if (canGoLeft()) {
                    c--;
                    d = d == 0 ? 3 : d - 1;
                } else if (canGoRight()) {
                    c++;
                    d = (d + 1) % 4;
                } else {
                    if (r <= 1) init();
                    else {
                        fill(i);
                        answer += move();
                    }
                    break;
                }
            }
        }
        System.out.println(answer);
    }

    public static void init() {
        map = new int[R + 1][C + 1];
        exit = new boolean[R + 1][C + 1];
    }

    public static boolean canGoDown() {
        for (int d = 1; d < 4; d++) {
            int tr = r + dr[d] + 1;
            int tc = c + dc[d];
            if (tr > R) return false;
            if (map[tr][tc] != 0) return false;
        }
        return true;
    }

    public static boolean canGoLeft() {
        for (int d = 0; d < 4; d++) {
            int tr = r + dr[d];
            int tc = c + dc[d] - 1;
            if (tc <= 0) return false;
            if (tr >= 0 && map[tr][tc] != 0) return false;
            tr++;
            if (tr > R) return false;
            if (tr >= 0 && map[tr][tc] != 0) return false;
        }
        return true;
    }

    public static boolean canGoRight() {
        for (int d = 0; d < 4; d++) {
            int tr = r + dr[d];
            int tc = c + dc[d] + 1;
            if (tc > C) return false;
            if (tr >= 0 && map[tr][tc] != 0) return false;
            tr++;
            if (tr > R) return false;
            if (tr >= 0 && map[tr][tc] != 0) return false;
        }
        return true;
    }

    public static void fill(int n) {
        map[r][c] = n;
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            map[nr][nc] = n;
        }
        exit[r + dr[d]][c + dc[d]] = true;
    }

    public static int move() {
        visited = new boolean[R + 1][C + 1];
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[] {r, c});
        visited[r][c] = true;
        int max = Integer.MIN_VALUE;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int n = map[cur[0]][cur[1]];
            boolean e = exit[cur[0]][cur[1]];
            max = Integer.max(max, cur[0]);
            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (!canMove(nr, nc, n, e)) continue;
                q.offer(new int[] {nr, nc});
                visited[nr][nc] = true;
            }
        }
        return max;
    }

    public static boolean canMove(int r, int c, int n, boolean e) {
        if (r <= 0 || c <= 0 || r > R || c > C) return false;
        if (visited[r][c]) return false;
        if (map[r][c] == 0) return false;
        if (e) return true;
        return map[r][c] == n;
    }
}