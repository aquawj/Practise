import java.util.HashMap;
import java.util.Map;

//https://en.wikipedia.org/wiki/Collatz_conjecture
//题目是给你公式，比如偶数的话除2，奇数的话就变成3*n+1，对于任何一个正数，数学猜想是最
//终他会变成1。每走一步步数加1，给一个上限，找出范围内的最长步数。

public class CollatzConjecture {

    //最优：Collatz Conjecture - Memorization

        Map<Integer, Integer> map = new HashMap<>();

        private int findSteps(int num) {
            //这种不太理解
//            if (num <= 1) return 1;
//            if (map.containsKey(num)) return map.get(num);
//            if (num % 2 == 0) num = num / 2;
//            else num = 3 * num + 1;
//            if (map.containsKey(num)) return map.get(num) + 1;
//            int t = findSteps(num);
//            map.put(num, t);
//            return t + 1;
            if(num <= 1) return 1;
            if(map.containsKey(num)) return map.get(num); //为什么map.get()在这一步
            if(num % 2 == 0) return findSteps(num / 2) + 1;
            else return findSteps(num * 3 + 1) + 1;
        }

        public int findLongestSteps(int num) { //找比1-num范围内，最长步数
            if (num < 1) return 0;

            int res = 0;
            for (int i = 1; i <= num; i++) {
                int t = findSteps(i);
                map.put(i, t);  //这里放map
                res = Math.max(res, t);
            }

            return res;
        }

        public static void main(String[] args){
            CollatzConjecture c = new CollatzConjecture();
            System.out.println(c.findSteps(6));
            System.out.println(c.findLongestSteps(6));
        }

        /* 原始做法：没有用map来memorize
        private int findSteps(int num) {
            if (num <= 1) {
                return 1;
            }
            if (num % 2 == 0) {
                return 1 + findSteps(num / 2);
            }else {
                return 1 + findSteps(3 * num + 1);
            }
        }

        public int findLongestSteps(int num) {
            if (num < 1) return 0;

            int res = 0;
            for (int i = 1; i <= num; i++) {
                int t = findSteps(i);
                res = Math.max(res, t);
            }

            return res;
        }
    */



}
