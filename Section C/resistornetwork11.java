import java.util.Stack;

public class ResistorNetwork {

    public static double resist(String network) {
        Stack<Double> stack = new Stack<>();
        int index = 0;

        try {
            while (index < network.length()) {
                char c = network.charAt(index);

                if (Character.isDigit(c)) {
                    int endIndex = index + 1;
                    while (endIndex < network.length() && Character.isDigit(network.charAt(endIndex))) {
                        endIndex++;
                    }
                    double resistance = Double.parseDouble(network.substring(index, endIndex));
                    stack.push(resistance);
                    index = endIndex;
                } else if (c == '(' || c == '[') {
                    stack.push((double) c);
                    index++;
                } else if (c == ')' || c == ']') {
                    double totalResistance = 0.0;
                    while (!stack.isEmpty() && stack.peek() != (double) (c == ')' ? '(' : '[')) {
                        totalResistance += 1 / stack.pop();
                    }
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("Invalid network structure");
                    }
                    stack.pop(); // Pop the opening bracket
                    stack.push(1 / totalResistance);
                    index++;
                } else {
                    index++; // Skip other characters
                }
            }

            double equivalentResistance = 0.0;
            while (!stack.isEmpty()) {
                equivalentResistance += stack.pop();
            }

            return Math.ceil(equivalentResistance * 10.0) / 10.0; // Round up to the nearest tenth
        } catch (Exception e) {
            e.printStackTrace();
            return -1.0; // Return -1.0 in case of an error
        }
    }

    public static void main(String[] args) {
        System.out.println(resist("(10, [20, 30])")); 
        System.out.println(resist("[10, (20, 30)]")); 
        System.out.println(resist("([10, 20], (30, 40))")); 
        System.out.println(resist("(1, [12, 4, (1, [10, (2, 8)])])")); 
        System.out.println(resist("(6, [8, (4, [8, (4, [6, (8, [6, (10, 2)])])])])")); 
        System.out.println(resist("(10, 20, 30)"));
    }
}


