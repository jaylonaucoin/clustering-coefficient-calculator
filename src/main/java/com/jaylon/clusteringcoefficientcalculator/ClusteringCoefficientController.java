package com.jaylon.clusteringcoefficientcalculator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClusteringCoefficientController {

    @PostMapping("/calculate")
    public String calculateCoefficient(@RequestParam("neighbors") int neighbors,
                                       @RequestParam("links") int links,
                                       Model model) {
        // Calculate clustering coefficient
        String coefficient = calculateClusteringCoefficient(neighbors, links);

        // Add the coefficient to the model to display it in the result page
        model.addAttribute("coefficient", coefficient);

        // Return the name of the Thymeleaf template for the result page
        return "result";
    }

    // Method to calculate the clustering coefficient
    private String calculateClusteringCoefficient(int neighbors, int links) {
        // Your logic to calculate the clustering coefficient goes here
        // Calculate the clustering coefficient

        int numerator = 2 * links;
        int k2 = neighbors - 1;
        int denominator = neighbors * k2;
        double coefficient = (double) numerator / denominator;

        // Output the result
        if (coefficient != 0) {
            int[] fractionArr = reduceFraction(numerator, denominator);
            String fraction = fractionArr[0] + "/" + fractionArr[1];
            return fraction;
        } else {
            return "0";
        }
    }

    // Function to reduce a fraction to its lowest form
    static int[] reduceFraction(int x, int y)
    {
        int d;
        d = __gcd(x, y);

        x = x / d;
        y = y / d;

        return new int[] {x, y};
    }

    static int __gcd(int a, int b)
    {
        if (b == 0)
            return a;
        return __gcd(b, a % b);
    }
}
