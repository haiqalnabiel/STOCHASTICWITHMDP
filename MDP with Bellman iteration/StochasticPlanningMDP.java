import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class StochasticPlanningMDP {

    // State Space and Action Space
    private static final String[] STATES = {"s1", "s2", "s3"};
    private static final String[] ACTIONS = {"a1", "a2"};

    // Transition Probabilities: P(s'|s, a)
    private static final Map<String, Double> TRANSITIONS = new LinkedHashMap<>();

    // Reward Function: R(s, a, s')
    private static final Map<String, Double> REWARDS = new LinkedHashMap<>();

    // Discount Factor
    private static final double GAMMA = 0.9;

    public static void main(String[] args) {

        TRANSITIONS.put("s1_a1_s1", 0.3);
        TRANSITIONS.put("s1_a1_s2", 0.7);
        TRANSITIONS.put("s1_a2_s1", 1.0);
        TRANSITIONS.put("s2_a1_s2", 0.4);
        TRANSITIONS.put("s2_a1_s3", 0.6);
        TRANSITIONS.put("s2_a2_s2", 1.0);
        TRANSITIONS.put("s3_a1_s3", 1.0);
        TRANSITIONS.put("s3_a2_s2", 1.0);

        REWARDS.put("s1_a1_s1", 5.0);
        REWARDS.put("s1_a1_s2", 5.0);
        REWARDS.put("s1_a2_s1", 2.0);
        REWARDS.put("s2_a1_s2", 3.0);
        REWARDS.put("s2_a1_s3", 10.0);
        REWARDS.put("s2_a2_s2", 3.0);
        REWARDS.put("s3_a1_s3", 10.0);
        REWARDS.put("s3_a2_s3", 10.0);

        // lek ngene ngko call in manual
        Map<String, Integer> valueFunction = new LinkedHashMap<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input initial values for each state:");
        for (String state : STATES) {
            System.out.print("Initial value for state " + state + ": ");
            int initialValue = scanner.nextInt();
            valueFunction.put(state, initialValue);
        }

        // formulasi
        for (int iteration = 0; iteration < 10; iteration++) {
            Map<String, Integer> newValueFunction = new LinkedHashMap<>();

            for (String state : STATES) {
                int value = Integer.MIN_VALUE;

                for (String action : ACTIONS) {
                    double actionValue = 0.0;

                    for (String nextState : STATES) {
                        String transitionKey = state + "_" + action + "_" + nextState;
                        double prob = TRANSITIONS.getOrDefault(transitionKey, 0.0);
                        double reward = REWARDS.getOrDefault(transitionKey, 0.0);
                        actionValue += prob * (reward + GAMMA * valueFunction.get(nextState));
                    }
                    
                    value = Math.max(value, (int) Math.round(actionValue));
                }

                newValueFunction.put(state, value);
            }

            valueFunction = newValueFunction;
            System.out.println("Iteration " + (iteration + 1) + ": " + valueFunction);
        }

        System.out.println("Final Value Function: " + valueFunction);
        scanner.close();
    }
}