import java.util.Arrays;

//API 返回1个digit， 猜中1个正确的数字并且该数字位置也正确。

public class GuessNumber2 {

    private static String target = "4361";

    //位置正确+数字正确 才算数
    public static int check(String guess) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            if (target.charAt(i) == guess.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    private static String client(){
        char[] result = new char[4];
        Arrays.fill(result, '0');
        String base = "1111";
        System.out.println("Server call: " + base);
        int baseResult = check(base);
        System.out.println("right numbers: " + baseResult);
        if (baseResult == 4) {
            return base;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 6; j++) {
                String newS = replace(base, i, (char) (j + '0'));
                System.out.println("Server call: " + newS);
                int newResult = check(newS);
                System.out.println("right numbers: " + newResult);
                if (newResult != baseResult) {
                    result[i] = baseResult > newResult ? '1' : (char) (j + '0');
                    break;
                }
            }
            if (result[i] == '0') {
                result[i] = '6';
            }
        }
        return new String(result);
    }

    private static String replace(String s, int index, char c) {
        char[] arr = s.toCharArray();
        arr[index] = c;
        return new String(arr);
    }

    public static void main(String args[]) {
        System.out.println("guess result: " + client());
    }

    //PI 返回2个digit，pos 0 代表数字正确且位置正确的个数，pos 1 代表数字正确但是位置不正确的个数。
    //第二种有一点儿脑筋急转弯的意思，先遍历一遍但是目的不是为了找全对的而是为了找全错的，
    // 然后拿这个全错的做基准，每一位从1试到6这样就能分别找出每一位的正确数字了
}
