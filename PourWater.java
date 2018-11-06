
public class PourWater {
    /*Input: heights = [2,1,1,2,1,2,2], V = 4, K = 3 从index K开始倒水，总量为V
    Output: [2,2,2,3,2,2,2]
    Explanation:
            #       #
            #       #
            ##  # ###
            #########
             0123456    <- index

    The final answer is [2,2,2,3,2,2,2]:
        w
     #ww#w##
     #######
     0123456

    */
    //
    public void pourWater(int[] heights, int water, int location){
        int[] waters = new int[heights.length];

        while(water > 0){
            int pour = location;
            for(int d = -1; d <= 1; d += 2){
                int i = location + d;
                //保持递减序列 non-decreasing sequence
                // i <= i - d 说明要使劲往左走
                while(i >= 0 && i < heights.length && heights[i] + waters[i] <= heights[i - d] + waters[i - d]){
                    if(heights[i] + waters[i] <= heights[pour] + waters[pour]){
                        pour = i;
                    }
                    i += d;
                }
                if(pour != location){
                    break;//不管left还是right，总之找到了滴水点，这滴水结束
                }
            }
            waters[pour]++;
            water--;
        }

        print(heights, waters);
    }

    public void pourWaterWithoutWall(int[] heights, int water, int location){
        int[] waters = new int[heights.length];

        while(water > 0){
            int pour = location;
            for(int d = -1; d <= 1; d += 2){
                int i = location + d;
                //保持递减序列 non-decreasing sequence
                while(heights[i] + waters[i] <= heights[i - d] + waters[i - d]){ // i <= i - d 说明要使劲往左走
                    pour = i;
                    i += d;
                    if(i == -1){
                        pour = -1;
                        break;
                    }
                    if(i == heights.length){
                        pour = heights.length;
                        break;
                    }
                }

                if(pour != location){
                    break;//不管left还是right，总之找到了滴水点，这滴水结束
                }
            }
            if(pour != -1 && pour != heights.length){
                waters[pour]++;
            }
            water--;
        }

        print(heights, waters);
    }


    public void pourWater1(int[] heights, int water, int location) {
        int[] waters = new int[heights.length];
        int pourLocation;
        while (water > 0) {
            int left = location - 1;
            while (left >= 0) {
                if (heights[left] + waters[left] > heights[left + 1] + waters[left
                        + 1]) {
                    break;
                }
                left--;
            }
            if (heights[left + 1] + waters[left + 1] < heights[location] +
                    waters[location]) {
                pourLocation = left + 1;
                waters[pourLocation]++;
                water--;
                continue;
            }
            int right = location + 1;
            while (right < heights.length) {
                if (heights[right] + waters[right] > heights[right - 1] +
                        waters[right - 1]) {
                    break;
                }
                right++;
            }
            if (heights[right - 1] + waters[right - 1] < heights[location] +
                    waters[location]) {
                pourLocation = right - 1;
                waters[pourLocation]++;
                water--;
                continue;
            }
            pourLocation = location;
            waters[pourLocation]++;
            water--;
        }
        print(heights, waters);
    }
    private void print(int[] heights, int[] waters) {
        int n = heights.length;
        int maxHeight = 0;
        for (int i = 0; i < n; i++) {
            maxHeight = Math.max(maxHeight, heights[i] + waters[i]);
        }
        for (int height = maxHeight; height > 0; height--) {
            for (int i = 0; i < n; i++) {
                if (height <= heights[i]) {
                    System.out.print("+");
                } else if (height > heights[i] && height <= heights[i] + waters[i])
                {
                    System.out.print("W");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }


    //时间 O(length * V) 每滴水寻找位置需要O(length)的时间
    //空间 O(1)
    public int[] pourWater2(int[] heights, int V, int K){
        if(heights == null || heights.length == 0 || V <= 0){
            return heights;
        }
        while(V > 0){
            int pourPosition = K;
            // left
            for(int i = K - 1; i >= 0; i--){
                if(heights[i] > heights[pourPosition]){
                    break;
                }
                else if(heights[i] < heights[pourPosition]){
                    pourPosition = i;
                }
            }
            if(pourPosition != K){
                V--;
                heights[pourPosition]++;
                continue;
            }
            //right
            for(int i = K + 1; i < heights.length; i++){
                if(heights[i] > heights[pourPosition]){
                    break;
                }
                else if(heights[i] < heights[pourPosition]){
                    pourPosition = i;
                }
            }
            //right and original place
            V--;
            heights[pourPosition]++;
        }
        return heights;
    }

    //Follow up 是打印出形状(github) 和 假设两端不是高地，水会流出去，怎么办(参考 LC42 trapping rain water)？
    //todo:

    public static void main(String[] args){
        PourWater p = new PourWater();
        int[] heights = {2,1,1,2,1,2,2};
        //int[] result = p.pourWater(heights, 3, 4);
//        for(int i = 0; i < heights.length; i++){
//            System.out.print(result[i] + " ");
//        }
        /*System.out.println("pourwater:");
        p.pourWater(heights, 2, 4);
        System.out.println("pourwater1:");
        p.pourWater1(heights, 2, 4);*/

        int[] heights2 = {2,2,3,4,5};
        System.out.println("pour water without wall:");
        p.pourWaterWithoutWall(heights2, 1, 0);
    }
}


