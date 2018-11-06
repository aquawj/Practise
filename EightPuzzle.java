
//
//import java.util.*;
//
//public class EightPuzzle {
// public int m;
// public int n;
// public int[][] matrix;
// public int x;
// public int y;
// public int[][] path = new int[][] {{-1, 0},{1, 0},{0, -1},{0, 1}};
// public String solve;
//
// public EightPuzzle(int[][] matrix) {
//    this.matrix = matrix;
//    m = matrix.length;
//    n = matrix[0].length;
//    int[][] res = new int[m][n];
//    for(int i=0; i<m; i++) {
//        for(int j=0; j<n; j++) {
//            if(matrix[i][j] == 0) {
//                x = i;
//                y = j;
//            }
//            res[i][j] = i * n + j;
//        }
//        solve = getString(res);
//    }
// }
//
// public boolean check() {
//    Queue<int[]> steps = new LinkedList<>();
//    Queue<String> matrixQ = new LinkedList<>();
//    Set<String> set = new HashSet<>();
//    String start = getString(matrix.clone());
//    steps.offer(new int[] {x, y});
//    matrixQ.offer(start);
//    set.add(start);
//    while(!steps.isEmpty()) {
//        int size = steps.size();
//        for(int i=0; i<size; i++) {
//            int[] cur = steps.poll();
//            x = cur[0];
//            y = cur[1];
//            String mm = matrixQ.poll();
//            if(mm.equals(solve)) {
//                return true;
//            }
//            set.add(mm);
//            for(int j=0; j<path.length; j++) {
//                int newX = x + path[j][0];
//                int newY = y + path[j][1];
//                if(newX < 0 || newY < 0 || newX >= m || newY >= n) continue;
//                int[][] tmp = getMatrix(mm);
//                int t = tmp[x][y];
//                tmp[x][y] = tmp[newX][newY];
//                tmp[newX][newY] = t;
//                String next = getString(tmp);
//                if(set.contains(next)) continue;
//                set.add(next);
//                steps.offer(new int[] {newX, newY});
//                matrixQ.offer(next);
//            }
//        }
//    }
//    return false;
// }
//
// public List<String> getPath() {
//   String[] p = new String[] {"left", "right", "down", "up"};
//   Queue<int[]> steps = new LinkedList<>();
//   Queue<String> matrixQ = new LinkedList<>();
//   Set<String> set = new HashSet<>();
//   Queue<List<String>> r = new LinkedList<>();
//   String start = getString(matrix.clone());
//   steps.offer(new int[] {x, y});
//   matrixQ.offer(start);
//   set.add(start);
//   r.offer(new ArrayList<>());
//   while(!steps.isEmpty()) {
//       int size = steps.size();
//       for(int i=0; i<size; i++) {
//           int[] cur = steps.poll();
//           x = cur[0];
//           y = cur[1];
//           String mm = matrixQ.poll();
//           List<String> c = r.poll();
//           if(mm.equals(solve)) {
//               return c;
//           }
//           for(int j=0; j<path.length; j++) {
//               int newX = x + path[j][0];
//               int newY = y + path[j][1];
//               if(newX < 0 || newY < 0 || newX >= m || newY >= n) continue;
//               int[][] tmp = getMatrix(mm);
//               int t = tmp[x][y];
//               tmp[x][y] = tmp[newX][newY];
//               tmp[newX][newY] = t;
//               String next = getString(tmp);
//               if(set.contains(next)) continue;
//               set.add(next);
//               steps.offer(new int[] {newX, newY});
//               matrixQ.offer(next);
//               List<String> newPath = new ArrayList<>(c);
//               newPath.add(p[j]);
//               r.offer(newPath);
//           }
//       }
//   }
//   return new ArrayList<String>();
//}
// private String getString(int[][] matrix) {
//    StringBuilder sb = new StringBuilder();
//    for(int i=0; i<m; i++) {
//        for(int j=0; j<n; j++) {
//            sb.append(matrix[i][j]);
//            sb.append(",");
//        }
//    }
//    return sb.toString();
// }
//
// private int[][] getMatrix(String s) {
//    String[] parts = s.split(",");
//    int[][] res = new int[m][n];
//    for(int i=0; i<m; i++) {
//        for(int j=0; j<n; j++) {
//            res[i][j] = Integer.parseInt(parts[i*n+j]);
//        }
//    }
//    return res;
// }
//
//public static void main(String[] args) {
//int[][] matrix = {
//    {0, 1, 3},
//    {4, 2, 5},
//    {7, 8, 6}
//};
//EightPuzzle ep = new EightPuzzle(matrix);
////System.out.println(ep.canSolve());
//System.out.println(ep.getPath());
//}
//}
/**
 * https://en.wikipedia.org/wiki/15_puzzle
 * 就是wikipeida里的问题换成九宫格，有8个版
 * 这里我们假设空格为0，所以0周围的数字可以与其交换
 *
 * 最好的应该是A*算法，这里用BFS也是可以做的。最好不要DFS，可能会爆栈。
 * 面经里应该只需要判断是否能solve，其实打印出最短路径也是差不多的
 *
 * 比如：
 * Matrix：          RecoveredMatrix:
 * 1 4 5                1 2 3
 * 2 0 7                4 5 6
 * 6 3 8                7 8 0
 */

