
//rounding prices
//prices: double[] prices; round/ceil each price to be added to the sum of target
//return double[] roundedPrices , to minimize the total diff(round/ceil price - price)

import java.util.ArrayList;
import java.util.List;

//自己写的，DFS
public class RoundPrices {

    public double[] getMiniDiffRounding(double[] prices, int target){
        List<Double> roundPrices = new ArrayList<>();
        double sum = getSum(prices);
        double[] floorArray = getRoundArray(prices, true);
        double[] ceilArray = getRoundArray(prices, false);

        helper(prices, floorArray, ceilArray, roundPrices, new ArrayList<>(), target, sum, 0, 0);

        double[] res = new double[prices.length];
        for(int i = 0; i < prices.length; i++){
            res[i] = roundPrices.get(i);
        }
        return res;
    }

    public void helper(double[] nums, double[] floor, double[] ceil,
                       List<Double> res, List<Double> com,
                       double target, double sum, double diffSum, int index){
        if(index == nums.length){
            if(sum == target){
                res.addAll(com);
                //TODO: double min = Math.min(diffSum, min);
            }
            return;
        }
        com.add(ceil[index]);
        helper(nums, floor, ceil, res, com, target,
                sum - nums[index] + ceil[index],
                diffSum + ceil[index] - nums[index],
                index + 1);
        com.remove(com.size() - 1);
        com.add(floor[index]);
        helper(nums, floor, ceil, res, com, target,
                sum - nums[index] + floor[index],
                diffSum + nums[index] - floor[index],
                index + 1);
        com.remove(com.size() - 1);
    }

    public double getSum(double[] nums){
        double sum = 0;
        for(double d : nums){
            sum += d;
        }
        return sum;
    }

    public double[] getRoundArray(double[] nums, boolean isFloor){
        double[] array = new double[nums.length];
        for(int i = 0 ; i < nums.length ; i++){
            if(isFloor){
                array[i] = Math.floor(nums[i]);
            }else{
                array[i] = Math.ceil(nums[i]);
            }
        }
        return array;
    }

    public static void main(String[] args){
        RoundPrices air = new RoundPrices();
        double[] prices = {1.2, 2.3, 3.4};
        double sum = 0;
        for(double d : prices){
            sum += d;
        }
        double[] roundPrices = air.getMiniDiffRounding(prices, 7);
        for(double d : roundPrices){
            System.out.print(d + " ");
        }

        System.out.println("round Price sum: " + Math.round(sum));
    }

}