import java.util.*;

public class EightPuzzle {
    private static final int[][] DIRS = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
    private int[][] matrix;
    private int m;
    private int n;
    private int originX;
    private int originY;
    private String recovered;

    public EightPuzzle(int[][] matrix) {
        this.matrix = matrix;
        this.m = matrix.length;
        this.n = matrix[0].length;
        int[][] recoveredMatrix = new int[m][n]; //目标Matrix：按1-n排列的，0在最后
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    this.originX = i;
                    this.originY = j;
                }

                recoveredMatrix[i][j] = i * n + j+1;  //值就是自然顺序数
            }
        }
        recoveredMatrix[m-1][n-1] = 0; //0在右下角
        this.recovered = getMatrixString(recoveredMatrix); //目标matrix的String
    }

    // Check if it can be solved
    public boolean canSolve() {
        Queue<int[]> elementQ = new LinkedList<>(); //存每一步的0的位置
        Queue<String> matrixQ = new LinkedList<>(); //存每个状态(交换0之后)的matrix
        Set<String> visited = new HashSet<>(); //检查是否某matrix访问过了

        String stringMatrix = getMatrixString(matrix.clone());
        elementQ.offer(new int[] { originX, originY });
        matrixQ.offer(stringMatrix);
        visited.add(stringMatrix);

        while (!elementQ.isEmpty()) {
            int size = elementQ.size();
            for (int i = 0; i < size; i++) {
                int[] curElement = elementQ.poll();
                String curMatrixString = matrixQ.poll();
                int x = curElement[0];
                int y = curElement[1];

                if (curMatrixString.equals(recovered)) {
                    return true; //唯一出口：只有某个状态交换成为目标matrix，才true
                }

                for (int k = 0; k < DIRS.length; k++) { //4个方向
                    int xx = x + DIRS[k][0];
                    int yy = y + DIRS[k][1];

                    if (xx < 0 || xx >= m || yy < 0 || yy >= n) {
                        continue;
                    }
                    //以下 swap 0和邻居
                    int[][] newMatrix = recoverMatrixString(curMatrixString); //复制当前matrix
                    int temp = newMatrix[x][y]; //为了下面swap 0和邻居
                    newMatrix[x][y] = newMatrix[xx][yy]; //将原来0的位置，值变成邻居值
                    newMatrix[xx][yy] = temp; //邻居值 变为0
                    String newMatrixString = getMatrixString(newMatrix);//交换之后的new matrix string
                    if (visited.contains(newMatrixString)) {
                        continue;
                    }
                    //将新的0位置点加入各个queue
                    elementQ.offer(new int[] { xx, yy });
                    matrixQ.offer(newMatrixString);
                    visited.add(newMatrixString);
                }
            }
        }

        return false; //除了上面的唯一出口，其他都是false
    }

    // Print shortest path
    public List<String> getSolution() {
        String[] pathWord = { "Down", "Right", "Up", "Left" }; //增加方向词

        Queue<int[]> elementQ = new LinkedList<>();
        Queue<String> matrixQ = new LinkedList<>();
        Queue<List<String>> pathQ = new LinkedList<>(); //增加存path的Q
        Set<String> visited = new HashSet<>();

        String stringMatrix = getMatrixString(matrix.clone());
        elementQ.offer(new int[] { originX, originY });
        matrixQ.offer(stringMatrix);
        pathQ.offer(new ArrayList<>());
        visited.add(stringMatrix);

        while (!elementQ.isEmpty()) {
            int size = elementQ.size();
            for (int i = 0; i < size; i++) {
                int[] curElement = elementQ.poll();
                String curMatrixString = matrixQ.poll();
                List<String> curPath = pathQ.poll();
                int x = curElement[0];
                int y = curElement[1];

                if (curMatrixString.equals(recovered)) {
                    return curPath;   //唯一出口
                }

                for (int k = 0; k < DIRS.length; k++) {
                    int xx = x + DIRS[k][0];
                    int yy = y + DIRS[k][1];

                    if (xx < 0 || xx >= m || yy < 0 || yy >= n) {
                        continue;
                    }

                    int[][] newMatrix = recoverMatrixString(curMatrixString);
                    int temp = newMatrix[x][y];
                    newMatrix[x][y] = newMatrix[xx][yy];
                    newMatrix[xx][yy] = temp;
                    String newMatrixString = getMatrixString(newMatrix);
                    if (visited.contains(newMatrixString)) {
                        continue;
                    }
                    //新加的部分：path
                    List<String> newPath = new ArrayList<>(curPath); //先复制原path
                    newPath.add(pathWord[k]); //再加上方向词

                    elementQ.offer(new int[] { xx, yy });
                    matrixQ.offer(newMatrixString);
                    pathQ.offer(newPath);
                    visited.add(newMatrixString);
                }
            }
        }

        return new ArrayList<>();
    }

    private String getMatrixString(int[][] matrix) { // Matrix -> String
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(matrix[i][j]).append(",");
            }
        }
        return sb.toString();
    }

    private int[][] recoverMatrixString(String str) { //String -> Matrix
        String[] parts = str.split(",");
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = Integer.parseInt(parts[i * n + j]);
            }
        }
        return res;
    }
    public static void main(String[] args) {
        int[][] matrix = {
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}
        };
        EightPuzzle ep = new EightPuzzle(matrix);
        //System.out.println(ep.canSolve());
        System.out.println(ep.getSolution());
    }
}

/* LC 6个格子
1 2 3
4 5 0

* public int slidingPuzzle(int[][] board) {
        String target = "123450";
        String start = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                start += board[i][j];
            }
        }
        HashSet<String> visited = new HashSet<>();

    // all the positions 0 can be swapped to
// dirs[i] 表示0在index为i时，可以往哪些index挪
// 比如 dirs[0] 表示 当0在index=0的位置时，可以挪动的index为1和3

        int[][] dirs = new int[][] { { 1, 3 }, { 0, 2, 4 },
                { 1, 5 }, { 0, 4 }, { 1, 3, 5 }, { 2, 4 } };
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        visited.add(start);
        int res = 0;
        while (!queue.isEmpty()) {
            // level count, has to use size control here, otherwise not needed
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                if (cur.equals(target)) {
                    return res;
                }
                int zero = cur.indexOf('0');
                // swap if possible
                for (int dir : dirs[zero]) {
                    String next = swap(cur, zero, dir);
                    if (visited.contains(next)) {
                        continue;
                    }
                    visited.add(next);
                    queue.offer(next);

                }
            }
            res++;
        }
        return -1;
    }

    private String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        return sb.toString();
    }*/
